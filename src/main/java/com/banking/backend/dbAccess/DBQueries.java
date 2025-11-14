package com.banking.backend.dbAccess;

public final class DBQueries {
    private DBQueries() {}
    
    //User specific:
    public static final String CREATE_USER = SQLFileReader.readSQL("SQL/business/users/create_user.sql");
    public static final String GET_PASS_HASH_BY_EMAIL = SQLFileReader.readSQL("SQL/business/users/get_pass_hash_by_email.sql");
    public static final String GET_PASS_HASH_BY_ID = SQLFileReader.readSQL("SQL/business/users/get_pass_hash_by_id.sql");
    public static final String GET_USER_ID_BY_EMAIL = SQLFileReader.readSQL("SQL/business/users/get_user_id_by_email.sql");
    public static final String GET_USER = SQLFileReader.readSQL("SQL/business/users/get_user.sql");

    //Security specific:
    public static final String GET_IV = SQLFileReader.readSQL("SQL/business/security/get_iv.sql");
    public static final String GET_SALT = SQLFileReader.readSQL("SQL/business/security/get_salt.sql");
    public static final String UPDATE_IV = SQLFileReader.readSQL("SQL/business/security/update_iv.sql");
    public static final String UDATE_SALT = SQLFileReader.readSQL("SQL/business/security/update_salt.sql");
    
    
    //Account specific:
    public static final String CREATE_ACCOUNT = SQLFileReader.readSQL("SQL/business/accounts/create_account.sql");
    public static final String GET_ACCOUNT_IDS = SQLFileReader.readSQL("SQL/business/accounts/get_account_ids.sql");
    public static final String GET_ACCOUNT_BY_ID = SQLFileReader.readSQL("SQL/business/accounts/get_account_by_id.sql");
    public static final String GET_FUNDS_FOR_ACCOUNT = SQLFileReader.readSQL("SQL/business/accounts/get_funds_for_account.sql");
    public static final String GET_ALL_ACCOUNTS_FOR_USER = SQLFileReader.readSQL("SQL/business/accounts/get_all_accounts_by_user_id.sql");
    public static final String UPDATE_FUNDS_FOR_ACCOUNT_ID = SQLFileReader.readSQL("SQL/business/accounts/update_funds.sql");
    public static final String LOCK_FOR_TRANSACTION = SQLFileReader.readSQL("SQL/business/accounts/lock_account_for_transaction.sql");
    
    //Login specific:
    public static final String LOGIN = SQLFileReader.readSQL("SQL/business/logins/login.sql");
    public static final String LOGOUT = SQLFileReader.readSQL("SQL/business/logins/logout.sql");

    //Session specific:
    public static final String ADD_SESSION = SQLFileReader.readSQL("SQL/business/sessions/add_session.sql");
    public static final String GET_LOGIN_ID = SQLFileReader.readSQL("SQL/business/sessions/get_login_id_from_session.sql");
    public static final String GET_NUMBER_OF_CURRENTLY_ACTIVE_USERS = SQLFileReader.readSQL("SQL/business/sessions/get_number_of_current_users.sql");

    //MasterRecord specific:
    public static final String GET_RECEIVER_DATA = SQLFileReader.readSQL("SQL/business/master_record/get_receiver_data.sql");
    public static final String GET_SENDER_DATA = SQLFileReader.readSQL("SQL/business/master_record/get_sender_data.sql");
    public static final String GET_TRANSACTIONS_DATA = SQLFileReader.readSQL("SQL/business/master_record/get_transaction_data.sql");
    public static final String RECORD_TRANSFER = SQLFileReader.readSQL("SQL/business/master_record/record_transfer.sql");
    public static final String RECORD_DEPOSIT = SQLFileReader.readSQL("SQL/business/master_record/record_deposit.sql");
    public static final String RECORD_WITHDRAWAL = SQLFileReader.readSQL("SQL/business/master_record/record_withdrawal.sql");

    //Deletion Specific:
    public static final String DELETE_USER = SQLFileReader.readSQL("SQL/business/deletions/delete_from_users.sql");
    public static final String DELETE_ACCOUNT = SQLFileReader.readSQL("SQL/business/deletions/delete_account.sql");
    public static final String DELETE_SESSION = SQLFileReader.readSQL("SQL/business/deletions/delete_session.sql");

    //COMPLEX QUERIES JOINS AND ALL THAT:
    public static final String GET_USER_ID_BY_SESSION_ID = SQLFileReader.readSQL("SQL/business/complex/join_users_on_session.sql");
}
