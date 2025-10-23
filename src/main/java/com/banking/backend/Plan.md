# Todo:

## Broad:

1.  [Create DB](#create-db)
2.  [Create Services](#create-services)
3.  [Create Rest Endpoints](#create-rest-endpoints)
3.  [Rework Authentication](#rework-authentication)

## Detailed:

### Create DB:

1.  PostgreSQL setup:
    - [x] Configure Application Properties
    - [x] Create Schema
    - [x] Create SQL queries for Said Schema
2.  DAO Setup:
    - [x] Create SQL FileReader (.SQL -> String)
    - [x] Create a static aggregator class to store the SQL Strings
    - [x] Configure JDBCTemplate
    - [x] Create DAOs for necessary DB operations
    - [ ] Create domain objects for service level data transfer
    - [ ] Create DTOs for requests

### Create Services:

1. Authentication service:
   - [ ] Password, username hashing
   - [ ] Argon 2 Key derivation
   - [ ] Data encryption, and decryption
2. User service:
   - [ ] User creation
   - [ ] User deletion
   - [ ] Account creation
   - [ ] Account deletion
   - [ ] inter Account transaction

### Create Rest Endpoints:

1. Authentication:
   - [ ] Registration
   - [ ] Login
   - [ ] Credential modification
2. Transaction:
   - [ ] DData visualization
   - [ ] Transfer
   - [ ] Withdrawal
   - [ ] DDeposit

### Rework Authentication:
- [ ] add active_sessions table to schema
      this will couple the logins table's session_id and a random UUID in a tight coupling
- [ ] add sql queries to access and modify said table
- [ ] add daos to implement said queries
- [ ] refractor authentication services
- [ ] implement rest access ponts
