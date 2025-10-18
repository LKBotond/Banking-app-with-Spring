UPDATE salts_and_ivs
SET
    salt = ?
WHERE
    user_id = ?;