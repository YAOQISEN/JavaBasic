package com.yqs.service;

import com.yqs.bean.User;
import org.springframework.stereotype.Service;

/**
 * @author yqs 2021/1/13
 */
public interface LoginService {
    User getUserByName(String getMapByName);
}
