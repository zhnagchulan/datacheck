package com.chinalife.datacheck.controller;

import javax.crypto.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinalife.datacheck.models.User;
import com.chinalife.datacheck.service.LoginService;

/**
 * 登录相关
 * @author suncp
 *
 */
//@CrossOrigin(origins="*",maxAge=3600)
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param requestJson
     * @return
     */
//    @PostMapping("/auth")
//    public User authLogin(@RequestBody User requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "username,password");
//        return loginService.authLogin(requestJson);
//    }

    /**
     * 查询当前登录用户的信息
     *
     * @return
     */
//    @CrossOrigin(origins = "http://127.0.0.1:8080/")
//    @GetMapping(value = "/login")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public User login(@RequestParam(value="username")String username,@RequestParam(value="password")String password) {
    	//String pas=Base64Utils.encodeToUrlSafeString("444".getBytes());
        // System.out.println(pas);
         //System.out.println(new String(Base64Utils.decodeFromString("dGVzdA==")));
    	return loginService.getUser(username, password);
    }
    @GetMapping("/checkAccount")
    @ResponseBody
    public String checkAccount(@RequestParam("user_id") String user_id) {
       return loginService.checkAccount(user_id);
    }
    /**
     * 登出
     *
     * @return
     */
//    @PostMapping("/logout")
//    public JSONObject logout() {
//        return loginService.logout();
//    }
}
