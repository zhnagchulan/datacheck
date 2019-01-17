package com.chinalife.datacheck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinalife.datacheck.models.App;
import com.chinalife.datacheck.models.DataSource;

public interface DataSourceDao {
	
void add(DataSource datasource);
	
	int delete(long id);
	String getappIDbySname(@Param("app_sname")String app_sname);
	int update(DataSource datasource);
	
	int addUserApp(DataSource datasource);
	
	List<DataSource> query(String code);
	
	List<DataSource> get(@Param("name")String name,@Param("id")String id,@Param("begin")int begin,@Param("end")int end);
	String total(@Param("name")String name,@Param("id")String id);
	List<DataSource> get();
	
	List<App> getApp(@Param("user_id")String user_id,@Param("app_sname")String app_sname);
   
	void enable(long id);

	List<DataSource> queryAll();
}
