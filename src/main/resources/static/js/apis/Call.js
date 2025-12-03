export async function postJsonRequest(endpoint, payload) {
  try {
    const response = await fetch(endpoint, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!response.ok) {
      return {
        ok: false,
        status: response.status,
        error: await safeParse(response),
        data: null,
      };
    }
    return {
      ok: true,
      status: response.status,
      data: await response.json(),
      error: null,
    };
  } catch (err) {
    return {
      ok: false,
      status: "network_error",
      error: err.message,
      data: null,
    };
  }
}
async function safeParse(response) {
  try {
    return await response.json();
  } catch {
    return await response.text();
  }
}
