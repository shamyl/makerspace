package com.shf.makerspace.application.module.lab.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabView {
    private Long id;
    private String name;
    private String timePeriod;
    private String createdDate;
    private String startDateTime;
    private String endDateTime;
    private Boolean isActive;
}
