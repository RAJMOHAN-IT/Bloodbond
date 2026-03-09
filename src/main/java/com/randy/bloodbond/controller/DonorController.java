package com.randy.bloodbond.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;

import com.randy.bloodbond.entity.Donor;
import com.randy.bloodbond.repository.DonorRepository;
import com.randy.bloodbond.service.DonorService;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    @Autowired
    private DonorRepository donorRepository;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping
    public Donor createDonor(@RequestBody Donor donor) {
        return donorService.createDonor(donor);
    }

    @GetMapping("/{id}")
    public Donor getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id);
    }

    @GetMapping("/filter")
    public Page<Donor> filterDonors(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return donorService.filterDonors(
                bloodGroup, city, minAge, maxAge,
                page, size, sortBy);
    }

    // ✅ NEW: Get all donors — used to match donor by email/name
    @GetMapping("/all")
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }
}