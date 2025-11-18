import { handleRegistration } from "../apis/Access.js";
import { saveSession } from "../helpers/Helpers.js";

export async function processRegistration(
  firstName,
  lastName,
  email,
  password
) {
  const registrationRequest = {
    firstName: firstName,
    lastName: lastName,
    email: email,
    password: password,
  };
  try {
    const accessToken = await handleRegistration(registrationRequest);
    console.log(accessToken);
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
