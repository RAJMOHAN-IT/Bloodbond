package com.randy.bloodbond.controller;

import com.randy.bloodbond.entity.BloodRequest;
import com.randy.bloodbond.repository.BloodRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blood-requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    // ✅ Hospital sends a request
    @PostMapping
    public ResponseEntity<String> sendRequest(@RequestBody BloodRequest request) {
        bloodRequestRepository.save(request);
        return ResponseEntity.ok("Request sent successfully");
    }

    // ✅ Admin gets all PENDING requests
    @GetMapping("/pending")
    public List<BloodRequest> getPendingRequests() {
        return bloodRequestRepository.findByStatus("PENDING");
    }

    // ✅ Admin approves a request
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long id) {
        BloodRequest request = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("APPROVED");
        bloodRequestRepository.save(request);
        return ResponseEntity.ok("Request approved");
    }

    // ✅ Admin rejects a request
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long id) {
        BloodRequest request = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("REJECTED");
        bloodRequestRepository.save(request);
        return ResponseEntity.ok("Request rejected");
    }

    // ✅ Donor gets their approved notifications
    @GetMapping("/notifications/{donorId}")
    public List<BloodRequest> getDonorNotifications(@PathVariable Long donorId) {
        return bloodRequestRepository.findByDonorIdAndStatus(donorId, "APPROVED");
    }
}