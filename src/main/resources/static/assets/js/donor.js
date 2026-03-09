// ===============================
// DONOR DASHBOARD
// ===============================

async function loadDonorDashboard() {

    const token = localStorage.getItem("token");

    if (!token) {
        alert("Please login first");
        window.location.href = "/login.html";   // ✅ FIXED: correct path
        return;
    }

    try {
        const response = await fetch("/api/donors/filter", {   // ✅ FIXED: removed hardcoded localhost, added /api
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!response.ok) {
            throw new Error("Access denied");
        }

        const data = await response.json();
        console.log("Donor data:", data);

    } catch (error) {
        alert(error.message);
    }
}
