# Todo:

## Broad:

1.  [Create DB](#create-db)
2.  [Create Services](#create-services)
3.  [Create Rest Endpoints](#create-rest-endpoints)
4.  [Rework Authentication](#rework-authentication)


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
    - [x] Create domain objects for service level data transfer
    - [x] Create DTOs for requests

### Create Services:

1. Authentication service:
   - [x] Password, username hashing
   - [x] Argon 2 Key derivation
   - [x] Data encryption, and decryption
2. Access service:
   - [x] User creation
   - [x] User deletion
   - [x] Account creation
   - [x] Account deletion
   - [x] inter Account transaction

### Create Rest Endpoints:

1. Authentication:
   - [ ] Registration
   - [ ] Login
   - [ ] Credential modification
2. Transaction:
   - [ ] DData visualization
   - [ ] Transfer
   - [ ] Withdrawal
   - [ ] Deposit

### Rework Authentication:
- [x] add active_sessions table to schema
      this will couple the logins table's session_id and a random UUID in a tight coupling
- [x] add sql queries to access and modify said table
- [x] add daos to implement said queries
- [ ] refractor authentication services
- [ ] implement rest access ponts
- [ ] Update sessionCache for domains which store minimal data for validation purposes to avoid excesive db lookups. 
 
 ## NEEEED TO UNLOCK GET_ACCOUNT_BY_ID IF TRANSACTION FAILS