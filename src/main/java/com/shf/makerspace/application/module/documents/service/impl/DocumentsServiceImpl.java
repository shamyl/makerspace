package com.shf.makerspace.application.module.documents.service.impl;


import com.shf.makerspace.application.module.common.CommonService;
import com.shf.makerspace.application.module.common.RepoFactory;
import com.shf.makerspace.application.module.documents.bean.DocumentsView;
import com.shf.makerspace.application.module.documents.service.DocumentsService;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.AttachedDocuments;
import com.shf.makerspace.utils.MapperUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;


@Component
@CommonsLog
public class DocumentsServiceImpl implements DocumentsService {


    private final CommonService commonService;
    private final RepoFactory repoFactory;
    @Value("${documents.file.path}")
    private String path;

    public DocumentsServiceImpl(CommonService commonService, RepoFactory repoFactory) {
        this.commonService = commonService;
        this.repoFactory = repoFactory;
    }


    @Override
    public List<DocumentsView> saveDocuments(List<MultipartFile> files, Long moduleId, String moduleType) {
        return saveOrUpdateDocuments(files, moduleId, moduleType);
    }

    @Override
    public List<DocumentsView> updateDocuments(List<MultipartFile> files, Long moduleId, String moduleType) {
        return saveOrUpdateDocuments(files, moduleId, moduleType);
    }

    @Override
    public List<DocumentsView> getAllDocumentsOfModule(String moduleType) {
        List<AttachedDocuments> list = repoFactory.findAllDocumentsByModuleType(moduleType);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return MapperUtil.mapList(list, DocumentsView.class);
    }

    @Override
    public DocumentsView getDocumentsOfModuleById(Long id) {
        AttachedDocuments documentsById = repoFactory.findDocumentsById(id);
        if (isNullOrEmpty(documentsById)) {
            return null;
        }
        return MapperUtil.map(documentsById, DocumentsView.class);
    }

    @Override
    public List<DocumentsView> getDocumentsByModuleId(Long moduleId, String moduleType) {
        List<AttachedDocuments> list = repoFactory.findAllDocumentsByModuleIdAndType(Collections.singletonList(moduleId), moduleType);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return MapperUtil.mapList(list, DocumentsView.class);
    }

    @Override
    public void downloadDocumentsById(Long id, HttpServletResponse response) {
        AttachedDocuments documentsById = repoFactory.findDocumentsById(id);
        commonService.downloadFileById(documentsById.getUrl(), response);
    }

    @Override
    public void downloadAllDocumentsOfModule(Long moduleId, String moduleType, Long contractId, HttpServletResponse response) {
        List<AttachedDocuments> attachedDocuments = repoFactory.findAllDocumentsByModuleIdAndType(Collections.singletonList(moduleId), moduleType);
        List<String> files = new ArrayList<>();
        if (!attachedDocuments.isEmpty()) {
            attachedDocuments.forEach(documents -> {
                files.add(documents.getUrl());
            });

            path = downloadPath(moduleId, moduleType, contractId);
            commonService.downloadAllFile(files, path + moduleType, moduleType + "Documents" + moduleId + ".zip", response);
        }

    }

    private List<DocumentsView> saveOrUpdateDocuments(List<MultipartFile> files, Long moduleId, String moduleType) {
        return getDocumentsView(files, moduleId, moduleType);
    }

    private List<DocumentsView> getDocumentsView(@NotNull List<MultipartFile> files, Long moduleId, String moduleType) {
        try {
            List<AttachedDocuments> list = new ArrayList<>();
            if (!files.isEmpty()) {

                String filePath = createPathOfModule(moduleId, moduleType);
                files.forEach(file -> {
                    System.out.println("path  " + filePath);
                    AttachedDocuments attachment = new AttachedDocuments();
                    commonService.createFile(file, filePath);
                    attachment.setName(file.getOriginalFilename());
                    attachment.setUrl(filePath + "/" + file.getOriginalFilename());
                    attachment.setModuleId(moduleId);
                    attachment.setModuleType(moduleType);
                    list.add(attachment);
                });
            }
            List<AttachedDocuments> attachedDocuments = repoFactory.saveAllDocuments(list);
            if (!attachedDocuments.isEmpty()) {
                return MapperUtil.mapList(attachedDocuments, DocumentsView.class);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e){
            throw new GenericException("Error Occurred during saving Documents", HttpStatus.BAD_REQUEST);
        }
    }

    private String createPathOfModule(Long moduleId, String moduleType) {
        return path + moduleType + "/" + moduleId;
    }

    private String downloadPath(Long moduleId, String moduleType, Long contractId) {

        int year = LocalDate.now().getYear();
        String filePath = path + moduleType + "/" + contractId + "/" + year;
        log.info("file path " + filePath);
        return filePath;
    }

    @Override
    public List<DocumentsView> getDocumentsByModuleId(List<Long> moduleIds, String moduleType) {
        List<AttachedDocuments> documents = repoFactory.findAllDocumentsByModuleIdAndType(moduleIds, moduleType);
        if (documents.isEmpty()) {
            return new ArrayList<>();
        }
        return MapperUtil.mapList(documents, DocumentsView.class);
    }

}
