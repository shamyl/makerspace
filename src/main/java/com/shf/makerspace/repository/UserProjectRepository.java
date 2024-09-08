package com.shf.makerspace.repository;

import com.shf.makerspace.models.UserProjects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProjects, Long> {
    UserProjects findUserProjectsById(Long id);
}
