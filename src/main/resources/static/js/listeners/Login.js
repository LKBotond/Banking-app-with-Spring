import { processLogin } from "../services/LoginService.js";
import { serializeFormToJSON } from "../helpers/Scraping.js";
import { on, redirect, validateFormData } from "../helpers/Helpers.js";

on("loginForm", "submit", async (event) => {
  event.preventDefault();
  const formData = serializeFormToJSON(event.target);
  if (!validateFormData(formData)) {
    alert("Missing username or password");
    return;
  }
  let success = await processLogin(formData.email, formData.password);
  if (!success) {
    return;
  }
  redirect("http://localhost:8080/pages/Overview.html");
});
