import { handleOperation } from "./Call";

export const handleDeposit = (depositRequest) =>
  handleOperation("account/deposit", depositRequest);

export const handleCreation = (creationRequest) =>
  handleOperation("account/create", creationRequest);

export const handleWithdrawal = (withdrawalRequest) =>
  handleOperation("account/withdraw", withdrawalRequest);

export const handleTransfer = (transferRequest) =>
  handleOperation("account/transfer", transferRequest);

export const handleAccountDataRequest = (accountRequest) =>
  handleOperation("account/getAccounts", accountRequest);
