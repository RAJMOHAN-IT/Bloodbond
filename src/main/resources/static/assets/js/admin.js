// ===============================
// ADMIN DASHBOARD
// ===============================

async function loadAdminDashboard() {

    const token = localStorage.getItem("token");

    if (!token) {
        alert("Please login first");
        window.location.href = "/login.html";   // ✅ FIXED: correct path
        return;
    }

    try {
        const response = await fetch("/api/admin/dashboard", {   // ✅ FIXED: removed hardcoded localhost, added /api
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!response.ok) {
            throw new Error("Access denied - Admins only!");
        }

        const text = await response.text();
        document.getElementById("adminContent").innerText = text;

    } catch (error) {
        alert(error.message);
    }
}
