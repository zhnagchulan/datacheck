package com.chinalife.datacheck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinalife.datacheck.models.User;

public interface LoginDao {
    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User getUser(@Param("username") String username, @Param("password") String password);
    List<User> count();
    String queryUser_id(@Param("user_id") String user_id);
}
