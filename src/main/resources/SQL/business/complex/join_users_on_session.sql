SELECT u.user_id
FROM users u
JOIN logins l ON u.user_id = l.user_id
JOIN active_sessions a ON l.id = a.login_id
WHERE a.session_id = ?;