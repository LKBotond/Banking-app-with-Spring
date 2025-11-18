import { handleLogin } from "../apis/Access.js";
import { saveSession } from "../helpers/Helpers.js";

export async function processLogin(email, password) {
  try {
    const loginRequest = {
      email: email,
      password: password,
    };
    const accessToken = await handleLogin(loginRequest);
    if (!accessToken) {
      alert("Invalid access Token Login failed");
      return false;
    }
    saveSession(accessToken);
    return true;
  } catch (e) {
    console.log(e);
    return false;
  }
}
