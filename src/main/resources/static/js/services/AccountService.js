import * as AccountApi from "../apis/AccountCalls.js";
import HTMLBuilder from "../helpers/HTMLBuilder.js";
import {
  handleDeposit,
  handleTransfer,
  handleWithdrawal,
} from "./TransactionService.js";

const builder = new HTMLBuilder();

export async function processAccounts(accessToken) {
  console.log(accessToken);
  const accounts = (await AccountApi.getAccounts(accessToken)).data;
  return accounts;
}
export async function processCreation(accessToken) {
  console.log(accessToken);
  const accounts = (await AccountApi.createAccount(accessToken)).data;
  return accounts;
}

export function buildAccounts(accessToken, accountTokens, holderId) {
  const accounts = Array.isArray(accountTokens)
    ? accountTokens
    : [accountTokens];
  let counter = 0;
  for (let accountToken of accounts) {
    let accountHolder = builder.buildAccount(accountToken, counter);
    registerDepositButton(accountHolder, accessToken);
    registerTransferButton(accountHolder, accessToken);
    registerWithdrawalButtons(accountHolder, accessToken);
    builder.appendElement(accountHolder, holderId);
    counter += 1;
  }
}

function registerDepositButton(accountHolder, accessToken) {
  const depositBTN = accountHolder.querySelector('[id^="deposit"]');
  builder.registerEventListener(depositBTN, "click", (event) => {
    deposit(event, accessToken);
  });
}

function registerTransferButton(accountHolder, accessToken) {
  const transferBTN = accountHolder.querySelector('[id^="transfer"]');
  builder.registerEventListener(transferBTN, "click", (event) => {
    transfer(event, accessToken);
  });
}

function registerWithdrawalButtons(accountHolder, accessToken) {
  const withdrawalButton = accountHolder.querySelector('[id^="withdraw"]');
  builder.registerEventListener(withdrawalButton, "click", (event) => {
    withdraw(event, accessToken);
  });
}

async function deposit(event, accessToken) {
  const account = await handleDeposit(event, accessToken);
  builder.rebuildAccount(account);
}

async function withdraw(event, accessToken) {
  const account = await handleWithdrawal(event, accessToken);
  builder.rebuildAccount(account);
}

async function transfer(event, accessToken) {
  const account = await handleTransfer(event, accessToken);
  builder.rebuildAccount(account);
}
