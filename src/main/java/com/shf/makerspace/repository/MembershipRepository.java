package com.shf.makerspace.repository;

import com.shf.makerspace.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Membership findMembershipById(Long id);

    Membership findMembershipByName(String id);

    List<Membership> findMembershipByIsActive(Boolean isActive);
}
