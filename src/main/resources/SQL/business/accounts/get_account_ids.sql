SELECT
    id
FROM
    accounts
WHERE
    user_id = ?
    AND status = 'ACTIVE';