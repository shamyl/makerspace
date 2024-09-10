package com.shf.makerspace.application.module.lab.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLabReqBean {
    private Long id;
    private Long userId;
    private Long labId;
    private String timePeriod;
    private String status;
    private String venue;
    private String startDateTime;
    private String endDateTime;
}
