-- ============================
-- Initial setup during development
-- Drops tables and types if they exist (for clean setup)
-- ============================

DROP TABLE IF EXISTS logins CASCADE;

DROP TABLE IF EXISTS users CASCADE;

DROP TABLE IF EXISTS accounts CASCADE;

DROP TABLE IF EXISTS master_record CASCADE;

DROP TABLE IF EXISTS active_sessions CASCADE;

DROP TYPE IF EXISTS transaction_type CASCADE;

-- ============================
-- Necessary enums and types
-- ============================

-- Enum for different types of transactions

CREATE TYPE transaction_type AS ENUM ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER');

--TABLES START HERE:

-- ============================
-- Users Table
-- ============================

CREATE TABLE
    users (
        user_id BIGINT GENERATED ALWAYS AS IDENTITY,
        email TEXT,
        name_encrypted TEXT, -- encrypted version of the user's full name
        salt TEXT,           -- used for password hashing
        iv TEXT,             -- initialization vector for name_encrypted
        pass_hash TEXT,      -- hashed password
        registered TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT pk_users PRIMARY KEY (user_id)
    );

COMMENT ON TABLE users IS 'Stores user authentication and identification data.';
COMMENT ON COLUMN users.email IS 'The user''s email address (used for login).';
COMMENT ON COLUMN users.name_encrypted IS 'Encrypted name of the user.';
COMMENT ON COLUMN users.salt IS 'Per-user salt used in password hashing.';
COMMENT ON COLUMN users.iv IS 'Initialization vector for decrypting user data.';
COMMENT ON COLUMN users.pass_hash IS 'Hashed password.';
COMMENT ON COLUMN users.registered IS 'Timestamp when the user registered.';

CREATE INDEX idx_email ON users (email);

-- ============================
-- Accounts Table
-- ============================

CREATE TABLE
    accounts (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        user_id BIGINT,
        funds NUMERIC(20, 2) DEFAULT 0,
        CONSTRAINT pk_accounts PRIMARY KEY (id),
        CONSTRAINT fk_accounts_users FOREIGN KEY (user_id) REFERENCES users (user_id)
    );

COMMENT ON TABLE accounts IS 'Stores financial account data for users.';
COMMENT ON COLUMN accounts.user_id IS 'Reference to the owning user.';
COMMENT ON COLUMN accounts.funds IS 'Current balance of the account.';

CREATE INDEX idx_user ON accounts (user_id);

-- ============================
-- Logins Table
-- ============================

CREATE TABLE
    logins (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        user_id BIGINT,
        status BOOLEAN, -- true = active session, false = logged out
        login_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        logout_time TIMESTAMPTZ,
        CONSTRAINT pk_session_users PRIMARY KEY (id),
        CONSTRAINT fk_logins_users FOREIGN KEY (user_id) REFERENCES users (user_id)
    );


COMMENT ON TABLE logins IS 'Tracks user login and logout sessions.';
COMMENT ON COLUMN logins.status IS 'Indicates if the session is still active.';
COMMENT ON COLUMN logins.login_time IS 'Timestamp of login.';
COMMENT ON COLUMN logins.logout_time IS 'Timestamp of logout (nullable).';

-- ============================
-- Active Sessions Table
-- ============================

CREATE TABLE
    active_sessions (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        login_id BIGINT,
        session_id TEXT, --session token
        CONSTRAINT pk_session_active_sessions PRIMARY KEY (id),
        CONSTRAINT fk_active_sessions FOREIGN KEY (login_id) REFERENCES logins (id)
    );

COMMENT ON TABLE active_sessions IS 'Holds currently active session identifiers.';
COMMENT ON COLUMN active_sessions.session_id IS 'Unique identifier for an active session.';

CREATE INDEX idx_login ON active_sessions (login_id);

-- ============================
-- Master Record Table
-- ============================

CREATE TABLE
    master_record (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        sender_id BIGINT,
        receiver_id BIGINT,
        transaction_type transaction_type NOT NULL,
        funds NUMERIC(20, 2),
        transaction_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT pk_master_record PRIMARY KEY (id),
        CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES accounts (id),
        CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES accounts (id)
    );

COMMENT ON TABLE master_record IS 'Tracks all transactions between accounts.';
COMMENT ON COLUMN master_record.sender_id IS 'Account ID of the sender.';
COMMENT ON COLUMN master_record.receiver_id IS 'Account ID of the receiver.';
COMMENT ON COLUMN master_record.transaction_type IS 'Type of transaction.';
COMMENT ON COLUMN master_record.funds IS 'Amount of funds transferred.';
COMMENT ON COLUMN master_record.transaction_date IS 'Timestamp of the transaction.';

CREATE INDEX idx_sender ON master_record (sender_id);
CREATE INDEX idx_receiver ON master_record (receiver_id);