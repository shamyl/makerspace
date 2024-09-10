package com.shf.makerspace.application.module.membership.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMembershipReqBean {
    private Long id;
    private Long userId;
    private Long membershipId;
    private String timePeriod;
    private String startDateTime;
    private String endDateTime;
}
