package com.chinalife.datacheck.service;

import com.chinalife.datacheck.models.User;

/**
 * @author: hxy
 * @description: 登录Service
 * @date: 2017/10/24 11:02
 */
public interface LoginService {
    /**
     * 登录表单提交
     *
     * @param jsonObject
     * @return
     */
//    User authLogin(User jsonObject);

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User getUser(String username, String password);

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return
     */
//    User getInfo();

    /**
     * 退出登录
     *
     * @return
     */
//    User logout();
    
    String checkAccount(String user_id);
}
