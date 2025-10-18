UPDATE salts_and_ivs
SET
    IV = ?
WHERE
    user_id = ?;