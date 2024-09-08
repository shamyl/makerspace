package com.shf.makerspace.application.module.course.bean;

import com.shf.makerspace.application.module.user.bean.UserView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseAssignmentView {
    private Long id;
    private Long userId;
    private UserView userView;
    private Long courseId;
    private CourseView courseView;
    private String timePeriod;
    private String status;
    private String earnedCertificate;
    private String courseVenue;
    private String startDateTime;
    private String endDateTime;
}
