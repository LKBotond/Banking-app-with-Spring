import { processLogout } from "../services/LogoutService.js";
import { on } from "../helpers/Helpers.js";

on("logout", "click", async () => {
  await processLogout();
});
