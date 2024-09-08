package com.shf.makerspace.repository;

import com.shf.makerspace.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findProjectById(Long id);

    Project findProjectByName(String name);

    List<Project> findProjectByIsActive(Boolean isActive);
}
