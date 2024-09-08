package com.shf.makerspace.repository;

import com.shf.makerspace.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findUserTypeById(Long id);

    UserType findUserTypeByName(String name);
}
