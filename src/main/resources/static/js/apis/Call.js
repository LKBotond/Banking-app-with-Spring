export async function handleOperation(endpoint, payload) {
  const response = await fetch(endpoint, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  console.log("Response status:", response.status);
  if (response.ok) {
    return await response.json();
  } else {
    alert(response.status);
    return null;
  }
}
