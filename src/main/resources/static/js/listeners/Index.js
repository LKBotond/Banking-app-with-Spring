import { redirect } from "../helpers/Helpers";
document.getElementById("register").addEventListener("click", (event) => {
  event.preventDefault();
  redirect("http://localhost:8080/pages/Registration.html");
});
document.getElementById("login").addEventListener("click", (event) => {
  event.preventDefault();
  redirect("http://localhost:8080/pages/Login.html");
});
