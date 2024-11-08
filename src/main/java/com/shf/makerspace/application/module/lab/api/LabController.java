package com.shf.makerspace.application.module.lab.api;

import com.shf.makerspace.application.module.lab.bean.LabReqBean;
import com.shf.makerspace.application.module.lab.bean.LabView;
import com.shf.makerspace.application.module.lab.bean.UserLabReqBean;
import com.shf.makerspace.application.module.lab.bean.UserLabView;
import com.shf.makerspace.application.module.lab.service.LabService;
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
// @CrossOrigin(origins = "*")
@CrossOrigin
@RequestMapping(value = URIs.BASE + URIs.LABS)
public class LabController {

    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @Operation(summary = "Add Lab ", description = "Add Lab ")
    @PostMapping(value = URIs.SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabView> saveLab(@RequestBody LabReqBean requestParam) {
        return ResponseEntity.ok(labService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Update Lab ", description = "Update Lab ")
    @PutMapping(value = URIs.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabView> updateLab(@RequestBody LabReqBean requestParam) {
        return ResponseEntity.ok(labService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Delete Lab", description = "Delete Lab")
    @DeleteMapping(value = URIs.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteLab(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(labService.deleteLabById(id));
    }

    @Operation(summary = "Get ALL Lab ", description = "Get ALL Lab ")
    @GetMapping(value = URIs.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LabView>> getAllLabs(
            @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        return ResponseEntity.ok(labService.getAllLabs(isActive));
    }

    @Operation(summary = "Get Lab By ID", description = "Get Lab By ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LabView> getLabById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(labService.getLabById(id));
    }

    @Operation(summary = "User Lab Booking", description = "User Lab Booking")
    @PostMapping(value = URIs.USER_LAB_BOOKING, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLabView> userLabBooking(@RequestBody UserLabReqBean requestParam) {
        return ResponseEntity.ok(labService.userLabBooking(requestParam));
    }

    @Operation(summary = "Get User Labs", description = "Get User Labs")
    @GetMapping(value = URIs.GET_USER_LABS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserLabView>> getLabsByUserId(@RequestParam(value = "userId") Long userId) {
        return ResponseEntity.ok(labService.getLabsByUserId(userId));
    }
}
