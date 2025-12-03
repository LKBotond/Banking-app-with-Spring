import { processLogout } from "../services/LogoutService.js";
import { loadSession, on, redirect } from "../helpers/Helpers.js";
import {
  processAccounts,
  processCreation,
  buildAccounts,
} from "../services/AccountService.js";

const accessToken = loadSession();

on(document, "DOMContentLoaded", async () => {
  if (!accessToken) {
    return;
  }
  await loadAccounts();

  handleCreation();

  handleLogout();

  handleDeletion();
});

async function loadAccounts() {
  const accounts = await processAccounts(accessToken);
  console.log(accounts);
  buildAccounts(accessToken, accounts, "accounts");
}

function handleLogout() {
  on("logout", "click", async () => {
    if (!accessToken) {
      return;
    }
    await processLogout(accessToken);
  });
}

function handleDeletion() {
  on("delete", "click", async () => {
    if (!accessToken) {
      return;
    }
    redirect("http://localhost:8080/pages/DeleteMe.html");
  });
}

function handleCreation() {
  on("create", "click", async () => {
    if (!accessToken) {
      return;
    }
    const accounts = await processCreation(accessToken);
    buildAccounts(accessToken, accounts, "accounts");
  });
}
