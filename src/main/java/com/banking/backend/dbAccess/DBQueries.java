package com.banking.backend.dbAccess;

public final class DBQueries {
    private DBQueries() {}
    public static final String CREATE_ACCOUNT = SQLFileReader.readSQL("SQL/Business/accounts/create_account.sql");
    public static final String CREATE_USER = SQLFileReader.readSQL("SQL/Business/users/create_user.sql");
    
    public static final String GET_PASS_HASH = SQLFileReader.readSQL("SQL/Business/users/get_pass_hash.sql");
    public static final String GET_PASS_HASH_BY_ID = SQLFileReader.readSQL("SQL/Business/users/get_pass_hash_by_id.sql");
    public static final String GET_USER = SQLFileReader.readSQL("SQL/Business/users/get_user.sql");
    public static final String GET_USER_ID = SQLFileReader.readSQL("SQL/Business/users/get_user_id.sql");
    
    public static final String GET_IV = SQLFileReader.readSQL("SQL/Business/security/get_iv.sql");
    public static final String GET_SALT = SQLFileReader.readSQL("SQL/Business/security/get_salt.sql");
    public static final String CREATE_USER_FIELD = SQLFileReader.readSQL("SQL/Business/security/create_user_field.sql");
    public static final String UDATE_SALT = SQLFileReader.readSQL("SQL/Business/security/update_salt.sql");
    public static final String UPDATE_IV = SQLFileReader.readSQL("SQL/Business/security/update_iv.sql");


    public static final String GET_ACCOUNT_IDS = SQLFileReader.readSQL("SQL/Business/accounts/get_account_ids.sql");
    public static final String GET_ACCOUNT_BY_ID = SQLFileReader.readSQL("SQL/Business/accounts/get_account_by_id.sql");
    public static final String GET_FUNDS_FOR_ACCOUNT = SQLFileReader.readSQL("SQL/Business/accounts/get_funds_for_account.sql");
    public static final String GET_ALL_ACCOUNTS_FOR_USER = SQLFileReader.readSQL("SQL/Business/accounts/get_funds_for_account.sql");
    public static final String UPDATE_FUNDS_FOR_ACCOUNT_ID = SQLFileReader.readSQL("SQL/Business/accounts/update_funds.sql");
    
    public static final String LOGIN = SQLFileReader.readSQL("SQL/Business/logins/login.sql");
    public static final String LOGOUT = SQLFileReader.readSQL("SQL/Business/logins/logout.sql");

    public static final String GET_RECEIVER_DATA = SQLFileReader.readSQL("SQL/Business/master_record/get_receiver_data.sql");
    public static final String GET_SENDER_DATA = SQLFileReader.readSQL("SQL/Business/master_record/get_sender_data.sql");
    public static final String GET_TRANSACTIONS_DATA = SQLFileReader.readSQL("SQL/Business/master_record/get_transaction_data.sql");
    public static final String RECORD_TRANSFER = SQLFileReader.readSQL("SQL/Business/master_record/record_transfer.sql");
    public static final String RECORD_DEPOSIT = SQLFileReader.readSQL("SQL/Business/master_record/record_deposit.sql");
    public static final String RECORD_WITHDRAWAL = SQLFileReader.readSQL("SQL/Business/master_record/record_withdrawal.sql");


    public static final String DELETE_USER = SQLFileReader.readSQL("SQL/Business/deletions/delete_from_user.sql");
    public static final String DELETE_USER_CRYPTO = SQLFileReader.readSQL("SQL/Business/deletions/delete_from_salts_and_ivs.sql");
    public static final String DELETE_ACCOUNT = SQLFileReader.readSQL("SQL/Business/deletions/delete_account.sql");




}
