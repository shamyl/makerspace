package com.shf.makerspace.application.module.documents.api;


import com.shf.makerspace.application.module.documents.bean.DocumentsView;
import com.shf.makerspace.application.module.documents.service.DocumentsService;
import com.shf.makerspace.utils.URIs;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
// @CrossOrigin
@RestController
@RequestMapping(value = URIs.BASE + URIs.DOCUMENTS)
public class DocumentsController {

    Logger logger = LoggerFactory.getLogger(DocumentsController.class);

    final DocumentsService documentsService;

    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @Operation(summary = "Add Documents", description = "save Documents")
    @PostMapping(value = URIs.SAVE)
    public ResponseEntity<List<DocumentsView>> saveDocuments(@RequestParam("files") List<MultipartFile> files,
                                                       @RequestParam("moduleId") Long moduleId,
                                                       @RequestParam("moduleType") String moduleType) {
        return ResponseEntity.ok(documentsService.saveDocuments(files, moduleId, moduleType));
    }

    @Operation(summary = "Update Documents", description = "update Documents")
    @PutMapping(value = URIs.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentsView>> updateDocuments(
                                                      @RequestParam("files") List<MultipartFile> files,
                                                      @RequestParam("moduleId") Long moduleId,
                                                      @RequestParam("moduleType") String moduleType) {
        return ResponseEntity.ok(documentsService.updateDocuments(files, moduleId, moduleType));
    }


    @Operation(summary = "Get ALL Documents of module", description = "Get ALL Documents of module")
    @GetMapping(value = URIs.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentsView>> getAllDocumentsOfModule(@RequestParam("moduleType") String moduleType) {
        return ResponseEntity.ok(documentsService.getAllDocumentsOfModule(moduleType));
    }

    @Operation(summary = "Get Documents of module By ID", description = "Get Documents of module By ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentsView> getDocumentsOfModuleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentsService.getDocumentsOfModuleById(id));
    }

    @Operation(summary = "Get Documents By Module ID", description = "Get Documents By Module ID")
    @GetMapping(value = URIs.BY_MODULE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentsView>> getDocumentsByModuleId(@RequestParam("moduleId") Long moduleId, @RequestParam("moduleType") String moduleType) {
        return ResponseEntity.ok(documentsService.getDocumentsByModuleId(moduleId, moduleType));
    }

    @Operation(summary = "Get Documents By Module IDs", description = "Get Documents By Module IDs")
    @GetMapping(value = URIs.BY_MODULE_IDS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentsView>> getDocumentsByModuleId(@RequestParam("moduleIds") List<Long> moduleIds, @RequestParam("moduleType") String moduleType) {
        return ResponseEntity.ok(documentsService.getDocumentsByModuleId(moduleIds, moduleType));
    }

    @Operation(summary = "DownLoad Documents Of Module By Id", description = "DownLoad Documents Of Module By Id")
    @GetMapping(value = URIs.DOWNLOAD)
    public void downloadDocumentsById(@RequestParam("id") Long id, final HttpServletResponse response) {
        documentsService.downloadDocumentsById(id, response);
    }

    @Operation(summary = "DownLoad All Documents of  Module", description = "DownLoad All Documents of  Module")
    @GetMapping(value = URIs.DOWNLOAD_ALL)
    public void downloadAllDocumentsOfModule(@RequestParam("moduleId") Long moduleId, @RequestParam("moduleType") String moduleType, @RequestHeader("contractId") Long contractId,
                                             final HttpServletResponse response) {
        documentsService.downloadAllDocumentsOfModule(moduleId, moduleType, contractId, response);
    }

}
