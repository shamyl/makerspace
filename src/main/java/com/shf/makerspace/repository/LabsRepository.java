package com.shf.makerspace.repository;

import com.shf.makerspace.models.Labs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabsRepository extends JpaRepository<Labs, Long> {
    Labs findLabsById(Long id);

    Labs findLabsByName(String name);

    List<Labs> findLabsByIsActive(Boolean isActive);
}
