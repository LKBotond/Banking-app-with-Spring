import { handleOperation } from "./Call.js";
export const handleLogout = (logOutRequest) =>
  handleOperation("/access/logout", logOutRequest);
