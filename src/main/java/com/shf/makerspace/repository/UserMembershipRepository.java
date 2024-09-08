package com.shf.makerspace.repository;

import com.shf.makerspace.models.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
    UserMembership findUserMembershipById(Long id);
}
