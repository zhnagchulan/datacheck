package com.chinalife.datacheck.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinalife.datacheck.dao.DataSourceDao;
import com.chinalife.datacheck.models.App;
import com.chinalife.datacheck.models.DataSource;
import com.chinalife.datacheck.service.DataSourceService;

@Service
public class DatasourceServiceImpl implements DataSourceService{
	@Autowired
	private DataSourceDao datasourceDao;

	@Override
	public List<DataSource> get(String name,String id,String begin,String end) {
		// TODO Auto-generated method stub
		 return datasourceDao.get(name,id,Integer.parseInt(begin),Integer.parseInt(end));
	}
	@Override
	public String total(String name,String id) {
		// TODO Auto-generated method stub
		 return datasourceDao.total(name,id);
	}
	
	@Override
	public List<DataSource> get() {
		// TODO Auto-generated method stub
		 return datasourceDao.get();
	}
	
	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		 datasourceDao.delete(id);
	}
	
	@Override
	public void update(DataSource datasource) {
		// TODO Auto-generated method stub
		datasource.setOwner(datasourceDao.getappIDbySname(datasource.getOwner()));
		if(datasource.getId() >= 1){
			datasourceDao.update(datasource);
		}else{
			//System.out.println(datasource.getOwner());
			datasource.setCode(generateDatasource_code(datasource.getCode()));
			
			//system.out.println(datasource.getOwner());
			datasourceDao.add(datasource);
			//datasourceDao.addUserApp(datasource);
		}
		 
	}
	//数据源名称=数据源名称+时间戳，生成唯一的数据源名称
	public String generateDatasource_code(String datasource_code) {
		return datasource_code+"_"+new Date().getTime();
	}
	@Override
	public List<App> getAppList(String user_id,String app_sname) {
		// TODO Auto-generated method stub
		 return datasourceDao.getApp(user_id,app_sname);
	}
	
	@Override
	public void enable(long id) {
		datasourceDao.enable(id);
	}
}
