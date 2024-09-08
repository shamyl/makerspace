package com.shf.makerspace.application.module.user.service.impl;

import com.shf.makerspace.application.module.common.CommonService;
import com.shf.makerspace.application.module.common.RepoFactory;
import com.shf.makerspace.application.module.user.bean.GenericDropdownView;
import com.shf.makerspace.application.module.user.bean.LoginBean;
import com.shf.makerspace.application.module.user.bean.UserRequestBean;
import com.shf.makerspace.application.module.user.bean.UserView;
import com.shf.makerspace.application.module.user.service.UserService;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.User;
import com.shf.makerspace.models.UserType;
import com.shf.makerspace.utils.MapperUtil;
import com.shf.makerspace.validation.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.shf.makerspace.utils.StringUtils.isNullOrEmpty;

@Component
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final RepoFactory repoFactory;
    private final CommonService commonService;

    @Override
    public UserView saveOrUpdate(UserRequestBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateUser().apply(requestParam);
        User user = new User();
        if (!isNullOrEmpty(requestParam.getId())) {
            user = repoFactory.findUserById(requestParam.getId());
            if (isNullOrEmpty(user)) {
                throw new GenericException("User Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateUserEmailOrUserName(requestParam, user);
        user.initModel(requestParam);
        setOtherObject(requestParam, user);
        user = repoFactory.saveAndFlushUser(user);
        return commonService.getUserViewByUserObj(user);
    }


    private void setOtherObject(UserRequestBean requestParam, User user) {
        user.setPassword(commonService.encrypt(requestParam.getPassword()));
        if (!isNullOrEmpty(requestParam.getUserTypeId())) {
            UserType userType = repoFactory.findUserTypeById(requestParam.getUserTypeId());
            if (!isNullOrEmpty(userType)) {
                user.setUserType(userType);
            } else {
                throw new GenericException("User type record not found against user type Id : " + requestParam.getUserTypeId() + "in System", HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public String deleteUserById(Long id) {
        User user = repoFactory.findUserById(id);
        if (isNullOrEmpty(user)) {
            throw new GenericException("User Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        try {
            repoFactory.deleteUserById(id);
            return "User Deleted Successfully!!!";
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<UserView> getAllUser(Boolean isActive) {
        List<User> userList = new ArrayList<>();
        if (isNullOrEmpty(isActive)) {
            userList = repoFactory.findAllUsers();
        } else {
            userList = repoFactory.findAllUsers();
        }
        if (userList.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserView> userViews = new ArrayList<>();
        userList.stream().forEach(user -> {
            userViews.add(commonService.getUserViewByUserObj(user));
        });
        return userViews;
    }

    @Override
    public UserView getUserById(Long id) {
        User user = repoFactory.findUserById(id);
        if (isNullOrEmpty(user)) {
            throw new GenericException("User Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        return commonService.getUserViewByUserObj(user);
    }

    @Override
    public UserView authenticate(LoginBean requestBean) {
        User userExist = repoFactory.findUserByUserName(requestBean.getUsername());
        if (isNullOrEmpty(userExist)) {
            userExist = repoFactory.findUserByEmail(requestBean.getUsername());
        }
        if (!isNullOrEmpty(userExist)) {
            if (commonService.checkPassword(userExist, requestBean.getPassword())) {
                return commonService.getUserViewByUserObj(userExist);
            }
            throw new GenericException("Authentication Failed : Username or Password is incorrect", HttpStatus.NOT_FOUND);
        }

        throw new GenericException("Authentication Failed : User Not Found", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<GenericDropdownView> getAllUserTypes() {
        List<UserType> userTypeList = repoFactory.findAllUserTypes();
        if (userTypeList.isEmpty()) {
            return new ArrayList<>();
        }
        return MapperUtil.mapList(userTypeList, GenericDropdownView.class);
    }


    private void validateUserEmailOrUserName(UserRequestBean requestParam, User user) {
        User userByEmail = repoFactory.findUserByEmail(requestParam.getEmail());
        if (!isNullOrEmpty(userByEmail) && (isNullOrEmpty(user) || !userByEmail.getId().equals(user.getId()))) {
            throw new GenericException("Email is already existed!!!", HttpStatus.FOUND);
        }
        User userByUsername = repoFactory.findUserByUserName(requestParam.getUserName());
        if (!isNullOrEmpty(userByUsername) && (isNullOrEmpty(user) || !userByUsername.getId().equals(user.getId()))) {
            throw new GenericException("User Name is already existed!!!", HttpStatus.FOUND);
        }
    }
}
