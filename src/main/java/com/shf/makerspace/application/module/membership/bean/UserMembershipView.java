package com.shf.makerspace.application.module.membership.bean;

import com.shf.makerspace.application.module.user.bean.UserView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMembershipView {
    private Long id;
    private Long userId;
    private UserView userView;
    private Long membershipId;
    private MembershipView membershipView;
    private String timePeriod;
    private Boolean isActive;
    private String startDateTime;
    private String endDateTime;
}
