INSERT INTO
    master_record (
        sender_id, 
        receiver_id, 
        funds,
        transaction_type)
VALUES
    (?, ?, ?, 'TRANSFER')