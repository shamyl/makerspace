package com.shf.makerspace.repository;

import com.shf.makerspace.models.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
    UserMembership findUserMembershipById(Long id);

    @Query(value = "select userMembership from UserMembership userMembership where " +
            "userMembership.user.id = :userId and userMembership.membership.id = :courseId and " +
            "(userMembership.startTime is NOT NULL and userMembership.endTime is NOT NULL " +
            "and (userMembership.startTime >= :dateFrom and userMembership.endTime <= :dateTo))")
    UserMembership findUserMembershipByUserIdAndCourseIdAndDate(Long userId, Long courseId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<UserMembership> findUserMembershipByUser_Id(Long userId);
}
