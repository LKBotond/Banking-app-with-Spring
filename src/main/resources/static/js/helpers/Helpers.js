export function saveSession(sessionToken) {
  sessionStorage.setItem("session", JSON.stringify(sessionToken));
}

export function loadSession() {
  const session = sessionStorage.getItem("session");
  return session ? JSON.parse(session) : null;
}

export function validateFormData(formData) {
  for (const value of Object.values(formData)) {
    if (value == null || value === "" || String(value).trim() === "") {
      return false;
    }
  }
  return true;
}

export function redirect(target) {
  window.location.href = target;
}

export function on(id, event, handler) {
  document.getElementById(id).addEventListener(event, (e) => {
    handler(e);
  });
}
