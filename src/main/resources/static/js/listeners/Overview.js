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
  const accounts = await processAccounts(accessToken);
  console.log(accounts);
  buildAccounts(accounts, "accounts");
});
on("logout", "click", async () => {
  if (!accessToken) {
    return;
  }
  await processLogout(accessToken);
});

on("delete", "click", async () => {
  if (!accessToken) {
    return;
  }
  redirect("http://localhost:8080/pages/DeleteMe.html");
});

on("create", "click", async () => {
  if (!accessToken) {
    return;
  }
  const accounts = await processCreation(accessToken);
  buildAccounts(accounts, "accounts");
});
