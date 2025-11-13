SELECT
    funds
FROM
    accounts
WHERE
    id = ?
    AND status = 'ACTIVE';