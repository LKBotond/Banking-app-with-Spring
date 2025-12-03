import { processRegistration } from "../services/RegistrationService.js";
import { serializeFormToJSON } from "../helpers/Scraping.js";
import { on, redirect, validateFormData } from "../helpers/Helpers.js";

on("registerForm", "submit", async (event) => {
  event.preventDefault();
  const formData = serializeFormToJSON(event.target);
  if (!validateFormData(formData)) {
    alert("Missing username or password or name");
    return;
  }

  let success = await processRegistration(
    formData.firstName,
    formData.lastName,
    formData.email,
    formData.password
  );

  if (!success) {
    return;
  }
  redirect("http://localhost:8080/pages/Overview.html");
});
