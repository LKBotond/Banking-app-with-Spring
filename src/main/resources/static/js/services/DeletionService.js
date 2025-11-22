import { deleteUser } from "../apis/Access.js";
import { loadSession } from "../helpers/Helpers.js";

export async function processDeletion(password) {
  const accessToken = loadSession();
  const deletionRequest = {
    sessionId: accessToken.sessionToken,
    password: password,
  };
  try {
    const response = await deleteUser(deletionRequest);
    if (!response.status) {
      alert("deletion failed, db is down");
    } else {
      window.location.href = "http://localhost:8080/pages/Index.html";
    }
  } catch (e) {
    console.error(e);
  }
}
