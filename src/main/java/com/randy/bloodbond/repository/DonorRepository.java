package com.randy.bloodbond.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.randy.bloodbond.entity.Donor;

public interface DonorRepository 
        extends JpaRepository<Donor, Long>, 
                JpaSpecificationExecutor<Donor> {
}