INSERT INTO
    logins (user_id, status)
VALUES
    (?, true) RETURNING session_id;