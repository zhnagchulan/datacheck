package com.chinalife.datacheck.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.stereotype.Service;


import com.chinalife.datacheck.dao.UserDao;

import com.chinalife.datacheck.models.User;
import com.chinalife.datacheck.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.updateUser(user);
	}
	@Override
	public void updateUsers(User user) {
		// TODO Auto-generated method stub
		userDao.updateUsers(user);
		//userDao.deleteUserApp(user.getId());
		List<Integer> appids=userDao.getUserApp(user.getId());
		if (user.getApp() != null){
			List<Integer> list = user.getApp();	
			for(int i=0;i<list.size();i++){
//				System.out.println(list.get(i));
					//System.out.println("test");
				if (appids.contains(list.get(i))) {
					continue;
				}
				userDao.insertUserApp(list.get(i),user.getId());
			}
		}else{
			
		}
	}
	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		userDao.deleteUser(id);
	}
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		userDao.addUser(user);
		userDao.deleteUserApp(user.getId());
		if (user.getApp() != null){
			List<Integer> list = user.getApp();
			for(int i=0;i<list.size();i++){
//				System.out.println(list.get(i));
					//System.out.println("test");
				userDao.insertUserApp(list.get(i),user.getId());
			}
		}else{
			
		}
	}
	@Override
	public int registryUser(User user) {
		// TODO Auto-generated method stub
		userDao.registryUser(user);
		return 1;
	}
	@Override
	public User getUser(String id) {
		// TODO Auto-generated method stub
		User user = userDao.getUser(id);
		List<Integer> app_id = userDao.getUserApp(id);
		user.setApp(app_id);
//		System.out.println(app_id.get(0));
		return user;
	}
	
	@Override
	public List<User> getLists(String id,String username,String status,String role,String userid,String begin,String end) {
		// TODO Auto-generated method stub
//		return userDao.getLists(id,username,status);
		List<User> list = userDao.getLists(id,username,status,role,userid,Integer.parseInt(begin),Integer.parseInt(end));
		for (int i=0;i<list.size();i++){
			User user = list.get(i);
			String user_id = user.getId();
//			System.out.println(user.getId());
//			Integer app_id = null;
			List<Integer> list_app = userDao.getUserApp(user_id);
			user.setApp(list_app);
//			System.out.println(user.getApp_id());
			list.set(i, user);
//			//System.out.println("test="+id);
		}
		return list;
	}
	@Override
	public String total(String id,String username,String status,String role,String userid) {
		// TODO Auto-generated method stub
//		return userDao.getLists(id,username,status);
		return userDao.total(id,username,status,role,userid);
		
	}
	
	@Override
	public List<User> getLists() {
		// TODO Auto-generated method stub
		return userDao.getLists();
//		List<User> list = userDao.getLists();
////		logger.debug("data{}",list);
//		//System.out.println("test="+list.get());
//		for (int i=0;i<list.size();i++){
//			User user = list.get(i);
//			String id = user.getId();
////			System.out.println(id);
//			Integer app_id = null;
//			List<Integer> list_id = userDao.getUserApp(app_id,id);
//			user.setApp_id(list_id);
//			System.out.println(user.getApp_id());
//			list.set(i, user);
//			//System.out.println("test="+id);
//		}
//		return list;
	}
	@Override
	public void enableUser(String id) {
		// TODO Auto-generated method stub
		userDao.enableUser(id);
	}
}
