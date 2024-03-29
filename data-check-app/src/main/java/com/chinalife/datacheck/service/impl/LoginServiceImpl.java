package com.chinalife.datacheck.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinalife.datacheck.dao.LoginDao;
import com.chinalife.datacheck.models.User;
import com.chinalife.datacheck.service.LoginService;

/**
 * @author: hxy
 * @description: 登录service实现类
 * @date: 2017/10/24 11:53
 */
@Service
public class LoginServiceImpl implements LoginService {
    private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private LoginDao loginDao;
//    private PermissionService permissionService;

    /**
     * 登录表单提交
     *
     * @param jsonObject
     * @return
     */
//    @Override
//    public User authLogin(User jsonObject) {
//        String username = jsonObject.getString("username");
//        String password = jsonObject.getString("password");
//        User returnData = new User();
//        Subject currentUser = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        try {
//            currentUser.login(token);
//            returnData.put("result", "success");
//        } catch (AuthenticationException e) {
//            returnData.put("result", "fail");
//        }
//        return CommonUtil.successJson(returnData);
//    }

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public User getUser(String username, String password) {
        return loginDao.getUser(username, password);
    }

	@Override
	public String checkAccount(String user_id) {
		// TODO Auto-generated method stub
		return loginDao.queryUser_id(user_id);
	}

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return
     */
//    @Override
//    public User getInfo() {
//        //从session获取用户信息
//        Session session = SecurityUtils.getSubject().getSession();
//        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
//        String username = userInfo.getString("username");
//        JSONObject returnData = new JSONObject();
//        JSONObject userPermission = permissionService.getUserPermission(username);
//        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
//        returnData.put("userPermission", userPermission);
//        return CommonUtil.successJson(returnData);
//    }

    /**
     * 退出登录
     *
     * @return
     */
//    @Override
//    public User logout() {
//        try {
//            Subject currentUser = SecurityUtils.getSubject();
//            currentUser.logout();
//        } catch (Exception e) {
//        }
//        return CommonUtil.successJson();
//    }
}
