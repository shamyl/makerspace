package com.shf.makerspace.application.module.documents.service;

import com.shf.makerspace.application.module.documents.bean.DocumentsView;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentsService {

    List<DocumentsView> saveDocuments(List<MultipartFile> files, Long moduleId, String moduleType);

    List<DocumentsView> updateDocuments(List<MultipartFile> files, Long moduleId, String moduleType);

    List<DocumentsView> getAllDocumentsOfModule(String moduleType);

    DocumentsView getDocumentsOfModuleById(Long id);

    List<DocumentsView> getDocumentsByModuleId(Long moduleId, String moduleType);

    void downloadDocumentsById(Long id, HttpServletResponse response);

    void downloadAllDocumentsOfModule(Long moduleId, String moduleType, Long contractId, HttpServletResponse response);

    List<DocumentsView> getDocumentsByModuleId(List<Long> moduleIds, String moduleType);
}
