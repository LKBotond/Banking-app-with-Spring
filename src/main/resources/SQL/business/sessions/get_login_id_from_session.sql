SELECT
    login_id
FROM
    active_sessions
WHERE
    session_id = ?;