import { postJsonRequest } from "./Call.js";

export const loginUser = (loginRequest) =>
  postJsonRequest("/access/login", loginRequest);

export const registerUser = (registrationRequest) =>
  postJsonRequest("/access/register", registrationRequest);

export const deleteUser = (deletionRequest) =>
  postJsonRequest("/access/delete", deletionRequest);
