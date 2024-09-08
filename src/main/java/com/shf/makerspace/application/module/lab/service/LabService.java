package com.shf.makerspace.application.module.lab.service;

import com.shf.makerspace.application.module.lab.bean.LabReqBean;
import com.shf.makerspace.application.module.lab.bean.LabView;

import java.util.List;

public interface LabService {
    LabView saveOrUpdate(LabReqBean requestParam);

    String deleteLabById(Long id);

    List<LabView> getAllLabs(Boolean isActive);

    LabView getLabById(Long id);
}
