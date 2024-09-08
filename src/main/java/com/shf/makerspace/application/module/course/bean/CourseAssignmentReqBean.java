package com.shf.makerspace.application.module.course.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseAssignmentReqBean {
    private Long id;
    private Long userId;
    private Long courseId;
    private String timePeriod;
    private String status;
    private String earnedCertificate;
    private String courseVenue;
    private String startDateTime;
    private String endDateTime;
}
