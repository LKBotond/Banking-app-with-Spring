UPDATE logins
SET
    logout_time = CURRENT_TIMESTAMP,
    status = false
WHERE
    session_id = ?;