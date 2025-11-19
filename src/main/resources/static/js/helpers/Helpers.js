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

export function on(target, event, handler) {
  let elements;

  if (target instanceof Element || target === document || target === window) {
    elements = [target];
  } else if (typeof target === "string") {
    const el = document.getElementById(target);
    if (!el) {
      throw new Error(`Element with ID "${target}" not found`);
    }
    elements = [el];
  } else {
    throw new Error("Invalid target for on()");
  }

  elements.forEach((el) => el.addEventListener(event, handler));
}
