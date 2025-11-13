SELECT
    *
FROM
    accounts
WHERE
    id IN (?, ?)
    AND status = 'ACTIVE' FOR
UPDATE;