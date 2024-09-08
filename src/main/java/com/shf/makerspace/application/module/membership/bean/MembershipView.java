package com.shf.makerspace.application.module.membership.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipView {
    private Long id;
    private String name;
    private String timePeriod;
    private String createdDate;
    private String startDateTime;
    private String endDateTime;
    private Boolean isActive;
}
