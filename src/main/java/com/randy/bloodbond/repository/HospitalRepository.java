package com.randy.bloodbond.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.randy.bloodbond.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}