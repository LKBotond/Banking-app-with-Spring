SELECT
    user_id,
    name_encrypted,
    iv
FROM
    users
WHERE
    email = ?;