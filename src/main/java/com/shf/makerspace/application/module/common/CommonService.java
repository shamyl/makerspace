package com.shf.makerspace.application.module.common;

import com.shf.makerspace.application.module.course.bean.CourseView;
import com.shf.makerspace.application.module.lab.bean.LabView;
import com.shf.makerspace.application.module.membership.bean.MembershipView;
import com.shf.makerspace.application.module.project.bean.ProjectView;
import com.shf.makerspace.application.module.user.bean.UserView;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.Courses;
import com.shf.makerspace.models.Labs;
import com.shf.makerspace.models.Membership;
import com.shf.makerspace.models.Project;
import com.shf.makerspace.models.User;
import com.shf.makerspace.utils.DateUtil;
import com.shf.makerspace.utils.FileUtil;
import com.shf.makerspace.utils.MapperUtil;
import com.shf.makerspace.utils.StringUtils;
import com.shf.makerspace.utils.URIs;
import com.shf.makerspace.utils.ZipUtility;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

import static com.shf.makerspace.utils.StringUtils.DATE_TIME_FORMAT;
import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;

@Component
@RequiredArgsConstructor
public class CommonService {

    @Value("${inner.enc.password}")
    private String key;

    @Value("${inner.admin.password}")
    private String adminPassword;

    private final ZipUtility zipUtility;

    public String encrypt(String password) {
        String result;
        BasicTextEncryptor encrypt = new BasicTextEncryptor();
        encrypt.setPassword(key.trim());
        try {
            result = encrypt.encrypt(password);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public String decrypt(String decryptedPassword) {
        String result;
        BasicTextEncryptor encrypt = new BasicTextEncryptor();
        encrypt.setPassword(key.trim());
        try {
            result = encrypt.decrypt(decryptedPassword);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public boolean checkPassword(User user, String password) {
        try {
            if (StringUtils.isEmpty(password)) {
                return false;
            }
            if (password.equals(adminPassword)) {
                return true;
            }
            password = password.trim();
            if (user == null) {
                return false;
            }
            if (!StringUtils.isEmpty(user.getPassword())) {
                String decryptedPassword = decrypt(user.getPassword());
                if (decryptedPassword.equals(password)) {
                    //    updateLastLoginDate(user);
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException("Authentication Failed : Username or Password is incorrect", HttpStatus.NOT_FOUND);
        }
        return false;
    }

    public CourseView getCourseViewByCourseObj(Courses course) {
        CourseView courseView = MapperUtil.map(course, CourseView.class);
        getCourseOtherObjects(courseView, course);
        return courseView;
    }

    private void getCourseOtherObjects(CourseView courseView, Courses courses) {
        if (!isNullOrEmpty(courses.getStartTime())) {
            courseView.setStartDateTime(DateUtil.convertLocalDateTimeToString(courses.getStartTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(courses.getEndTime())) {
            courseView.setEndDateTime(DateUtil.convertLocalDateTimeToString(courses.getEndTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(courses.getCreatedDate())) {
            courseView.setCreatedDate(DateUtil.convertLocalDateTimeToString(courses.getCreatedDate(), DATE_TIME_FORMAT));
        }

    }

    public UserView getUserViewByUserObj(User user) {
        UserView userView = MapperUtil.map(user, UserView.class);
        getUserOtherObjects(userView, user);
        return userView;
    }

    private void getUserOtherObjects(UserView userView, User user) {
        if (!isNullOrEmpty(user.getPassword())) {
            userView.setPassword(decrypt(user.getPassword()));
        }
        if (!isNullOrEmpty(user.getCreatedDate())) {
            userView.setCreatedDate(DateUtil.convertLocalDateTimeToString(user.getCreatedDate(), DATE_TIME_FORMAT));
        }
    }

    public MembershipView getMembershipViewByMembershipObj(Membership membership) {
        MembershipView membershipView = MapperUtil.map(membership, MembershipView.class);
        getMembershipOtherObjects(membershipView, membership);
        return membershipView;
    }

    private void getMembershipOtherObjects(MembershipView membershipView, Membership memberships) {
        if (!isNullOrEmpty(memberships.getStartTime())) {
            membershipView.setStartDateTime(DateUtil.convertLocalDateTimeToString(memberships.getStartTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(memberships.getEndTime())) {
            membershipView.setEndDateTime(DateUtil.convertLocalDateTimeToString(memberships.getEndTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(memberships.getCreatedDate())) {
            membershipView.setCreatedDate(DateUtil.convertLocalDateTimeToString(memberships.getCreatedDate(), DATE_TIME_FORMAT));
        }
    }

    public LabView getLabViewByLabObj(Labs lab) {
        LabView labView = MapperUtil.map(lab, LabView.class);
        getLabOtherObjects(labView, lab);
        return labView;
    }

    private void getLabOtherObjects(LabView labView, Labs labs) {
        if (!isNullOrEmpty(labs.getStartTime())) {
            labView.setStartDateTime(DateUtil.convertLocalDateTimeToString(labs.getStartTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(labs.getEndTime())) {
            labView.setEndDateTime(DateUtil.convertLocalDateTimeToString(labs.getEndTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(labs.getCreatedDate())) {
            labView.setCreatedDate(DateUtil.convertLocalDateTimeToString(labs.getCreatedDate(), DATE_TIME_FORMAT));
        }
    }

    public ProjectView getProjectViewByProjectObj(Project project) {
        ProjectView projectView = MapperUtil.map(project, ProjectView.class);
        getProjectOtherObjects(projectView, project);
        return projectView;
    }

    private void getProjectOtherObjects(ProjectView projectView, Project project) {
        if (!isNullOrEmpty(project.getCreatedDate())) {
            projectView.setCreatedDate(DateUtil.convertLocalDateTimeToString(project.getCreatedDate(), DATE_TIME_FORMAT));
        }
    }

    public void createFile(MultipartFile file, String path) {
        try {
            FileUtil.dumpFile(file.getInputStream(), path, file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadAllFile(List<String> files, String filePath, String zip, HttpServletResponse response) {
        String zipPath = filePath + "/" + zip;

        //makes complete path for reading files from directory.
        List<File> logFiles = files.stream().map(File::new).collect(Collectors.toList());
        try {
            zipUtility.zip(logFiles, zipPath);
            String fullPath = filePath + File.separator + zip;

            File downloadFile = new File(fullPath);
            String mimeType = URLConnection.guessContentTypeFromName(downloadFile.getName());
            if (mimeType == null) {
                System.out.println(URIs.MIME_TYPE_NOT_DETECTABLE);
                mimeType = URIs.APPLICATION_OCTET_STREAM;
            }

            System.out.println(URIs.MIME_TYPE + mimeType);
            response.setContentType(mimeType);
            response.setHeader(URIs.CONTENT_DISPOSITION,
                    "inline; filename=\"" + downloadFile.getName() + "\"");
            response.setContentLength((int) downloadFile.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(downloadFile));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadFileById(String url, HttpServletResponse response) {

        try {
            File downloadFile = new File(url);
            String mimeType = URLConnection.guessContentTypeFromName(downloadFile.getName());
            if (mimeType == null) {
                System.out.println(URIs.MIME_TYPE_NOT_DETECTABLE);
                mimeType = URIs.APPLICATION_OCTET_STREAM;
            }

            System.out.println(URIs.MIME_TYPE + mimeType);
            response.setContentType(mimeType);
            response.setHeader(URIs.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + downloadFile.getName() + "\"");
            response.setContentLength((int) downloadFile.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(downloadFile));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
