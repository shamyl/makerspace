package com.shf.makerspace.application.module.lab.service.impl;

import com.shf.makerspace.application.module.common.CommonService;
import com.shf.makerspace.application.module.common.RepoFactory;
import com.shf.makerspace.application.module.lab.bean.LabReqBean;
import com.shf.makerspace.application.module.lab.bean.LabView;
import com.shf.makerspace.application.module.lab.service.LabService;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.Labs;
import com.shf.makerspace.utils.DateUtil;
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

    private void validateLabByName(LabReqBean requestParam, Labs lab) {
        Labs labsByName = repoFactory.findLabsByName(requestParam.getName());
        if (!isNullOrEmpty(labsByName) && (isNullOrEmpty(lab) || !labsByName.getId().equals(lab.getId()))) {
            throw new GenericException("Lab name is already existed!!!", HttpStatus.FOUND);
        }
    }
}
