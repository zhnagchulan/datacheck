package com.chinalife.datacheck.controller;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinalife.datacheck.models.User;
import com.chinalife.datacheck.service.UserService;

//@CrossOrigin(origins="*",maxAge=3600)
//@RequestMapping("/test")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/user/edit",method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@RequestBody User user){
		userService.updateUser(user);
	}
	
	//查询个人用户
	@RequestMapping(value = "/user/get",method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam(value="id") String id){
		return userService.getUser(id);
	};
	//查询所有用户
	@RequestMapping(value = "/user/getLists",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getLists(@RequestParam(value="id",required=false)String id,
    		@RequestParam(value="username",required=false)String username,
    		@RequestParam(value="status",required=false)String status,
    		@RequestParam(value="role",required=false)String role,
    		@RequestParam(value="userid",required=false)String userid,
    		@RequestParam(value="begin",required=false)String begin,
    		@RequestParam(value="end",required=false)String length){
		return userService.getLists(id,username,status,role,userid,begin,length);
	}
	
	@RequestMapping(value = "/user/total",method = RequestMethod.GET)
    @ResponseBody
    public String total(@RequestParam(value="id",required=false)String id,
    		@RequestParam(value="username",required=false)String username,
    		@RequestParam(value="status",required=false)String status,
    		@RequestParam(value="role",required=false)String role,
    		@RequestParam(value="userid",required=false)String userid
    		
    		){
		return userService.total(id,username,status,role,userid);
	}
	//添加用户
	@RequestMapping(value = "/user/addUser",method = RequestMethod.POST)
    @ResponseBody
    public void addUser(@RequestBody User user){
		userService.addUser(user);
	}
	//注册用户
	@RequestMapping(value = "/registry",method = RequestMethod.POST)
    @ResponseBody
    public int registryUser(@RequestBody User user){
		userService.registryUser(user);
		return 1;
	}
	//删除用户
	@RequestMapping(value = "/user/deleteUser",method = RequestMethod.GET)
    @ResponseBody
    public void disabledUser(@RequestParam(value="id")String id){
		userService.deleteUser(id);
	}
	
	//修改用户
	@RequestMapping(value = "/user/updateUsers",method = RequestMethod.POST)
    @ResponseBody
    public void updateUsers(@RequestBody User user){
		//system.out.(user.getRole());
		userService.updateUsers(user);
	}
	
}
