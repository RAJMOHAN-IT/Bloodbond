package com.randy.bloodbond.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.randy.bloodbond.entity.Hospital;
import com.randy.bloodbond.repository.HospitalRepository;

@RestController
@RequestMapping("/api/hospitals")   // ✅ FIXED: added /api prefix
public class HospitalController {

    private final HospitalRepository hospitalRepository;

    public HospitalController(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @PostMapping
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }
}
