const PREHASH_ITERATIONS = 100000;   


async function prehashPassword(loginId, password) {
    if (!window.crypto || !crypto.subtle) {
        throw new Error("Open this page via http://localhost or https (crypto.subtle is not available).");
    }
    const enc = new TextEncoder();
    const key = await crypto.subtle.importKey(
            "raw", enc.encode(password), "PBKDF2", false, ["deriveBits"]);
    const bits = await crypto.subtle.deriveBits({
        name: "PBKDF2",
        hash: "SHA-256",
        salt: enc.encode("znews-login:" + loginId.trim()),
        iterations: PREHASH_ITERATIONS
    }, key, 256);
    return Array.from(new Uint8Array(bits), b => b.toString(16).padStart(2, "0")).join("");
}

async function postJson(path, body) {
    const resp = await fetch("api" + path, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });
    const data = await resp.json();
    if (!resp.ok) throw new Error(data.error || "Request failed");
    return data;
}

function onSubmit(formId, handler) {
    const form = document.getElementById(formId);
    if (!form) return;                                     
    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const error = document.getElementById("formError");
        error.textContent = "";
        try {
            await handler(form.elements);
        } catch (e) {
            error.textContent = e.message;               
        }
    });
}

onSubmit("loginForm", async (f) => {
    const loginId = f.loginId.value;
    const data = await postJson("/auth/login", {
        loginId: loginId,
        password: await prehashPassword(loginId, f.password.value)
    });
    sessionStorage.setItem("jwt", data.token);           
    location.href = "home.html";
});

onSubmit("registerForm", async (f) => {
    const loginId = f.loginId.value;
    await postJson("/auth/register", {
        name: f.name.value,
        loginId: loginId,
        password: await prehashPassword(loginId, f.password.value)
    });
    location.href = "login.html";
});