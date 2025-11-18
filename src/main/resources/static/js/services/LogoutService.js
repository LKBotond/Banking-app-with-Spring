import { handleLogout } from "../apis/Logout.js";
import { loadSession } from "../helpers/Helpers.js";

export async function processLogout() {
  const accessToken = loadSession();
  try {
    const response = await handleLogout(accessToken);
    console.log("Response status:", response.status);
    if (response.status) {
      window.location.href = "http://localhost:8080/pages/Index.html";
    } else {
      alert("Logout failed, db is down");
    }
  } catch (e) {
    console.error(e);
  }
}

//only need to create logout dtros on the backend
