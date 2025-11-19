import { on, validateFormData } from "../helpers/Helpers.js";
import { processDeletion } from "../services/DeletionService.js";
import { serializeFormToJSON } from "../helpers/Scraping.js";
on("deletionForm", "submit", async (e) => {
  e.preventDefault();
  const formData = serializeFormToJSON(e.target);
  if (!validateFormData(formData)) {
    alert("You must provide a password");
    return;
  }

  let success = await processDeletion(formData.password);
  if (!success) {
    return;
  }

  redirect("http://localhost:8080/pages/Index.html");
});
