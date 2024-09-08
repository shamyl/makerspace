package com.shf.makerspace.application.module.common;

import com.shf.makerspace.models.CourseEnrollment;
import com.shf.makerspace.models.Courses;
import com.shf.makerspace.models.Labs;
import com.shf.makerspace.models.Membership;
import com.shf.makerspace.models.Project;
import com.shf.makerspace.models.User;
import com.shf.makerspace.models.UserType;
import com.shf.makerspace.repository.CourseEnrollmentRepository;
import com.shf.makerspace.repository.CourseRepository;
import com.shf.makerspace.repository.LabBookingRepository;
import com.shf.makerspace.repository.LabsRepository;
import com.shf.makerspace.repository.MembershipRepository;
import com.shf.makerspace.repository.ProjectRepository;
import com.shf.makerspace.repository.UserMembershipRepository;
import com.shf.makerspace.repository.UserProjectRepository;
import com.shf.makerspace.repository.UserRepository;
import com.shf.makerspace.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RepoFactory {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final CourseRepository courseRepository;
    private final LabsRepository labsRepository;
    private final ProjectRepository projectRepository;
    private final MembershipRepository membershipRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final LabBookingRepository labBookingRepository;
    private final UserMembershipRepository userMembershipRepository;
    private final UserProjectRepository userProjectRepository;

    // TODO USERS
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserByUserName(String username) {
        return userRepository.findUserByUserName(username);
    }

    public UserType findUserTypeById(Long id) {
        return userTypeRepository.findUserTypeById(id);
    }

    public UserType findUserTypeByName(String name) {
        return userTypeRepository.findUserTypeByName(name);
    }

    public User saveAndFlushUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserType> findAllUserTypes() {
        return userTypeRepository.findAll();
    }

    // TODO Courses
    public Courses findCoursesById(Long id) {
        return courseRepository.findCoursesById(id);
    }

    public Courses findCoursesByName(String name) {
        return courseRepository.findCoursesByName(name);
    }

    public List<Courses> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Courses> findCoursesByIsActive(Boolean isActive) {
        return courseRepository.findCoursesByIsActive(isActive);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    public Courses saveAndFlushCourses(Courses course) {
        return courseRepository.saveAndFlush(course);
    }

    // TODO PROJECTS
    public Project findProjectById(Long id) {
        return projectRepository.findProjectById(id);
    }

    public Project findProjectByName(String name) {
        return projectRepository.findProjectByName(name);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> findProjectByIsActive(Boolean isActive) {
        return projectRepository.findProjectByIsActive(isActive);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

    public Project saveAndFlushProject(Project project) {
        return projectRepository.saveAndFlush(project);
    }


    // TODO LABS
    public Labs findLabsById(Long id) {
        return labsRepository.findLabsById(id);
    }

    public Labs findLabsByName(String name) {
        return labsRepository.findLabsByName(name);
    }

    public List<Labs> findAllLabs() {
        return labsRepository.findAll();
    }

    public List<Labs> findLabsByIsActive(Boolean isActive) {
        return labsRepository.findLabsByIsActive(isActive);
    }

    public void deleteLabById(Long id) {
        labsRepository.deleteById(id);
    }

    public Labs saveAndFlushLabs(Labs lab) {
        return labsRepository.saveAndFlush(lab);
    }

    // TODO MEMBERSHIP
    public Membership findMembershipById(Long id) {
        return membershipRepository.findMembershipById(id);
    }

    public Membership findMembershipByName(String name) {
        return membershipRepository.findMembershipByName(name);
    }

    public List<Membership> findAllMemberships() {
        return membershipRepository.findAll();
    }

    public List<Membership> findMembershipByIsActive(Boolean isActive) {
        return membershipRepository.findMembershipByIsActive(isActive);
    }

    public void deleteMembershipById(Long id) {
        membershipRepository.deleteById(id);
    }

    public Membership saveAndFlushMembership(Membership membership) {
        return membershipRepository.saveAndFlush(membership);
    }

    //TODO COURSE ENROLLMENT
    public CourseEnrollment findCourseEnrollmentById(Long id) {
        return courseEnrollmentRepository.findCourseEnrollmentById(id);
    }

    public CourseEnrollment findCourseEnrollmentByUserIdAndCourseIdAndDate(Long userId, Long courseId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return courseEnrollmentRepository.findCourseEnrollmentByUserIdAndCourseIdAndDate(userId, courseId, dateFrom, dateTo);
    }

    public CourseEnrollment saveCourseEnrollment(CourseEnrollment courseEnrollment) {
        return courseEnrollmentRepository.saveAndFlush(courseEnrollment);
    }

    public List<CourseEnrollment> findCourseEnrollmentByUserId(Long userId) {
        return courseEnrollmentRepository.findCourseEnrollmentByUser_Id(userId);
    }

}
