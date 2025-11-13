UPDATE accounts
SET
    status = 'DELETED'
WHERE
    id = ?;