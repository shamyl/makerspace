package com.shf.makerspace.utils;

public class URIs {

    private URIs() {
    }

    public static final String MIME_TYPE_NOT_DETECTABLE = "mimetype is not detectable, will take default";
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String MIME_TYPE = "mimetype : ";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";


    public static final String BASE = "/makerspace";
    public static final String USERS = "/user";
    public static final String COURSES = "/course";
    public static final String LABS = "/lab";
    public static final String PROJECTS = "/project";
    public static final String MEMBERSHIP = "/membership";
    public static final String DOCUMENTS = "/documents";
    // generic Url
    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String ALL = "/all";
    public static final String ID = "/{id}";
    public static final String LOGIN = "/login";
    public static final String TYPE = "/type";
    public static final String ASSIGNMENT = "/assignment";
    public static final String GET_USER_COURSES = "/getCoursesByUserId";
    public static final String GET_COURSE_STATUS = "/getCourseStatus";
    public static final String GET_COURSE_ASSIGNMENT_STATUS = "/getCourseAssignmentStatus";
    public static final String GET_TIME_PERIOD_LIST = "/getTimePeriodList";
    public static final String USER_TAKE_MEMBERSHIP = "/userTakeMembership";
    public static final String GET_USER_MEMBERSHIP = "/getMembershipByUserId";
    public static final String USER_LAB_BOOKING = "/userLabBooking";
    public static final String GET_USER_LABS = "/getLabsByUserId";
    public static final String BY_MODULE_ID = "by-module-id";
    public static final String BY_MODULE_IDS = "/by-module-ids";
    public static final String DOWNLOAD = "/documents-download";
    public static final String DOWNLOAD_ALL = "/download-all-docments";
}
