INSERT INTO
    users (email, name_encrypted, salt, iv, pass_hash)
VALUES
    (?, ?, ?, ?, ?) RETURNING user_id;