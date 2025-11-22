import * as AccountApi from "../apis/AccountCalls.js";
import HTMLBuilder from "../helpers/HTMLBuilder.js";

const builder = new HTMLBuilder();

export async function processAccounts(accessToken) {
  console.log(accessToken);
  const accounts = await handleAccountDataRequest(accessToken);
  return accounts;
}
export async function processCreation(accessToken) {
  console.log(accessToken);
  const accounts = await handleCreation(accessToken);
  console.log(accounts);
  return accounts;
}

export async function handleDeposit(accessToken, accountId, sum) {
  const depositRequest = {
    sessionToken: accessToken.sessionToken,
    accountId: accountId,
    sum: sum,
  };
  await AccountApi.depositFunds(depositRequest);
  //rework the backend so after each operation i return stuff for the fronten to unerstand.
  //also fuck busywork
  }

export async function withdraw(params) {}

export function buildAccounts(accountTokens, holderId) {
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
