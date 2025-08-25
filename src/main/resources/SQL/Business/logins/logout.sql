UPDATE logins (logout_time)
SET
    logout_time = CURRENT_TIMESTAMP
WHERE
    user_id = ?
    AND logout_time IS NULL;