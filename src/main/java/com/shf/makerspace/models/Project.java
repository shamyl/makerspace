package com.shf.makerspace.models;

import com.shf.makerspace.application.module.project.bean.ProjectReqBean;
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
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "is_active")
    private Boolean isActive;

    public void initModel(ProjectReqBean requestParam) {
        if (!isNullOrEmpty(requestParam.getId())) {
            this.id = requestParam.getId();
        } else {
            this.createdDate = LocalDateTime.now();
        }
        this.name = requestParam.getName();
        this.description = requestParam.getDescription();
        this.url = requestParam.getUrl();
        this.isActive = requestParam.getIsActive();
    }
}
