package com.shf.makerspace.validation;

import com.shf.makerspace.application.module.course.bean.CourseAssignmentReqBean;
import com.shf.makerspace.application.module.course.bean.CourseReqBean;
import com.shf.makerspace.application.module.lab.bean.LabReqBean;
import com.shf.makerspace.application.module.membership.bean.MembershipReqBean;
import com.shf.makerspace.application.module.project.bean.ProjectReqBean;
import com.shf.makerspace.application.module.user.bean.UserRequestBean;
import com.shf.makerspace.exception.GenericException;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;

public interface RequestValidator<T> extends Function<T, RequestValidator.ValidationResult> {
    static RequestValidator validateUser() {
        return t -> {
            UserRequestBean requestParam = (UserRequestBean) t;
            if (isNullOrEmpty(requestParam.getUserName())) {
                throw new GenericException("Please provide valid input for username!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getEmail())) {
                throw new GenericException("Please provide valid input for email!!!", HttpStatus.BAD_REQUEST);
            }

            if (isNullOrEmpty(requestParam.getPassword())) {
                throw new GenericException("Please provide valid input for password!!!", HttpStatus.BAD_REQUEST);
            }

            if (isNullOrEmpty(requestParam.getUserTypeId()) || requestParam.getUserTypeId() <= 0) {
                throw new GenericException("Please provide valid input for user type!!!", HttpStatus.BAD_REQUEST);
            }

            return new ValidationResult(HttpStatus.OK.value(), "Success");
        };
    }

    static RequestValidator validateCourse() {
        return t -> {
            CourseReqBean requestParam = (CourseReqBean) t;
            if (isNullOrEmpty(requestParam.getName())) {
                throw new GenericException("Please provide valid input for name!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getDescription())) {
                throw new GenericException("Please provide valid input for description!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getTimePeriod())) {
                throw new GenericException("Please provide valid input for Time Period!!!", HttpStatus.BAD_REQUEST);
            }

            return new ValidationResult(HttpStatus.OK.value(), "Success");
        };
    }

    static RequestValidator validateProject() {
        return t -> {
            ProjectReqBean requestParam = (ProjectReqBean) t;
            if (isNullOrEmpty(requestParam.getName())) {
                throw new GenericException("Please provide valid input for name!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getDescription())) {
                throw new GenericException("Please provide valid input for description!!!", HttpStatus.BAD_REQUEST);
            }
            return new ValidationResult(HttpStatus.OK.value(), "Success");
        };
    }

    static RequestValidator validateLab() {
        return t -> {
            LabReqBean requestParam = (LabReqBean) t;
            if (isNullOrEmpty(requestParam.getName())) {
                throw new GenericException("Please provide valid input for name!!!", HttpStatus.BAD_REQUEST);
            }
            return new ValidationResult(HttpStatus.OK.value(), "Success");
        };
    }

    static RequestValidator validateMembership() {
        return t -> {
            MembershipReqBean requestParam = (MembershipReqBean) t;
            if (isNullOrEmpty(requestParam.getName())) {
                throw new GenericException("Please provide valid input for name!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getDescription())) {
                throw new GenericException("Please provide valid input for description!!!", HttpStatus.BAD_REQUEST);
            }
            return new ValidationResult(HttpStatus.OK.value(), "Success");
        };
    }

    static RequestValidator validateCourseAssignment() {
        return t -> {
            CourseAssignmentReqBean requestParam = (CourseAssignmentReqBean) t;
            if (isNullOrEmpty(requestParam.getUserId()) || requestParam.getUserId() <= 0) {
                throw new GenericException("Please provide valid input for user id!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getCourseId()) || requestParam.getCourseId() <= 0) {
                throw new GenericException("Please provide valid input for course id!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getTimePeriod())) {
                throw new GenericException("Please provide valid input for time period!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getStatus())) {
                throw new GenericException("Please provide valid input for status!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getCourseVenue())) {
                throw new GenericException("Please provide valid input for venue!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getStartDateTime())) {
                throw new GenericException("Please provide valid input start date time!!!", HttpStatus.BAD_REQUEST);
            }
            if (isNullOrEmpty(requestParam.getEndDateTime())) {
                throw new GenericException("Please provide valid input end date time!!!", HttpStatus.BAD_REQUEST);
            }

            return new ValidationResult(HttpStatus.OK.value(), "Success");
        };
    }


    class ValidationResult {
        private int statusCode;
        private String msg;

        private ValidationResult(int statusCode, String msg) {
            this.statusCode = statusCode;
            this.msg = msg;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMsg() {
            return msg;
        }
    }
}
