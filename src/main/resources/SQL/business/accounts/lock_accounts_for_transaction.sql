SELECT
    *
FROM
    accounts
WHERE
    id IN (?, ?) FOR
UPDATE;