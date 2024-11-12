package com.shf.makerspace.application.module.course.api;

import com.shf.makerspace.application.module.course.bean.CourseAssignmentReqBean;
import com.shf.makerspace.application.module.course.bean.CourseAssignmentView;
import com.shf.makerspace.application.module.course.bean.CourseReqBean;
import com.shf.makerspace.application.module.course.bean.CourseView;
import com.shf.makerspace.application.module.course.service.CourseService;
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
@RequestMapping(value = URIs.BASE + URIs.COURSES)
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Add Course ", description = "Add Course ")
    @PostMapping(value = URIs.SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseView> saveCourse(@RequestBody CourseReqBean requestParam) {
        return ResponseEntity.ok(courseService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Update Course ", description = "Update Course ")
    @PutMapping(value = URIs.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseView> updateCourse(@RequestBody CourseReqBean requestParam) {
        return ResponseEntity.ok(courseService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Delete Course", description = "Delete Course")
    @DeleteMapping(value = URIs.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCourse(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(courseService.deleteCourseById(id));
    }

    @Operation(summary = "Get ALL Course ", description = "Get ALL Course ")
    @GetMapping(value = URIs.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseView>> getAllCourses(
            @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        return ResponseEntity.ok(courseService.getAllCourses(isActive));
    }

    @Operation(summary = "Get Course By ID", description = "Get Course By ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseView> getCourseById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @Operation(summary = "User Course Assignment", description = "User Course Assignment")
    @PostMapping(value = URIs.ASSIGNMENT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseAssignmentView> userCourseAssignment(@RequestBody CourseAssignmentReqBean requestParam) {
        return ResponseEntity.ok(courseService.userCourseAssignment(requestParam));
    }

    @Operation(summary = "Get User Assigned Courses", description = "Get User Assigned Courses")
    @GetMapping(value = URIs.GET_USER_COURSES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseAssignmentView>> getCoursesByUserId(@RequestParam(value = "userId") Long userId) {
        return ResponseEntity.ok(courseService.getCoursesByUserId(userId));
    }

    @Operation(summary = "Get Course Status", description = "Get Course Status")
    @GetMapping(value = URIs.GET_COURSE_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getCourseStatus() {
        return ResponseEntity.ok(courseService.getCourseStatus());
    }

    @Operation(summary = "Get Course Assignment Status", description = "Get Course Assignment Status")
    @GetMapping(value = URIs.GET_COURSE_ASSIGNMENT_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getCourseAssignmentStatus() {
        return ResponseEntity.ok(courseService.getCourseAssignmentStatus());
    }

    @Operation(summary = "Get Time Period List", description = "Get Time Period List")
    @GetMapping(value = URIs.GET_TIME_PERIOD_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getTimePeriodList() {
        return ResponseEntity.ok(courseService.getTimePeriodList());
    }
}
