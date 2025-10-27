# Architecture:

## Login chain:

### Abstract :

```mermaid

graph TD
    subgraph Client
        A[User Submits Credentials]
    end

    subgraph System
        B{1. Find User by Email}
        C{2. Verify Password}
        D[3. Generate Session Token]
    end

    subgraph Response
        E[404 Not Found]
        F[401 Unauthorized]
        G[200 OK + Session Token]
    end

    A --> B
    B -- User Not Found --> E
    B -- User Found --> C
    C -- Password Mismatch --> F
    C -- Password Match --> D
    D --> G
```

### Implementation:

```mermaid
sequenceDiagram
    participant LoginController
    participant LoginService
    participant UsersDao
    participant AuthenticationService
    participant AccountDAO
    participant Encryptor
    participant LoginDao
    participant SessionService

    LoginController->>LoginService: login(LoginRequestDTO)
    activate LoginService

    alt Successful Login

        Note over LoginService: 1. Authenticate User
        LoginService->>UsersDao: getUserByEmail(email)
        UsersDao-->>LoginService: User object
        LoginService->>AuthenticationService: verifyPass(passHash, password)
        AuthenticationService-->>LoginService: boolean (true)

        Note over LoginService: try...finally block begins
        Note over LoginService: 2. Fetch Accounts & Decrypt Name
        LoginService->>AccountDAO: getAccountsByUserID(userId)
        AccountDAO-->>LoginService: ArrayList<Account>

        LoginService->>Encryptor: decryptUserName(...)
        Encryptor-->>LoginService: decryptedName

        Note over LoginService: 3. Create Login & Session
        LoginService->>LoginDao: login(userId)
        LoginDao-->>LoginService: loginId
        LoginService->>SessionService: createSessionToken(accessToken, loginId)
        SessionService-->>LoginService: (accessToken is populated)

        Note over LoginService: Finally block: Wipe sensitive data
        LoginService->>AuthenticationService: wipeSensitiveMemory(password, iv, salt)
        AuthenticationService-->>LoginService:

        LoginService-->>LoginController: return AccessToken

    else User Not Found

        LoginService->>UsersDao: getUserByEmail(email)
        UsersDao-->>LoginService: Optional.empty()
        LoginService-->>LoginController: throws UserNotFoundException

    else Wrong Password

        LoginService->>UsersDao: getUserByEmail(email)
        UsersDao-->>LoginService: User object
        LoginService->>AuthenticationService: verifyPass(passHash, password)
        AuthenticationService-->>LoginService: boolean (false)
        LoginService-->>LoginController: throws WrongPasswordException

    end

    deactivate LoginService
```

## Registration chain

### Implementation:

```mermaid
sequenceDiagram
    participant RegistrationController
    participant RegistrationService
    participant Argon2KDF
    participant Encryptor
    participant AuthenticationService
    participant UsersDao
    participant LoginDao
    participant SessionService

    RegistrationController->>RegistrationService: register(RegisterRequestDTO)
    activate RegistrationService

    alt Successful Registration

        Note over RegistrationService: try...finally block begins
        RegistrationService->>Argon2KDF: getRandom(16)
        Argon2KDF-->>RegistrationService: salt
        RegistrationService->>Argon2KDF: getRandom(12)
        Argon2KDF-->>RegistrationService: iv

        Note over RegistrationService: 1. Encrypt Name & Hash Password
        RegistrationService->>Argon2KDF: deriveKey(password, salt)
        Argon2KDF-->>RegistrationService: encryptionKey
        RegistrationService->>Encryptor: encryptWithAESGCM(fullName, key, iv)
        Encryptor-->>RegistrationService: encryptedName
        RegistrationService->>AuthenticationService: hashWithArgon2(password)
        AuthenticationService-->>RegistrationService: passHash

        Note over RegistrationService: 2. Create User in DB
        RegistrationService->>UsersDao: create(...)
        UsersDao-->>RegistrationService: Optional(userId)

        Note over RegistrationService: 3. Create Login Entry
        RegistrationService->>LoginDao: login(userId)
        LoginDao-->>RegistrationService: Optional(loginId)

        Note over RegistrationService: 4. Create Session & Access Token
        RegistrationService->>SessionService: createSessionToken(accessToken, loginId)
        SessionService-->>RegistrationService: (accessToken is populated)

        Note over RegistrationService: Finally block: Wipe sensitive data
        RegistrationService->>AuthenticationService: wipeSensitiveMemory(salt, iv, password)
        AuthenticationService-->>RegistrationService:

        RegistrationService-->>RegistrationController: return AccessToken

    else Registration Failed (e.g., DB Error)

        Note over RegistrationService: try...finally block begins
        %% ... key generation and hashing steps succeed ...

        Note over RegistrationService: A DAO operation fails (e.g., UsersDao.create)
        RegistrationService->>UsersDao: create(...)
        UsersDao-->>RegistrationService: Optional.empty()
        Note over RegistrationService: The service prepares to throw the exception

        Note over RegistrationService: Finally block still executes
        RegistrationService->>AuthenticationService: wipeSensitiveMemory(salt, iv, password)
        AuthenticationService-->>RegistrationService:

        RegistrationService-->>RegistrationController: throws RegistrationFailedException

    end

    deactivate RegistrationService
```

## Deletion chain:

### Implementation:

```mermaid
sequenceDiagram
    participant DeletionController
    participant DeletionService
    participant ActiveSessionsDao
    participant UsersDao
    participant AuthenticationService
    participant AccountDAO
    participant DeletionDao

    DeletionController->>DeletionService: deleteUser(DeletionRequestDTO)
    activate DeletionService

    alt Successful Deletion

        Note over DeletionService: 1. Validate Session & Get User ID
        DeletionService->>ActiveSessionsDao: getUserIdbySessionId(sessionId)
        ActiveSessionsDao-->>DeletionService: Optional(userId)

        Note over DeletionService: 2. Verify Password
        DeletionService->>UsersDao: getPassHashByID(userId)
        UsersDao-->>DeletionService: Optional(passOnRecord)
        DeletionService->>AuthenticationService: verifyPass(passOnRecord, password)
        AuthenticationService-->>DeletionService: boolean (true)

        Note over DeletionService: 3. Delete Associated Accounts
        DeletionService->>AccountDAO: getAccountsByUserID(userId)
        AccountDAO-->>DeletionService: List<Account>

        loop For Each Account
            DeletionService->>DeletionDao: deleteAccount(accountId)
            DeletionDao-->>DeletionService:
        end

        Note over DeletionService: 4. Delete User & Session
        DeletionService->>DeletionDao: deleteUser(userId)
        DeletionDao-->>DeletionService:
        DeletionService->>ActiveSessionsDao: deleteActiveSession(sessionId)
        ActiveSessionsDao-->>DeletionService:

        DeletionService-->>DeletionController: (return void)

    else Invalid Session

        DeletionService->>ActiveSessionsDao: getUserIdbySessionId(sessionId)
        ActiveSessionsDao-->>DeletionService: Optional.empty()
        DeletionService-->>DeletionController: throws InvalidSessionException

    else User Not Found (Post-Session Check)

        DeletionService->>ActiveSessionsDao: getUserIdbySessionId(sessionId)
        ActiveSessionsDao-->>DeletionService: Optional(userId)
        DeletionService->>UsersDao: getPassHashByID(userId)
        UsersDao-->>DeletionService: Optional.empty()
        DeletionService-->>DeletionController: throws UserNotFoundException

    else Wrong Password

        DeletionService->>ActiveSessionsDao: getUserIdbySessionId(sessionId)
        ActiveSessionsDao-->>DeletionService: Optional(userId)
        DeletionService->>UsersDao: getPassHashByID(userId)
        UsersDao-->>DeletionService: Optional(passOnRecord)
        DeletionService->>AuthenticationService: verifyPass(passOnRecord, password)
        AuthenticationService-->>DeletionService: boolean (false)
        DeletionService-->>DeletionController: throws WrongPasswordException

    end

    deactivate DeletionService


```

## Logout chain:

### Implementation:

```mermaid
sequenceDiagram
    participant LogoutController
    participant LogoutService
    participant ActiveSessionsDao
    participant LoginDao

    LogoutController->>LogoutService: logout(LogoutRequestDTO)
    activate LogoutService

    alt Successful Logout

        Note over LogoutService: 1. Get Login ID from Session
        LogoutService->>ActiveSessionsDao: getUsersLoginId(sessionId)
        ActiveSessionsDao-->>LogoutService: Optional(loginId)

        Note over LogoutService: 2. Update Login Record
        LogoutService->>LoginDao: logout(loginId)
        LoginDao-->>LogoutService:

        Note over LogoutService: 3. Delete Active Session
        LogoutService->>ActiveSessionsDao: deleteActiveSession(sessionId)
        ActiveSessionsDao-->>LogoutService:

        LogoutService-->>LogoutController: (return void)

    else Invalid Session / Login Not Found

        Note over LogoutService: Session ID does not exist
        LogoutService->>ActiveSessionsDao: getUsersLoginId(sessionId)
        ActiveSessionsDao-->>LogoutService: Optional.empty()
        LogoutService-->>LogoutController: throws LoginIdNotFoundException

    end
```
