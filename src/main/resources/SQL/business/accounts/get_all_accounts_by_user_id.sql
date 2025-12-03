SELECT
    id,
    funds
FROM
    accounts
WHERE
    user_id = ?
    AND status = 'ACTIVE';