package com.chinalife.datacheck.service;

import java.util.List;

import com.chinalife.datacheck.models.User;

public interface UserService {
	
	void updateUser(User user);
	void updateUsers(User user);
	void deleteUser(String id);
	void enableUser(String id);
	void addUser(User user);
	User getUser(String id);
	List<User> getLists(String id,String username,String status,String role,String userid,
			String begin,String end);
	String total(String id,String username,String status,String role,String userid);
	List<User> getLists();
	int registryUser(User user);
}
