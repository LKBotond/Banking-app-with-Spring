export function serializeFormToJSON(form) {
  const data = {};
  const fields = form.querySelectorAll("input, select, textarea");

  fields.forEach((field) => {
    if (field.id) {
      data[field.id] = field.value;
    }
  });
  return data;
}

export function getButtonsByIdPrefix(prefix) {
  const selector = `button[id^="${prefix}"]`;
  const buttons = document.querySelectorAll(selector);
  return buttons;
}

export function massAddEventListener(elements, event, handler) {
  for (let element of elements) {
    element.addEventListener(event, handler);
  }
}

export function extractIdFromEvent(event) {
  return event.currentTarget.dataset.accountId;
}

export function getFloatFromPrompt() {
  const input = prompt("Enter a number:");
  if (input == null) {
    return null;
  }
  const value = parseFloat(input);
  if (isNaN(value) || !isFinite(value) || value === 0) {
    return null;
  }
  return value;
}
