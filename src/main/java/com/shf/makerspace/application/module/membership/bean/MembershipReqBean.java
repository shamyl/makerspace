package com.shf.makerspace.application.module.membership.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipReqBean {
    private Long id;
    private String name;
    private String description;
    private String timePeriod;
    private String startDateTime;
    private String endDateTime;
    private Boolean isActive = true;
}
