package com.shf.makerspace.application.module.project.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReqBean {
    private Long id;
    private String name;
    private String description;
    private String url;
    private Boolean isActive = true;
}
