package com.shf.makerspace.application.module.course.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseView {
    private Long id;
    private String name;
    private String description;
    private String timePeriod;
    private String courseVenue;
    private String createdDate;
    private String startDateTime;
    private String status;
    private String endDateTime;
    private Boolean isActive;
}
