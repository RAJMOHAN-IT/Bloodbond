package com.randy.bloodbond.repository;

import com.randy.bloodbond.entity.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    // Get all requests by status — used by Admin
    List<BloodRequest> findByStatus(String status);

    // Get all requests for a specific donor — used for notifications
    List<BloodRequest> findByDonorIdAndStatus(Long donorId, String status);
}