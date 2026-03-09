# Bloodbond

# 🩸 BloodBond — Real-Time Blood Donation Management System

A full-stack web application connecting **Donors**, **Hospitals**, and **Admins** 
for real-time blood donation management.

## 🚀 Tech Stack
- **Backend:** Spring Boot 4.0, Java 17, Spring Security, JWT
- **Frontend:** HTML, CSS, JavaScript
- **Database:** MySQL 8.0
- **Authentication:** JWT Role-based (ADMIN, DONOR, HOSPITAL)

## ✅ Features
- 🔐 JWT Authentication with 3 roles
- 🔍 Search donors by blood group + city
- 🏥 Hospital sends blood requests
- 👑 Admin approves/rejects requests
- 🔔 Real-time donor notifications with hospital contact

## 👤 Roles
| Role | Access |
|---|---|
| Hospital | Search donors, send blood requests |
| Admin | View and approve/reject all requests |
| Donor | View approved notifications on dashboard |
