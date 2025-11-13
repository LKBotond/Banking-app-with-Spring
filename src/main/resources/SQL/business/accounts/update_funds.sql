UPDATE accounts
SET
    funds = ?
WHERE
    id = ?
    AND status = 'ACTIVE';