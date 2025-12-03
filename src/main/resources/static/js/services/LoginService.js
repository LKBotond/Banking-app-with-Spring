import { loginUser } from "../apis/Access.js";
import { saveSession } from "../helpers/Helpers.js";

export async function processLogin(email, password) {
  try {
    const loginRequest = {
      email: email,
      password: password,
    };
    const accessToken = await loginUser(loginRequest);
    if (!accessToken.ok) {
      alert("Invalid access Token Login failed");
      return false;
    }
    saveSession(accessToken.data);
    return true;
  } catch (e) {
    console.log(e);
    return false;
  }
}
