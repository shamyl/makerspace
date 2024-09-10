package com.shf.makerspace.application.module.lab.bean;

import com.shf.makerspace.application.module.user.bean.UserView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLabView {
    private Long id;
    private Long userId;
    private UserView userView;
    private Long labId;
    private LabView labView;
    private String timePeriod;
    private String status;
    private String venue;
    private String startDateTime;
    private String endDateTime;
}
