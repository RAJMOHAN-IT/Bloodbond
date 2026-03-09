package com.randy.bloodbond.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")   // ✅ FIXED: added /api prefix
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")   // ✅ Works because UserService uses builder.roles() which auto-adds ROLE_ prefix
    public String adminDashboard() {
        return "Welcome Admin 👑";
    }
}
