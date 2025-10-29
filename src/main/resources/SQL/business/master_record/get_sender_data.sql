SELECT
    *
FROM
    master_record
WHERE
    sender_id = ?
ORDER BY
    transaction_date
LIMIT
    ?
OFFSET
    ?;