import { saveSession } from "./helpers/Helpers.js";
document
  .getElementById("registerForm")
  .addEventListener("submit", async (event) => {
    event.preventDefault();
    console.log("Register button clicked");

    const RegistrationRequest = {
      firstName: document.getElementById("firstName").value,
      lastName: document.getElementById("lastName").value,
      email: document.getElementById("email").value,
      password: document.getElementById("password").value.split(""),
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
        document.getElementById("message").textContent =
          "Registered successfully! Welcome, " + result.name;
        saveSession(result);
        window.location.href="http://localhost:8080/pages/Overview.html"
      } else {
        document.getElementById("message").textContent = "Registration failed.";
      }
    } catch (err) {
      console.error(err);
      document.getElementById("message").textContent =
        "Error connecting to backend.";
    }
  });
