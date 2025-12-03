import { extractIdFromEvent, getFloatFromPrompt } from "../helpers/Scraping.js";
import {
  transferFunds,
  depositFunds,
  withdrawFunds,
} from "../apis/AccountCalls.js";

export async function handleDeposit(event, accessToken) {
  const id = extractIdFromEvent(event);
  console.log(id);
  const sum = getFloatFromPrompt();
  if (!sum) return;
  const depositRequest = {
    sessionToken: accessToken.sessionToken,
    accountId: id,
    sum: sum,
  };
  const accountResponse = await depositFunds(depositRequest);
  if (accountResponse.ok) {
    return accountResponse.data;
  } else return null;
}

export async function handleTransfer(event, accessToken) {
  const id = extractIdFromEvent(event);
  console.log(id);
  const receiver = getFloatFromPrompt();
  const sum = getFloatFromPrompt();
  if (!receiver || !sum) return;
  const transactionRequest = {
    sessionToken: accessToken.sessionToken,
    sender: id,
    receiver: receiver,
    sum: sum,
  };
  const response = await transferFunds(transactionRequest);
  if (response.ok) {
    return response.data;
  } else return null;
}

export async function handleWithdrawal(event, accessToken) {
  const id = extractIdFromEvent(event);
  console.log(id);
  const sum = getFloatFromPrompt();
  if (!sum) return;
  const withdrawalRequest = {
    sessionToken: accessToken.sessionToken,
    accountId: id,
    sum: sum,
  };
  const accountResponse = await withdrawFunds(withdrawalRequest);
  if (accountResponse.ok) {
    return accountResponse.data;
  } else return null;
}
