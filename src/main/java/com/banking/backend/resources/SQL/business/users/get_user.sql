SELECT
    user_id,
    name_encrypted,
    salt,
    iv,
    pass_hash
FROM
    users
WHERE
    email = ?;