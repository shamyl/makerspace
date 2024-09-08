package com.shf.makerspace.application.module.project.service;

import com.shf.makerspace.application.module.project.bean.ProjectReqBean;
import com.shf.makerspace.application.module.project.bean.ProjectView;

import java.util.List;

public interface ProjectService {
    ProjectView saveOrUpdate(ProjectReqBean requestParam);

    String deleteProjectById(Long id);

    List<ProjectView> getAllProjects(Boolean isActive);

    ProjectView getProjectById(Long id);
}
