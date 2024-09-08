package com.shf.makerspace.repository;

import com.shf.makerspace.models.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    CourseEnrollment findCourseEnrollmentById(Long id);

    @Query(value = "select courseEnrollment from CourseEnrollment courseEnrollment where " +
            "courseEnrollment.user.id = :userId and courseEnrollment.course.id = :courseId and " +
            "(courseEnrollment.startTime is NOT NULL and courseEnrollment.endTime is NOT NULL " +
            "and (courseEnrollment.startTime >= :dateFrom and courseEnrollment.endTime <= :dateTo))")
    CourseEnrollment findCourseEnrollmentByUserIdAndCourseIdAndDate(Long userId, Long courseId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<CourseEnrollment> findCourseEnrollmentByUser_Id(Long userId);
}
