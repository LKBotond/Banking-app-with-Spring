UPDATE users
SET
    email = '',
    name_encrypted = '',
    salt = '',
    iv = '',
    pass_hash = '',
    status = 'DELETED',
    deleted_at = CURRENT_TIMESTAMP
WHERE
    user_id = ?;