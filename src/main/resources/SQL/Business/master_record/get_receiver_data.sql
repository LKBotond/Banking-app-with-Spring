SELECT
    *
FROM
    master_record
WHERE
    receiver_id = ?
ORDER BY
    transaction_date
LIMIT
    ?
OFFSET
    ?; 