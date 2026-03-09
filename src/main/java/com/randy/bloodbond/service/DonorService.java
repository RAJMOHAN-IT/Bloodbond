package com.randy.bloodbond.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;

import com.randy.bloodbond.entity.Donor;
//import com.randy.bloodbond.entity.Hospital;
import com.randy.bloodbond.repository.DonorRepository;
import com.randy.bloodbond.exception.DonorNotFoundException;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new DonorNotFoundException("Donor not found with id: " + id));
    }

    public Page<Donor> filterDonors(
            String bloodGroup,
            String city,
            Integer minAge,
            Integer maxAge,
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Specification<Donor> spec = (Root<Donor> root,
                                     CriteriaQuery<?> query,
                                     CriteriaBuilder cb) -> {

            Predicate predicate = cb.conjunction();

            if (bloodGroup != null && !bloodGroup.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("bloodGroup"), bloodGroup));
            }

            if (city != null && !city.isEmpty()) {
                // ✅ LIKE search — "chen" will match "Chennai"
                String cityPattern = "%" + city.toLowerCase() + "%";
                Predicate cityOnDonor = cb.like(cb.lower(root.get("city")), cityPattern);
                predicate = cb.and(predicate, cityOnDonor);
            }

            if (minAge != null) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(root.get("age"), minAge));
            }

            if (maxAge != null) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(root.get("age"), maxAge));
            }

            return predicate;
        };

        return donorRepository.findAll(spec, pageable);
    }
}