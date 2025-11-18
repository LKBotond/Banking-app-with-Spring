import { loadSession } from "./helpers/Helpers.js";
import HTMLBuilder from "./helpers/HTMLBuilder.js";
import {
  handleCreation,
  handleDeposit,
  handleWithdrawal,
  handleTransfer,
  handleAccountRequest,
} from "./helpers/AccountAPI.js";

const builder = new HTMLBuilder();
const accessToken = loadSession();
console.log(accessToken);

//controll flow
if (accessToken) {
  document.addEventListener("click", async (event) => {
    await determineAccountAction(event);
    await visualizeAccounts();
  });
}

//logic
async function determineAccountAction(event) {
  if (id.startsWith("transfer")) action = "transfer";
  else if (id.startsWith("deposit")) action = "deposit";
  else if (id.startsWith("details")) action = "details";
  else action = id;

  switch (action) {
    case "transfer":
      console.log("TRANSFER →", event.target.dataset.accountId);
      break;

    case "deposit":
      console.log("DEPOSIT →", event.target.dataset.accountId);
      processDeposit();
      break;

    case "details":
      console.log("DETAILS →", event.target.dataset.accountId);
      break;

    case "create":
      console.log("accessToken: ", accessToken);
      console.log("sessionToken: ", accessToken.sessionToken);
      await processCreation(accessToken);
      break;

    case "logout":
      console.log(accessToken);
      await processLogout(accessToken);
      break;

    case "delete":
      window.location.href = "http://localhost:8080/pages/DeleteMe.html";
      break;

    default:
      console.log("Unknown action:", id);
  }
}

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

async function processCreation(accessToken) {
  const accounts = await handleCreation("create", accessToken);
  console.log(accounts);
  buildAccounts(accounts, "accounts");
}
async function processDeposit(accountId) {
  let sum = getValue();
  if (!sum) return;
  const depositRequest = {
    sessionToken: accessToken.sessionToken,
    accountId: accountId,
    sum: sum,
  };
  await handleDeposit(depositRequest);
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

function createDTO(accessToken, id, sum) {}

function getValue() {
  while (true) {
    let input = prompt("Enter amount:");

    if (input === null) return null;

    input = input.trim().replace(",", ".");
   
    if (/^\d+(\.\d+)?$/.test(input)) {
      return input;
    }
  }
}

async function visualizeAccounts() {
  const accountTokens = await handleAccountRequest(accessToken);
  if (!accountTokens) {
    return;
  }
  buildAccounts(accountTokens, "accounts");
}
