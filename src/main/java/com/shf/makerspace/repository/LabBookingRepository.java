package com.shf.makerspace.repository;

import com.shf.makerspace.models.LabBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LabBookingRepository extends JpaRepository<LabBooking, Long> {
    LabBooking findLabBookingsById(Long id);

    @Query(value = "select labBooking from LabBooking labBooking where " +
            "labBooking.user.id = :userId and labBooking.lab.id = :courseId and " +
            "(labBooking.startTime is NOT NULL and labBooking.endTime is NOT NULL " +
            "and (labBooking.startTime >= :dateFrom and labBooking.endTime <= :dateTo))")
    LabBooking findLabBookingByUserIdAndCourseIdAndDate(Long userId, Long courseId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<LabBooking> findLabBookingsByUser_Id(Long userId);
}
