package com.shf.makerspace.application.module.membership.service.impl;

import com.shf.makerspace.application.module.common.CommonService;
import com.shf.makerspace.application.module.common.RepoFactory;
import com.shf.makerspace.application.module.membership.bean.MembershipReqBean;
import com.shf.makerspace.application.module.membership.bean.MembershipView;
import com.shf.makerspace.application.module.membership.bean.UserMembershipReqBean;
import com.shf.makerspace.application.module.membership.bean.UserMembershipView;
import com.shf.makerspace.application.module.membership.service.MembershipService;
import com.shf.makerspace.exception.GenericException;
import com.shf.makerspace.models.Membership;
import com.shf.makerspace.models.User;
import com.shf.makerspace.models.UserMembership;
import com.shf.makerspace.utils.DateUtil;
import com.shf.makerspace.utils.MapperUtil;
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
public class MembershipServiceImpl implements MembershipService {

    private final RepoFactory repoFactory;
    private final CommonService commonService;

    @Override
    public MembershipView saveOrUpdate(MembershipReqBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateMembership().apply(requestParam);
        Membership membership = new Membership();
        if (!isNullOrEmpty(requestParam.getId())) {
            membership = repoFactory.findMembershipById(requestParam.getId());
            if (isNullOrEmpty(membership)) {
                throw new GenericException("Membership Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateMembershipByName(requestParam, membership);
        membership.initModel(requestParam);
        setOtherObject(requestParam, membership);
        membership = repoFactory.saveAndFlushMembership(membership);
        return commonService.getMembershipViewByMembershipObj(membership);
    }

    private void setOtherObject(MembershipReqBean requestParam, Membership membership) {
        if (!isNullOrEmpty(requestParam.getStartDateTime())) {
            try {
                membership.setStartTime(DateUtil.convertStringToLocalDateTime(requestParam.getStartDateTime(), DATE_TIME_FORMAT));
            } catch (Exception ex) {
                throw new GenericException("Please Set Start Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
            }
        }
        if (!isNullOrEmpty(requestParam.getEndDateTime())) {
            try {
                membership.setEndTime(DateUtil.convertStringToLocalDateTime(requestParam.getEndDateTime(), DATE_TIME_FORMAT));
            } catch (Exception ex) {
                throw new GenericException("Please Set End Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
            }
        }

    }

    @Override
    public String deleteMembershipById(Long id) {
        Membership memberships = repoFactory.findMembershipById(id);
        if (isNullOrEmpty(memberships)) {
            throw new GenericException("Membership Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        try {
            repoFactory.deleteMembershipById(id);
            return "Membership Deleted Successfully!!!";
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<MembershipView> getAllMemberships(Boolean isActive) {
        List<Membership> membershipsList = new ArrayList<>();
        if (isNullOrEmpty(isActive)) {
            membershipsList = repoFactory.findAllMemberships();
        } else {
            membershipsList = repoFactory.findMembershipByIsActive(isActive);
        }
        if (membershipsList.isEmpty()) {
            return new ArrayList<>();
        }
        List<MembershipView> membershipViews = new ArrayList<>();
        membershipsList.stream().forEach(membership -> {
            membershipViews.add(commonService.getMembershipViewByMembershipObj(membership));
        });
        return membershipViews;
    }

    @Override
    public MembershipView getMembershipById(Long id) {
        Membership membership = repoFactory.findMembershipById(id);
        if (isNullOrEmpty(membership)) {
            throw new GenericException("Membership Details not found against id : " + id + "in System", HttpStatus.BAD_REQUEST);
        }
        return commonService.getMembershipViewByMembershipObj(membership);
    }

    @Override
    public UserMembershipView userTakeMembership(UserMembershipReqBean requestParam) {
        RequestValidator.ValidationResult res = (RequestValidator.ValidationResult) RequestValidator.validateUserTakeMembership().apply(requestParam);
        UserMembership userMembership = new UserMembership();
        if (!isNullOrEmpty(requestParam.getId())) {
            userMembership = repoFactory.findUserMembershipById(requestParam.getId());
            if (isNullOrEmpty(userMembership)) {
                throw new GenericException("Course Enrollment Details not found against id : " + requestParam.getId() + "in System", HttpStatus.BAD_REQUEST);
            }
        }
        validateUserMembership(requestParam, userMembership);
        userMembership.initModel(requestParam);
        userMembership.setIsActive(Boolean.TRUE);
        setOtherObjectUserMembership(requestParam, userMembership);
        userMembership = repoFactory.saveUserMembership(userMembership);
        UserMembershipView userMembershipView = MapperUtil.map(userMembership, UserMembershipView.class);
        getOtherObjectsUserMembership(userMembershipView, userMembership);
        return userMembershipView;
    }

    @Override
    public List<UserMembershipView> getMembershipByUserId(Long userId) {
        List<UserMembership> userMembershipList = repoFactory.findUserMembershipByUserId(userId);
        if (userMembershipList.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserMembershipView> userMembershipViewArrayList = new ArrayList<>();
        userMembershipList.stream().forEach(userMembership -> {
            UserMembershipView userMembershipView = MapperUtil.map(userMembership, UserMembershipView.class);
            getOtherObjectsUserMembership(userMembershipView, userMembership);
            userMembershipViewArrayList.add(userMembershipView);
        });
        return userMembershipViewArrayList;
    }

    private void validateMembershipByName(MembershipReqBean requestParam, Membership membership) {
        Membership membershipsByName = repoFactory.findMembershipByName(requestParam.getName());
        if (!isNullOrEmpty(membershipsByName) && (isNullOrEmpty(membership) || !membershipsByName.getId().equals(membership.getId()))) {
            throw new GenericException("Membership name is already existed!!!", HttpStatus.FOUND);
        }
    }

    private void validateUserMembership(UserMembershipReqBean requestParam, UserMembership userMembership) {
        try {
            userMembership.setStartTime(DateUtil.convertStringToLocalDateTime(requestParam.getStartDateTime(), DATE_TIME_FORMAT));
        } catch (Exception ex) {
            throw new GenericException("Please Set Start Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
        }
        try {
            userMembership.setEndTime(DateUtil.convertStringToLocalDateTime(requestParam.getEndDateTime(), DATE_TIME_FORMAT));
        } catch (Exception ex) {
            throw new GenericException("Please Set End Date Time like : YYYY-MM-DD HH:MM:SS", HttpStatus.NOT_FOUND);
        }
        UserMembership userMembershipValidate = repoFactory.findUserMembershipByUserIdAndCourseIdAndDate(requestParam.getUserId(), requestParam.getMembershipId(), userMembership.getStartTime(), userMembership.getEndTime());
        if (!isNullOrEmpty(userMembershipValidate) && (isNullOrEmpty(userMembership) || !userMembershipValidate.getId().equals(userMembership.getId()))) {
            throw new GenericException("User Membership is already assigned to this user in selected Start And End Time Interval!!!", HttpStatus.FOUND);
        }
    }

    private void setOtherObjectUserMembership(UserMembershipReqBean requestParam, UserMembership userMembership) {
        User user = repoFactory.findUserById(requestParam.getUserId());
        if (!isNullOrEmpty(user)) {
            userMembership.setUser(user);
        } else {
            throw new GenericException("User Details not found against id : " + requestParam.getUserId() + "in System", HttpStatus.BAD_REQUEST);
        }
        Membership membership = repoFactory.findMembershipById(requestParam.getMembershipId());
        if (!isNullOrEmpty(membership)) {
            userMembership.setMembership(membership);
        } else {
            throw new GenericException("Membership Details not found against id : " + requestParam.getMembershipId() + "in System", HttpStatus.BAD_REQUEST);
        }
    }

    private void getOtherObjectsUserMembership(UserMembershipView userMembershipView, UserMembership userMembership) {
        if (!isNullOrEmpty(userMembership.getStartTime())) {
            userMembershipView.setStartDateTime(DateUtil.convertLocalDateTimeToString(userMembership.getStartTime(), DATE_TIME_FORMAT));
        }
        if (!isNullOrEmpty(userMembership.getEndTime())) {
            userMembershipView.setEndDateTime(DateUtil.convertLocalDateTimeToString(userMembership.getEndTime(), DATE_TIME_FORMAT));
        }
        userMembershipView.setMembershipId(userMembership.getMembership().getId());
        userMembershipView.setUserId(userMembership.getUser().getId());
        userMembershipView.setMembershipView(commonService.getMembershipViewByMembershipObj(userMembership.getMembership()));
        userMembershipView.setUserView(commonService.getUserViewByUserObj(userMembership.getUser()));
    }
}
