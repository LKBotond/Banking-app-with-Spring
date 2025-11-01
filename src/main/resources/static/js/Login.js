import { saveSession } from "./helpers/Helpers.js";
document
  .getElementById("loginForm")
  .addEventListener("submit", async (event) => {
    event.preventDefault();
    console.log("Login button clicked");

    const loginRequest = {
      email: document.getElementById("email").value,
      password: document.getElementById("password").value.split(""),
    };
    try {
      const response = await fetch("/access/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(loginRequest),
      });
      console.log("Response status:", response.status);
      if (response.ok) {
        const result = await response.json();
        document.getElementById("message").textContent =
          "Login successfull! Welcome, " + result.name;
        saveSession(result);
      } else {
        document.getElementById("message").textContent = "Registration failed.";
      }
    } catch (err) {
      console.error(err);
      document.getElementById("message").textContent =
        "Error connecting to backend.";
    }
  });
