import { registerUser } from "../apis/Access.js";
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
    const accessToken = await registerUser(registrationRequest);
    console.log(accessToken);
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
