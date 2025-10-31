export function saveSession(sessionToken) {
  sessionStorage.setItem("session", JSON.stringify(sessionToken));
}

export function loadSession() {
  const session = sessionStorage.getItem("session");
  return session ? JSON.parse(session) : null;
}

