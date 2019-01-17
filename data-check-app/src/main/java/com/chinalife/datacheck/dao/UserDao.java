package com.chinalife.datacheck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinalife.datacheck.models.App;
import com.chinalife.datacheck.models.User;

public interface UserDao {
	
	void updateUser(User user);
	void updateUsers(User user);
	void addUser(User user);
	void deleteUser(String id);
	void enableUser(String id);
	User getUser(String id);
	List<User> getLists(@Param("id")String id,@Param("username")String username,@Param("status")String status,@Param("role")String role,
			@Param("userid")String userid,	@Param("begin")int begin,@Param("end")int end);
	String total(@Param("id")String id,@Param("username")String username,@Param("status")String status,@Param("role")String role,@Param("userid")String userid);
	public List<User> getLists();
	List<Integer> getUserApp(@Param("id")String id);
	void insertUserApp(@Param("app_id")int app_id,@Param("id")String id);
	void deleteUserApp(@Param("id")String id);
	void registryUser(User user);
}
