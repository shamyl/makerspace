package com.shf.makerspace.application.module.course.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReqBean {
    private Long id;
    private String name;
    private String description;
    private String timePeriod;
    private String courseVenue;
    private String startDateTime;
    private String endDateTime;
    private String status;
    private Boolean isActive = true;
}
