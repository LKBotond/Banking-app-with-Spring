import { handleOperation } from "./Call.js";

export const handleLogin = (loginRequest) =>
  handleOperation("/access/login", loginRequest);

export const handleRegistration = (registrationRequest) =>
  handleOperation("/access/register", registrationRequest);
