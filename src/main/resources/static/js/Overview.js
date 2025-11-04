import { loadSession } from "./helpers/Helpers.js";
import HTMLBuilder from "./helpers/HTMLBuilder.js";
const $ = window.jQuery;

const builder = new HTMLBuilder();

$(document).ready(async () => {
  const accessToken = loadSession();
  console.log(accessToken);
  if (!accessToken) return;

  $("#create").on("click", async () => {
    console.log("accessToken: ", accessToken);
    console.log("sessionToken: ", accessToken.sessionToken);
    await processCreation(accessToken);
  });
  $("#logout").on("click", async () => {
    console.log(accessToken);
    await processLogout(accessToken);
  });
  $("#delete").on("click", async () => {
    window.location.href = "http://localhost:8080/pages/DeleteMe.html";
  });
  const accountTokens = await fetchAccounts(accessToken);
  if (!accountTokens) {
    return;
  }
  buildAccounts(accountTokens, "accounts");
});

async function processLogout(accessToken) {
  try {
    const response = await fetch("/access/logout", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(accessToken),
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
    const response = await fetch("/accounts/getAccount", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(accessToken),
    });
    console.log("Response status:", response.status);
    if (response.ok) {
      return response.json();
    } else {
      alert("DAFUQ");
      return null;
    }
  } catch (err) {
    console.error(err);
    return null;
  }
}

async function processCreation(accessToken) {
  const accounts = await handleCreation(accessToken);
  console.log(accounts);
  buildAccounts(accounts, "accounts");
}

async function handleCreation(accessToken) {
  try {
    const response = await fetch("/accounts/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(accessToken),
    });
    console.log("Response status:", response.status);
    if (response.ok) {
      return response.json();
    } else {
      alert("DAFUQ");
      return null;
    }
  } catch (err) {
    console.error(err);
    return null;
  }
}
function buildAccounts(accountTokens, holderId) {
  const accounts = Array.isArray(accountTokens)
    ? accountTokens
    : [accountTokens];
  let counter = 0;
  for (let accountToken of accounts) {
    let account = builder.buildAccount(accountToken, counter);
    builder.appendElement(account, holderId);
    counter += 1;
  }
}
