const BASE_URL = "/api";

// ======================================================
// REGISTER
// ======================================================
async function registerUser(event) {
    event.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.getElementById("role").value.toUpperCase();

    const data = { username, password, role };

    try {
        const response = await fetch(BASE_URL + "/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        const message = await response.text();

        if (response.ok) {
            alert("Registration Successful!");
            window.location.href = "/login.html";
        } else {
            alert("Registration Failed: " + message);
        }

    } catch (error) {
        alert("Server Error!");
        console.error(error);
    }
}

// ======================================================
// LOGIN
// ======================================================
async function loginUser(event) {
    event.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    const data = { username, password };

    try {
        const response = await fetch(BASE_URL + "/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const msg = await response.text();
            alert("Login Failed: " + msg);
            return;
        }

        const result = await response.json();

        localStorage.setItem("token", result.token);
        localStorage.setItem("role", result.role);
        localStorage.setItem("username", result.username); // ✅ Save username

        redirectByRole(result.role);

    } catch (error) {
        alert("Server Error!");
        console.error(error);
    }
}

// ======================================================
// ROLE BASED REDIRECT
// ======================================================
function redirectByRole(role) {
    if (role === "DONOR") {
        window.location.href = "/pages/donor/dashboard.html";
    } else if (role === "HOSPITAL") {
        window.location.href = "/pages/hospital/dashboard.html";
    } else if (role === "ADMIN") {
        window.location.href = "/pages/admin/dashboard.html";
    } else {
        window.location.href = "/index.html";
    }
}

// ======================================================
// LOGOUT
// ======================================================
function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("username");
    localStorage.removeItem("donorId");
    window.location.href = "/login.html";
}

// ======================================================
// AUTHORIZED FETCH
// ======================================================
async function authorizedFetch(url, options = {}) {

    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/login.html";
        return;
    }

    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token,
        "Content-Type": "application/json"
    };

    const response = await fetch(url, options);

    if (response.status === 401 || response.status === 403) {
        alert("Session Expired. Please Login Again.");
        logout();
        return;
    }

    return response;
}