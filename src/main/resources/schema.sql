DROP TABLE IF EXISTS logins CASCADE;

DROP TABLE IF EXISTS users CASCADE;

DROP TABLE IF EXISTS accounts CASCADE;

DROP TABLE IF EXISTS salts_and_ivs CASCADE;

DROP TABLE IF EXISTS master_record CASCADE;

CREATE TABLE
    users (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        email TEXT,
        name_encrypted TEXT,
        pass_hash TEXT,
        registered TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT pk_users PRIMARY KEY (id)
    );

CREATE INDEX idx_email ON users (email);

CREATE TABLE
    accounts (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        user_id BIGINT,
        funds NUMERIC(20, 2) DEFAULT 0,
        CONSTRAINT pk_accounts PRIMARY KEY (id),
        CONSTRAINT fk_accounts_users FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE INDEX idx_user ON accounts (user_id);

CREATE TABLE
    logins (
        session_id BIGINT GENERATED ALWAYS AS IDENTITY,
        user_id BIGINT,
        status BOOLEAN,
        login_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        logout_time TIMESTAMPTZ,
        CONSTRAINT pk_session_users PRIMARY KEY (session_id),
        CONSTRAINT fk_logins_users FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE
    salts_and_ivs (
        salt TEXT,
        iv TEXT,
        user_id BIGINT,
        constraint fk_this_users (user_id) REFERENCES users (id) constraint pk_user_id PRIMARY KEY (user_id)
    );

CREATE TABLE
    master_record (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        sender_id BIGINT,
        receiver_id BIGINT,
        funds NUMERIC(20, 2),
        date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT pk_master_record PRIMARY KEY (id),
        CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES accounts (id),
        CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES accounts (id)
    );

CREATE INDEX idx_sender ON master_record (sender_id);

CREATE INDEX idx_receiver ON master_record (receiver_id);