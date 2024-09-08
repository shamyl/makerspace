package com.shf.makerspace.application.module.user.service;

import com.shf.makerspace.application.module.user.bean.GenericDropdownView;
import com.shf.makerspace.application.module.user.bean.LoginBean;
import com.shf.makerspace.application.module.user.bean.UserRequestBean;
import com.shf.makerspace.application.module.user.bean.UserView;

import java.util.List;

public interface UserService {
    UserView saveOrUpdate(UserRequestBean requestParam);

    String deleteUserById(Long id);

    List<UserView> getAllUser(Boolean isActive);

    UserView getUserById(Long id);

    UserView authenticate(LoginBean requestBean);

    List<GenericDropdownView> getAllUserTypes();
}
