UPDATE logins (logout_time)
SET
    logout_time = CURRENT_TIMESTAMP
WHERE
    session_id = ?;