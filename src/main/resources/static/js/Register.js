import { saveSession } from "./helpers/Helpers.js";
const $ = window.jQuery;

$("#registerForm").on("submit", async (event) => {
  event.preventDefault();
  console.log("Register button clicked");

  const RegistrationRequest = {
    firstName: $("#firstName").val(),
    lastName: $("#lastName").val(),
    email: $("#email").val(),
    password: $("#password").val().split(""),
  };
  try {
    const response = await fetch("/access/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(RegistrationRequest),
    });
    console.log("Response status:", response.status);
    if (response.ok) {
      const result = await response.json();
      $("#message").text("Registered successfully! Welcome, " + result.name);
      saveSession(result);
      window.location.href = "http://localhost:8080/pages/Overview.html";
    } else {
      $("#message").text("Registration failed.");
    }
  } catch (err) {
    console.error(err);
    $("#message").text("Error connecting to backend.");
  }
});
