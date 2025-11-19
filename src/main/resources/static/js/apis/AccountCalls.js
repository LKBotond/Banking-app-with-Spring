import { handleOperation } from "./Call.js";

export const handleDeposit = (depositRequest) =>
  handleOperation("accounts/deposit", depositRequest);

export const handleCreation = (accessToken) =>
  handleOperation("/accounts/create", accessToken);

export const handleWithdrawal = (withdrawalRequest) =>
  handleOperation("/accounts/withdraw", withdrawalRequest);

export const handleTransfer = (transferRequest) =>
  handleOperation("/accounts/transfer", transferRequest);

export const handleAccountDataRequest = (accessToken) =>
  handleOperation("/accounts/getAccounts", accessToken);