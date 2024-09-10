package com.shf.makerspace.models;

import com.shf.makerspace.application.module.membership.bean.UserMembershipReqBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_membership")
public class UserMembership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Column(name = "time_period")
    private String timePeriod;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_active")
    private Boolean isActive;

    public void initModel(UserMembershipReqBean requestParam) {
        if (!isNullOrEmpty(requestParam.getId())) {
            this.id = requestParam.getId();
        }
        this.timePeriod = requestParam.getTimePeriod();
    }
}
