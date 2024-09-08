package com.shf.makerspace.models;

import com.shf.makerspace.application.module.lab.bean.LabReqBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "labs")
public class Labs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time_period")
    private String timePeriod;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "image")
    private String image;

    public void initModel(LabReqBean requestParam) {
        if (!isNullOrEmpty(requestParam.getId())) {
            this.id = requestParam.getId();
        } else {
            this.createdDate = LocalDateTime.now();
        }
        this.name = requestParam.getName();
        this.timePeriod = requestParam.getTimePeriod();
        this.isActive = requestParam.getIsActive();
    }
}
