package com.shf.makerspace.repository;

import com.shf.makerspace.models.LabBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabBookingRepository extends JpaRepository<LabBooking, Long> {
    LabBooking findLabBookingsById(Long id);
}
