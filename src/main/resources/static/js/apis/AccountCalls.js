import { postJsonRequest } from "./Call.js";

export const createAccount = (accessToken) =>
  postJsonRequest("/accounts/create", accessToken);

export const depositFunds = (depositRequest) =>
  postJsonRequest("/accounts/deposit", depositRequest);

export const withdrawFunds = (withdrawalRequest) =>
  postJsonRequest("/accounts/withdraw", withdrawalRequest);

export const transferFunds = (transferRequest) =>
  postJsonRequest("/accounts/transfer", transferRequest);

export const getAccounts = (accessToken) =>
  postJsonRequest("/accounts/getAccounts", accessToken);
