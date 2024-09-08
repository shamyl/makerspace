package com.shf.makerspace.application.module.project.service.impl;

import com.shf.makerspace.application.module.common.CommonService;
import com.shf.makerspace.application.module.common.RepoFactory;
import com.shf.makerspace.application.module.project.bean.ProjectReqBean;
import com.shf.makerspace.application.module.project.bean.ProjectView;
import com.shf.makerspace.application.module.project.service.ProjectService;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.Project;
import com.shf.makerspace.validation.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;

@Component
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final RepoFactory repoFactory;
    private final CommonService commonService;

    @Override
    public ProjectView saveOrUpdate(ProjectReqBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateProject().apply(requestParam);
        Project project = new Project();
        if (!isNullOrEmpty(requestParam.getId())) {
            project = repoFactory.findProjectById(requestParam.getId());
            if (isNullOrEmpty(project)) {
                throw new GenericException("Project Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateProjectByName(requestParam, project);
        project.initModel(requestParam);
        project = repoFactory.saveAndFlushProject(project);
        return commonService.getProjectViewByProjectObj(project);
    }


    @Override
    public String deleteProjectById(Long id) {
        Project project = repoFactory.findProjectById(id);
        if (isNullOrEmpty(project)) {
            throw new GenericException("Project Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        try {
            repoFactory.deleteProjectById(id);
            return "Project Deleted Successfully!!!";
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<ProjectView> getAllProjects(Boolean isActive) {
        List<Project> projectList = new ArrayList<>();
        if (isNullOrEmpty(isActive)) {
            projectList = repoFactory.findAllProjects();
        } else {
            projectList = repoFactory.findProjectByIsActive(isActive);
        }
        if (projectList.isEmpty()) {
            return new ArrayList<>();
        }
        List<ProjectView> projectViews = new ArrayList<>();
        projectList.stream().forEach(project -> {
            projectViews.add(commonService.getProjectViewByProjectObj(project));
        });
        return projectViews;
    }

    @Override
    public ProjectView getProjectById(Long id) {
        Project project = repoFactory.findProjectById(id);
        if (isNullOrEmpty(project)) {
            throw new GenericException("Project Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        return commonService.getProjectViewByProjectObj(project);
    }

    private void validateProjectByName(ProjectReqBean requestParam, Project project) {
        Project projectByName = repoFactory.findProjectByName(requestParam.getName());
        if (!isNullOrEmpty(projectByName) && (isNullOrEmpty(project) || !projectByName.getId().equals(project.getId()))) {
            throw new GenericException("Project is already existed!!!", HttpStatus.FOUND);
        }
    }
}
