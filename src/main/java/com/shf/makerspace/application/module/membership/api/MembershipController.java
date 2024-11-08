package com.shf.makerspace.application.module.membership.api;

import com.shf.makerspace.application.module.membership.bean.MembershipReqBean;
import com.shf.makerspace.application.module.membership.bean.MembershipView;
import com.shf.makerspace.application.module.membership.bean.UserMembershipReqBean;
import com.shf.makerspace.application.module.membership.bean.UserMembershipView;
import com.shf.makerspace.application.module.membership.service.MembershipService;
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
@CrossOrigin(origins = "http://localhost:4200/")
// @CrossOrigin
@RequestMapping(value = URIs.BASE + URIs.MEMBERSHIP)
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @Operation(summary = "Add Membership ", description = "Add Membership ")
    @PostMapping(value = URIs.SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MembershipView> saveMembership(@RequestBody MembershipReqBean requestParam) {
        return ResponseEntity.ok(membershipService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Update Membership ", description = "Update Membership ")
    @PutMapping(value = URIs.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MembershipView> updateMembership(@RequestBody MembershipReqBean requestParam) {
        return ResponseEntity.ok(membershipService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Delete Membership", description = "Delete Membership")
    @DeleteMapping(value = URIs.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteMembership(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(membershipService.deleteMembershipById(id));
    }

    @Operation(summary = "Get ALL Membership ", description = "Get ALL Membership ")
    @GetMapping(value = URIs.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MembershipView>> getAllMemberships(
            @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        return ResponseEntity.ok(membershipService.getAllMemberships(isActive));
    }

    @Operation(summary = "Get Membership By ID", description = "Get Membership By ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MembershipView> getMembershipById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @Operation(summary = "User Take Membership", description = "User Take Membership")
    @PostMapping(value = URIs.USER_TAKE_MEMBERSHIP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMembershipView> userTakeMembership(@RequestBody UserMembershipReqBean requestParam) {
        return ResponseEntity.ok(membershipService.userTakeMembership(requestParam));
    }

    @Operation(summary = "Get User Membership", description = "Get User Membership")
    @GetMapping(value = URIs.GET_USER_MEMBERSHIP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserMembershipView>> getMembershipByUserId(@RequestParam(value = "userId") Long userId) {
        return ResponseEntity.ok(membershipService.getMembershipByUserId(userId));
    }
}
