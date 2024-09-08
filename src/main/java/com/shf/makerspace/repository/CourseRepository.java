package com.shf.makerspace.repository;

import com.shf.makerspace.models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses, Long> {
    Courses findCoursesById(Long id);

    Courses findCoursesByName(String name);

    List<Courses> findCoursesByIsActive(Boolean isActive);
}
