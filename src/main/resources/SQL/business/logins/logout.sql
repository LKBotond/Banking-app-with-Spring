UPDATE logins
SET
    logout_time = CURRENT_TIMESTAMP
WHERE
    id = ?;