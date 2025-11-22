import { postJsonRequest } from "./Call.js";
export const logoutUser = (logOutRequest) =>
  postJsonRequest("/access/logout", logOutRequest);
