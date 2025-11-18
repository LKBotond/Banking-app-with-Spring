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
