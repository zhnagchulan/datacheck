package com.chinalife.datacheck.service;

import java.util.List;

import com.chinalife.datacheck.models.App;
import com.chinalife.datacheck.models.DataSource;

public interface DataSourceService {
	List<DataSource> get(String name,String id,String begin,String end);
	String total(String name,String id);
	List<DataSource> get();
	void update(DataSource datasource);
	void delete(long id);
	List<App> getAppList(String user_id,String app_sname);
	void enable(long id);
}
