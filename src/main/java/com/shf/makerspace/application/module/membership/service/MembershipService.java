package com.shf.makerspace.application.module.membership.service;


import com.shf.makerspace.application.module.membership.bean.MembershipReqBean;
import com.shf.makerspace.application.module.membership.bean.MembershipView;

import java.util.List;

public interface MembershipService {
    MembershipView saveOrUpdate(MembershipReqBean requestParam);

    String deleteMembershipById(Long id);

    List<MembershipView> getAllMemberships(Boolean isActive);

    MembershipView getMembershipById(Long id);
}
