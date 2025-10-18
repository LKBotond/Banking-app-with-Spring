# Todo:

## Broad:

1.  [Create DB](#create-db)
2.  [Create Services](#create-services)
3.  [Create Rest Endpoints](#create-rest-endpoints)

## Detailed:

### Create DB:

1.  PostgreSQL setup:
    - [x] Configure Application Properties
    - [x] Create Schema
    - [x] Create SQL queries for Said Schema
2.  DAO Setup:
    - [ ] Create SQL FileReader (.SQL -> String)
    - [ ] Create a static aggregator class to store the SQL Strings
    - [ ] Configure JDBCTemplate
    - [ ] Create DAOs for necessary DB operations
    - [ ] Create DTOs for service level data transfer

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
