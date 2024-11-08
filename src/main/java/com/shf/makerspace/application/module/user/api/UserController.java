package com.shf.makerspace.application.module.user.api;

import com.shf.makerspace.application.module.user.bean.GenericDropdownView;
import com.shf.makerspace.application.module.user.bean.LoginBean;
import com.shf.makerspace.application.module.user.bean.UserRequestBean;
import com.shf.makerspace.application.module.user.bean.UserView;
import com.shf.makerspace.application.module.user.service.UserService;
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
@CrossOrigin(origins = "http://35.232.175.135")
// @CrossOrigin
@RequestMapping(value = URIs.BASE + URIs.USERS)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add User ", description = "Add User ")
    @PostMapping(value = URIs.SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> saveUser(@RequestBody UserRequestBean requestParam) {
        return ResponseEntity.ok(userService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Update User ", description = "Update User ")
    @PutMapping(value = URIs.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> updateUser(@RequestBody UserRequestBean requestParam) {
        return ResponseEntity.ok(userService.saveOrUpdate(requestParam));
    }

    @Operation(summary = "Delete User", description = "Delete User")
    @DeleteMapping(value = URIs.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @Operation(summary = "Get ALL User ", description = "Get ALL User ")
    @GetMapping(value = URIs.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserView>> getAllUsers(
            @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        return ResponseEntity.ok(userService.getAllUser(isActive));
    }

    @Operation(summary = "Get User By ID", description = "Get User By ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Login User ", description = "Login User ")
    @PostMapping(value = URIs.LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> login(@RequestBody LoginBean requestBean) {
        return ResponseEntity.ok(userService.authenticate(requestBean));
    }

    @Operation(summary = "Get ALL User Type", description = "Get ALL User ")
    @GetMapping(value = URIs.TYPE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenericDropdownView>> getAllUserTypes() {
        return ResponseEntity.ok(userService.getAllUserTypes());
    }
}
