package com.shf.makerspace.application.module.lab.service.impl;

import com.shf.makerspace.application.module.common.CommonService;
import com.shf.makerspace.application.module.common.RepoFactory;
import com.shf.makerspace.application.module.lab.bean.LabReqBean;
import com.shf.makerspace.application.module.lab.bean.LabView;
import com.shf.makerspace.application.module.lab.bean.UserLabReqBean;
import com.shf.makerspace.application.module.lab.bean.UserLabView;
import com.shf.makerspace.application.module.lab.service.LabService;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.LabBooking;
import com.shf.makerspace.models.Labs;
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
public class LabServiceImpl implements LabService {

    private final RepoFactory repoFactory;
    private final CommonService commonService;

    @Override
    public LabView saveOrUpdate(LabReqBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateLab().apply(requestParam);
        Labs lab = new Labs();
        if (!isNullOrEmpty(requestParam.getId())) {
            lab = repoFactory.findLabsById(requestParam.getId());
            if (isNullOrEmpty(lab)) {
                throw new GenericException("Lab Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateLabByName(requestParam, lab);
        lab.initModel(requestParam);
        setOtherObject(requestParam, lab);
        lab = repoFactory.saveAndFlushLabs(lab);
        return commonService.getLabViewByLabObj(lab);
    }


    private void setOtherObject(LabReqBean requestParam, Labs lab) {
        if (!isNullOrEmpty(requestParam.getStartDateTime())) {
            try {
                lab.setStartTime(DateUtil.convertStringToLocalDateTime(requestParam.getStartDateTime(), DATE_TIME_FORMAT));
            } catch (Exception ex) {
                throw new GenericException("Please Set Start Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
            }
        }
        if (!isNullOrEmpty(requestParam.getEndDateTime())) {
            try {
                lab.setEndTime(DateUtil.convertStringToLocalDateTime(requestParam.getEndDateTime(), DATE_TIME_FORMAT));
            } catch (Exception ex) {
                throw new GenericException("Please Set End Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
            }
        }

    }

    @Override
    public String deleteLabById(Long id) {
        Labs labs = repoFactory.findLabsById(id);
        if (isNullOrEmpty(labs)) {
            throw new GenericException("Lab Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        try {
            repoFactory.deleteLabById(id);
            return "Lab Deleted Successfully!!!";
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<LabView> getAllLabs(Boolean isActive) {
        List<Labs> labsList = new ArrayList<>();
        if (isNullOrEmpty(isActive)) {
            labsList = repoFactory.findAllLabs();
        } else {
            labsList = repoFactory.findLabsByIsActive(isActive);
        }
        if (labsList.isEmpty()) {
            return new ArrayList<>();
        }
        List<LabView> labViews = new ArrayList<>();
        labsList.stream().forEach(lab -> {
            labViews.add(commonService.getLabViewByLabObj(lab));
        });
        return labViews;
    }

    @Override
    public LabView getLabById(Long id) {
        Labs lab = repoFactory.findLabsById(id);
        if (isNullOrEmpty(lab)) {
            throw new GenericException("Lab Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        return commonService.getLabViewByLabObj(lab);
    }

    @Override
    public UserLabView userLabBooking(UserLabReqBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateLabBooking().apply(requestParam);
        LabBooking labBooking = new LabBooking();
        if (!isNullOrEmpty(requestParam.getId())) {
            labBooking = repoFactory.findLabBookingsById(requestParam.getId());
            if (isNullOrEmpty(labBooking)) {
                throw new GenericException("Lab Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateUserLab(requestParam, labBooking);
        labBooking.initModel(requestParam);
        setOtherObjectLabBooking(requestParam, labBooking);
        labBooking = repoFactory.saveLabBooking(labBooking);
        UserLabView userLabView = MapperUtil.map(labBooking, UserLabView.class);
        getOtherObjectsUserLabView(userLabView, labBooking);
        return userLabView;
    }

    @Override
    public List<UserLabView> getLabsByUserId(Long userId) {
        List<LabBooking> labBookings = repoFactory.findLabBookingsByUserId(userId);
        if (labBookings.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserLabView> userLabViews = new ArrayList<>();
        labBookings.stream().forEach(labBooking -> {
            UserLabView userLabView = MapperUtil.map(labBooking, UserLabView.class);
            getOtherObjectsUserLabView(userLabView, labBooking);
            userLabViews.add(userLabView);
        });
        return userLabViews;
    }

    private void validateLabByName(LabReqBean requestParam, Labs lab) {
        Labs labsByName = repoFactory.findLabsByName(requestParam.getName());
        if (!isNullOrEmpty(labsByName) && (isNullOrEmpty(lab) || !labsByName.getId().equals(lab.getId()))) {
            throw new GenericException("Lab name is already existed!!!", HttpStatus.FOUND);
        }
    }

    private void getOtherObjectsUserLabView(UserLabView userLabView, LabBooking labBooking) {
        if (!isNullOrEmpty(labBooking.getStartTime())) {
            userLabView.setStartDateTime(DateUtil.convertLocalDateTimeToString(labBooking.getStartTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(labBooking.getEndTime())) {
            userLabView.setEndDateTime(DateUtil.convertLocalDateTimeToString(labBooking.getEndTime(), DATE_TIME_FORMAT));
        }
        userLabView.setLabId(labBooking.getLab().getId());
        userLabView.setUserId(labBooking.getUser().getId());
        userLabView.setLabView(commonService.getLabViewByLabObj(labBooking.getLab()));
        userLabView.setUserView(commonService.getUserViewByUserObj(labBooking.getUser()));
    }

    private void validateUserLab(UserLabReqBean requestParam, LabBooking labBooking) {
        try {
            labBooking.setStartTime(DateUtil.convertStringToLocalDateTime(requestParam.getStartDateTime(), DATE_TIME_FORMAT));
        } catch (Exception ex) {
            throw new GenericException("Please Set Start Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
        }
        try {
            labBooking.setEndTime(DateUtil.convertStringToLocalDateTime(requestParam.getEndDateTime(), DATE_TIME_FORMAT));
        } catch (Exception ex) {
            throw new GenericException("Please Set End Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
        }
        LabBooking labBookingValidate = repoFactory.findLabBookingByUserIdAndCourseIdAndDate(requestParam.getUserId(), requestParam.getLabId(), labBooking.getStartTime(), labBooking.getEndTime());
        if (!isNullOrEmpty(labBookingValidate) && (isNullOrEmpty(labBooking) || !labBookingValidate.getId().equals(labBooking.getId()))) {
            throw new GenericException("Lab is already assigned to this user in selected Start And End Time Interval!!!", HttpStatus.FOUND);
        }
    }

    private void setOtherObjectLabBooking(UserLabReqBean requestParam, LabBooking labBooking) {
        User user = repoFactory.findUserById(requestParam.getUserId());
        if (!isNullOrEmpty(user)) {
            labBooking.setUser(user);
        } else {
            throw new GenericException("User Details not found against id : " + requestParam.getUserId() + "in System", HttpStatus.BAD_REQUEST);
        }
        Labs labs = repoFactory.findLabsById(requestParam.getLabId());
        if (!isNullOrEmpty(labs)) {
            labBooking.setLab(labs);
        } else {
            throw new GenericException("Lab Details not found against id : " + requestParam.getLabId() + "in System", HttpStatus.BAD_REQUEST);
        }
    }
}
