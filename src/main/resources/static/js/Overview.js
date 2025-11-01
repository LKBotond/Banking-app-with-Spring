import { loadSession } from "./helpers/Helpers";
import HTMLBuilder from "./helpers/HTMLBuilder";
import Account from "./helpers/Account";

document.addEventListener("DOMContentLoaded", async () => {
  const accessToken = loadSession();
  if (!accessToken) return;

  document.getElementById("logout").addEventListener("click", async () => {
    await processLogout(accessToken);
  });
  document.getElementById("delete").addEventListener("click", async () => {
    window.location.href = "http://localhost:8080/pages/DeleteMe.html";
  });
  const accounts = await fetchAccounts(accessToken);
});

async function processLogout(accessToken) {
  const logoutRequest = {
    sessionId: accessToken.sessionToken,
  };
  try {
    const response = await fetch("/access/logout", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(logoutRequest),
    });
    console.log("Response status:", response.status);
    if (response.ok) {
      window.location.href = "http://localhost:8080/pages/Index.html";
    } else {
      alert("Logout failed, db is down");
    }
  } catch (err) {
    console.error(err);
  }
}

async function fetchAccounts(accessToken) {
  try {
    const response = await fetch("/business/getAccounts", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(accessToken),
    });
    console.log("Response status:", response.status);
    if (response.ok) {
     
    } else {
      alert("DAFUQ");
    }
  } catch (err) {
    console.error(err);
  }
}
