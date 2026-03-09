// =============================================
// NOTIFICATION SYSTEM — BloodBond
// =============================================

function toggleNotification() {
    const dropdown = document.getElementById("notificationDropdown");
    if (dropdown.style.display === "block") {
        dropdown.style.display = "none";
    } else {
        dropdown.style.display = "block";
    }
}

window.onclick = function(event) {
    const container = document.querySelector(".notification-container");
    const dropdown = document.getElementById("notificationDropdown");
    if (container && !container.contains(event.target)) {
        dropdown.style.display = "none";
    }
};

async function loadNotifications() {
    const token = localStorage.getItem("token");
    if (!token) return;

    let donorId = localStorage.getItem("donorId");
    if (!donorId) {
        donorId = await fetchAndSaveDonorId();
        if (!donorId) return;
    }

    const response = await fetch("/api/blood-requests/notifications/" + donorId, {
        headers: { "Authorization": "Bearer " + token }
    });

    if (!response.ok) return;

    const notifications = await response.json();
    const dropdown = document.getElementById("notificationDropdown");
    const badge = document.getElementById("notificationCount");

    // ✅ Fix dropdown width
    dropdown.style.width = "320px";

    if (notifications.length === 0) {
        badge.innerText = "0";
        dropdown.innerHTML = `<p style="text-align:center; color:#999; padding:10px;">No new notifications</p>`;
        return;
    }

    badge.innerText = notifications.length;

    dropdown.innerHTML = "";
    notifications.forEach(req => {
        const date = new Date(req.createdAt).toLocaleDateString("en-IN");
        dropdown.innerHTML += `
            <div style="
                padding: 14px;
                border-bottom: 1px solid #f0f0f0;
                font-size: 13px;
                color: #333;
            ">
                <div style="color:#c0392b; font-weight:bold; font-size:14px; margin-bottom:8px;">
                    🩸 Blood Request — <span style="color:green;">✅ Approved</span>
                </div>
                <div style="display:grid; grid-template-columns: 100px 1fr; row-gap:4px;">
                    <span style="color:#888;">Hospital</span>
                    <span style="font-weight:bold;">${req.hospitalName || "-"}</span>

                    <span style="color:#888;">City</span>
                    <span>${req.hospitalCity || "-"}</span>

                    <span style="color:#888;">Blood Group</span>
                    <span style="font-weight:bold; color:#c0392b;">${req.bloodGroup || "-"}</span>

                    <span style="color:#888;">📞 Contact</span>
                    <span style="font-weight:bold; color:#2980b9;">${req.hospitalPhone || "-"}</span>

                    <span style="color:#888;">Date</span>
                    <span style="color:#999;">${date}</span>
                </div>
            </div>
        `;
    });
}

async function fetchAndSaveDonorId() {
    const token = localStorage.getItem("token");
    const username = localStorage.getItem("username");
    if (!token || !username) return null;

    const response = await fetch("/api/donors/all", {
        headers: { "Authorization": "Bearer " + token }
    });

    if (!response.ok) return null;

    const donors = await response.json();
    const matched = donors.find(d => d.email === username);

    if (matched) {
        localStorage.setItem("donorId", matched.id);
        return matched.id;
    }

    return null;
}