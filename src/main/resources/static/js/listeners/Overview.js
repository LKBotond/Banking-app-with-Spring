import { processLogout } from "../services/LogoutService.js";
import { loadSession, on, redirect } from "../helpers/Helpers.js";
import {
  processAccounts,
  processCreation,
  buildAccounts,
  deposit,
} from "../services/AccountService.js";
import {
  extractIdFromEvent,
  getButtonsByIdPrefix,
  massAddEventListener,
} from "../helpers/Scraping.js";

const accessToken = loadSession();

on(document, "DOMContentLoaded", async () => {
  if (!accessToken) {
    return;
  }
  await loadAccounts();

  handleCreation();

  handleLogout();

  handleDeletion();

  handleDepositListeners();
});

async function loadAccounts() {
  const accounts = await processAccounts(accessToken);
  console.log(accounts);
  buildAccounts(accounts, "accounts");
  handleDepositListeners();
}

function handleDepositListeners() {
  const depositButtons = getButtonsByIdPrefix("deposit");
  console.log(depositButtons);
  massAddEventListener(depositButtons, "click", async (event) => {
    console.log("clicked");
    const id = extractIdFromEvent(event);
    console.log(id);
    const input = prompt("Enter a number:");
    const num = parseFloat(input);
    await deposit(accessToken, id, num);
  });
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
    buildAccounts(accounts, "accounts");
  });
}
