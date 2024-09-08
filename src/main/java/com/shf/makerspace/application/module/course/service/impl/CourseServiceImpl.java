package com.shf.makerspace.application.module.course.service.impl;

import com.shf.makerspace.Enum.CourseStatusEnum;
import com.shf.makerspace.Enum.EnrolllCourseStatusEnum;
import com.shf.makerspace.Enum.TimePeriodEnum;
import com.shf.makerspace.application.module.common.CommonService;
import com.shf.makerspace.application.module.common.RepoFactory;
import com.shf.makerspace.application.module.course.bean.CourseAssignmentReqBean;
import com.shf.makerspace.application.module.course.bean.CourseAssignmentView;
import com.shf.makerspace.application.module.course.bean.CourseReqBean;
import com.shf.makerspace.application.module.course.bean.CourseView;
import com.shf.makerspace.application.module.course.service.CourseService;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.CourseEnrollment;
import com.shf.makerspace.models.Courses;
import com.shf.makerspace.models.User;
import com.shf.makerspace.utils.DateUtil;
import com.shf.makerspace.utils.MapperUtil;
import com.shf.makerspace.validation.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.shf.makerspace.utils.StringUtils.DATE_TIME_FORMAT;
import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;

@Component
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final RepoFactory repoFactory;
    private final CommonService commonService;

    @Override
    public CourseView saveOrUpdate(CourseReqBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateCourse().apply(requestParam);
        Courses course = new Courses();
        if (!isNullOrEmpty(requestParam.getId())) {
            course = repoFactory.findCoursesById(requestParam.getId());
            if (isNullOrEmpty(course)) {
                throw new GenericException("Course Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateCourseByName(requestParam, course);
        course.initModel(requestParam);
        setOtherObject(requestParam, course);
        course = repoFactory.saveAndFlushCourses(course);
        return commonService.getCourseViewByCourseObj(course);
    }

    private void setOtherObject(CourseReqBean requestParam, Courses course) {
        if (!isNullOrEmpty(requestParam.getStartDateTime())) {
            try {
                course.setStartTime(DateUtil.convertStringToLocalDateTime(requestParam.getStartDateTime(), DATE_TIME_FORMAT));
            } catch (Exception ex) {
                throw new GenericException("Please Set Start Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
            }
        }
        if (!isNullOrEmpty(requestParam.getEndDateTime())) {
            try {
                course.setEndTime(DateUtil.convertStringToLocalDateTime(requestParam.getEndDateTime(), DATE_TIME_FORMAT));
            } catch (Exception ex) {
                throw new GenericException("Please Set End Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
            }
        }

    }

    @Override
    public String deleteCourseById(Long id) {
        Courses courses = repoFactory.findCoursesById(id);
        if (isNullOrEmpty(courses)) {
            throw new GenericException("Course Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        try {
            repoFactory.deleteCourseById(id);
            return "Course Deleted Successfully!!!";
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<CourseView> getAllCourses(Boolean isActive) {
        List<Courses> coursesList = new ArrayList<>();
        if (isNullOrEmpty(isActive)) {
            coursesList = repoFactory.findAllCourses();
        } else {
            coursesList = repoFactory.findCoursesByIsActive(isActive);
        }
        if (coursesList.isEmpty()) {
            return new ArrayList<>();
        }
        List<CourseView> courseViews = new ArrayList<>();
        coursesList.stream().forEach(course -> {
            courseViews.add(commonService.getCourseViewByCourseObj(course));
        });
        return courseViews;
    }

    @Override
    public CourseView getCourseById(Long id) {
        Courses course = repoFactory.findCoursesById(id);
        if (isNullOrEmpty(course)) {
            throw new GenericException("Course Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        return commonService.getCourseViewByCourseObj(course);
    }

    @Override
    public CourseAssignmentView userCourseAssignment(CourseAssignmentReqBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateCourseAssignment().apply(requestParam);
        CourseEnrollment courseEnrollment = new CourseEnrollment();
        if (!isNullOrEmpty(requestParam.getId())) {
            courseEnrollment = repoFactory.findCourseEnrollmentById(requestParam.getId());
            if (isNullOrEmpty(courseEnrollment)) {
                throw new GenericException("Course Enrollment Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateCourseEnrollment(requestParam, courseEnrollment);
        courseEnrollment.initModel(requestParam);
        setOtherObjectCourseEnrollment(requestParam, courseEnrollment);
        courseEnrollment = repoFactory.saveCourseEnrollment(courseEnrollment);
        CourseAssignmentView courseAssignmentView = MapperUtil.map(courseEnrollment, CourseAssignmentView.class);
        getOtherObjectsCourseEnrollment(courseAssignmentView, courseEnrollment);
        return courseAssignmentView;
    }

    @Override
    public List<CourseAssignmentView> getCoursesByUserId(Long userId) {
        List<CourseEnrollment> courseEnrollmentList = repoFactory.findCourseEnrollmentByUserId(userId);
        if (courseEnrollmentList.isEmpty()) {
            return new ArrayList<>();
        }
        List<CourseAssignmentView> courseAssignmentViewList = new ArrayList<>();
        courseEnrollmentList.stream().forEach(courseEnrollment -> {
            CourseAssignmentView courseAssignmentView = MapperUtil.map(courseEnrollment, CourseAssignmentView.class);
            getOtherObjectsCourseEnrollment(courseAssignmentView, courseEnrollment);
            courseAssignmentViewList.add(courseAssignmentView);
        });
        return courseAssignmentViewList;
    }

    @Override
    public List<String> getCourseStatus() {
        List<String> courseStatus = new ArrayList<>();
        courseStatus.add(CourseStatusEnum.AVAILABLE.getValue());
        courseStatus.add(CourseStatusEnum.COMING_SOON.getValue());
        return courseStatus;
    }

    @Override
    public List<String> getCourseAssignmentStatus() {
        List<String> courseAssignmentStatus = new ArrayList<>();
        courseAssignmentStatus.add(EnrolllCourseStatusEnum.ONGOING.getValue());
        courseAssignmentStatus.add(EnrolllCourseStatusEnum.COMPLETE.getValue());
        return courseAssignmentStatus;
    }

    @Override
    public List<String> getTimePeriodList() {
        List<String> timePeriod = new ArrayList<>();
        timePeriod.add(TimePeriodEnum.DAYS_15.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_1.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_2.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_3.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_4.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_5.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_6.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_7.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_8.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_9.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_10.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_11.getValue());
        timePeriod.add(TimePeriodEnum.MONTH_12.getValue());
        timePeriod.add(TimePeriodEnum.YEAR_1.getValue());
        return timePeriod;
    }

    private void setOtherObjectCourseEnrollment(CourseAssignmentReqBean requestParam, CourseEnrollment courseEnrollment) {
        User user = repoFactory.findUserById(requestParam.getUserId());
        if (!isNullOrEmpty(user)) {
            courseEnrollment.setUser(user);
        } else {
            throw new GenericException("User Details not found against id : " + requestParam.getUserId() + "in System", HttpStatus.BAD_REQUEST);
        }
        Courses courses = repoFactory.findCoursesById(requestParam.getCourseId());
        if (!isNullOrEmpty(courses)) {
            courseEnrollment.setCourse(courses);
        } else {
            throw new GenericException("Course Details not found against id : " + requestParam.getCourseId() + "in System", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateCourseEnrollment(CourseAssignmentReqBean requestParam, CourseEnrollment courseEnrollment) {
        try {
            courseEnrollment.setStartTime(DateUtil.convertStringToLocalDateTime(requestParam.getStartDateTime(), DATE_TIME_FORMAT));
        } catch (Exception ex) {
            throw new GenericException("Please Set Start Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
        }
        try {
            courseEnrollment.setEndTime(DateUtil.convertStringToLocalDateTime(requestParam.getEndDateTime(), DATE_TIME_FORMAT));
        } catch (Exception ex) {
            throw new GenericException("Please Set End Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
        }
        CourseEnrollment coursesValidate = repoFactory.findCourseEnrollmentByUserIdAndCourseIdAndDate(requestParam.getUserId(), requestParam.getCourseId(), courseEnrollment.getStartTime(), courseEnrollment.getEndTime());
        if (!isNullOrEmpty(coursesValidate) && (isNullOrEmpty(courseEnrollment) || !coursesValidate.getId().equals(courseEnrollment.getId()))) {
            throw new GenericException("Course is already assigned to this user in selected Start And End Time Interval!!!", HttpStatus.FOUND);
        }
    }

    private void validateCourseByName(CourseReqBean requestParam, Courses course) {
        Courses coursesByName = repoFactory.findCoursesByName(requestParam.getName());
        if (!isNullOrEmpty(coursesByName) && (isNullOrEmpty(course) || !coursesByName.getId().equals(course.getId()))) {
            throw new GenericException("Course is already existed!!!", HttpStatus.FOUND);
        }
    }

    private void getOtherObjectsCourseEnrollment(CourseAssignmentView courseAssignmentView, CourseEnrollment courseEnrollment) {
        if (!isNullOrEmpty(courseEnrollment.getStartTime())) {
            courseAssignmentView.setStartDateTime(DateUtil.convertLocalDateTimeToString(courseEnrollment.getStartTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(courseEnrollment.getEndTime())) {
            courseAssignmentView.setEndDateTime(DateUtil.convertLocalDateTimeToString(courseEnrollment.getEndTime(), DATE_TIME_FORMAT));
        }
        courseAssignmentView.setCourseId(courseEnrollment.getCourse().getId());
        courseAssignmentView.setUserId(courseEnrollment.getUser().getId());
        courseAssignmentView.setCourseView(commonService.getCourseViewByCourseObj(courseEnrollment.getCourse()));
        courseAssignmentView.setUserView(commonService.getUserViewByUserObj(courseEnrollment.getUser()));
    }
}
