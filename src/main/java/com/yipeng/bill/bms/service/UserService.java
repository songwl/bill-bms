package com.yipeng.bill.bms.service;


import com.yipeng.bill.bms.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 17/3/4.
 */
public interface UserService {

    int saveUser(User user);

    User getUserById(Long id);

    List<User> findList(Map<String,Object> params);

}
