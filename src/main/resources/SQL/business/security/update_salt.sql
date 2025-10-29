UPDATE users
SET
    salt = ?
WHERE
    user_id = ?;