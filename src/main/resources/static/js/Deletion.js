import { loadSession } from "./helpers/Helpers.js";
const $ = window.jQuery;
$("#deletionForm").on("submit", async (e) => {
  e.preventDefault();
  const accessToken = loadSession();
  console.log(accessToken);
  if (!accessToken) {
    return;
  }
  const deletionRequest = {
    sessionId: accessToken.sessionToken,
    password: $("#password").val(),
  };
  console.log(deletionRequest);
  try {
    const response = await fetch("/access/delete", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(deletionRequest),
    });
    console.log("Response status:", response.status);
    if (response.ok) {
      $("#message").text(
        "Deletion successfull! YOur User and accounts have been deleted. "
      );

      window.location.href = "http://localhost:8080/pages/Index.html";
    } else {
      $("#message").text("Deletion failed.");
    }
  } catch (err) {
    console.error(err);
    $("#message").text("Error connecting to backend.");
  }
});
