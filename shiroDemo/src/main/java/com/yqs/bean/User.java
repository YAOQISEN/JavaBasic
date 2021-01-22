package com.yqs.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author yqs 2021/1/13
 */
@Data
@AllArgsConstructor
public class User {
    private String id;
    private String userName;
    private String password;
    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
