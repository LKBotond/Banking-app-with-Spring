import { logoutUser } from "../apis/Logout.js";

export async function processLogout(accessToken) {
  try {
    const response = await logoutUser(accessToken);
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

