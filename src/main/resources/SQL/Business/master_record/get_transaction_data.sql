SELECT
    *
FROM
    master_record
WHERE
    (
        sender_id = ?
        AND receiver_id = ?
        AND transaction_type = 'TRANSFER'
    )
ORDER BY
    transaction_date
LIMIT
    ?
OFFSET
    ?;