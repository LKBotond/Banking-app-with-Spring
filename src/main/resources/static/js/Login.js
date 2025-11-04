import { saveSession } from "./helpers/Helpers.js";
const $ = window.jQuery;
$("#loginForm").on("submit", async (event) => {
  event.preventDefault();
  console.log("Login button clicked");

  const loginRequest = {
    email: $("#email").val(),
    password: $("#password").val().split(""),
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
      $("#message").text("Login successfull! Welcome, " + result.name);

      saveSession(result);
      window.location.href = "http://localhost:8080/pages/Overview.html";
    } else {
      $("#message").text("Login failed.");
    }
  } catch (err) {
    console.error(err);
    $("#message").text("Error connecting to backend.");
  }
});
