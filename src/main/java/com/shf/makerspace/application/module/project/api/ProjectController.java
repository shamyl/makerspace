package com.shf.makerspace.application.module.project.api;

import com.shf.makerspace.application.module.project.bean.ProjectReqBean;
import com.shf.makerspace.application.module.project.bean.ProjectView;
import com.shf.makerspace.application.module.project.service.ProjectService;
import com.shf.makerspace.utils.URIs;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
// @CrossOrigin
@RequestMapping(value = URIs.BASE + URIs.PROJECTS)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Add Project ", description = "Add Project ")
    @PostMapping(value = URIs.SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectView> saveProject(@RequestBody ProjectReqBean requestParam) {
        return ResponseEntity.ok(projectService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Update Project ", description = "Update Project ")
    @PutMapping(value = URIs.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectView> updateProject(@RequestBody ProjectReqBean requestParam) {
        return ResponseEntity.ok(projectService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Delete Project", description = "Delete Project")
    @DeleteMapping(value = URIs.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProject(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(projectService.deleteProjectById(id));
    }

    @Operation(summary = "Get ALL Project ", description = "Get ALL Project ")
    @GetMapping(value = URIs.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectView>> getAllProjects(
            @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        return ResponseEntity.ok(projectService.getAllProjects(isActive));
    }

    @Operation(summary = "Get Project By ID", description = "Get Project By ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectView> getProjectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }
}
