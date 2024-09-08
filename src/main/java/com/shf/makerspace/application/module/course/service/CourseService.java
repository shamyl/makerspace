package com.shf.makerspace.application.module.course.service;

import com.shf.makerspace.application.module.course.bean.CourseAssignmentReqBean;
import com.shf.makerspace.application.module.course.bean.CourseAssignmentView;
import com.shf.makerspace.application.module.course.bean.CourseReqBean;
import com.shf.makerspace.application.module.course.bean.CourseView;

import java.util.List;

public interface CourseService {
    CourseView saveOrUpdate(CourseReqBean requestParam);

    String deleteCourseById(Long id);

    List<CourseView> getAllCourses(Boolean isActive);

    CourseView getCourseById(Long id);

    CourseAssignmentView userCourseAssignment(CourseAssignmentReqBean requestParam);

    List<CourseAssignmentView> getCoursesByUserId(Long userId);
}
