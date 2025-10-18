SELECT
    id,
    name_encrypted,
    iv
FROM
    users
WHERE
    email = ?;