package com.chinalife.datacheck.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.Base64;
import com.chinalife.datacheck.dao.DataSourceDao;
import com.chinalife.datacheck.models.App;
import com.chinalife.datacheck.models.DataSource;
import com.chinalife.datacheck.service.DataSourceService;


//@CrossOrigin(origins="*",maxAge=3600)
@RestController
public class DataSourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);
	
	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private DataSourceDao dsDao;
    @RequestMapping(value = "/datasource/query",method = RequestMethod.GET)
    @ResponseBody
    public List<DataSource> getInfo(@RequestParam(value="name",required=false)String name,@RequestParam(value="id")String id,
    		@RequestParam(value="begin")String begin,@RequestParam(value="end")String length) {
//    	logger.debug("data{}",resourceService.get(username)); 
    	//if(name.equals("")) {name=null;}
    	return dataSourceService.get(name,id,begin,length);
    }
    @RequestMapping(value = "/datasource/total",method = RequestMethod.GET)
    @ResponseBody
    public String total(@RequestParam(value="name",required=false)String name,@RequestParam(value="id")String id) {
//    	logger.debug("data{}",resourceService.get(username)); 
    	//if(name.equals("")) {name=null;}
    //	System.out.println(dataSourceService.total(name,id)+"-----------");
    	return dataSourceService.total(name,id);
    }
    
    @RequestMapping(value = "/datasource/update",method = RequestMethod.POST)
    @ResponseBody
    public void updateInfo(@RequestBody DataSource datasource) {
//    	logger.debug("data{}",datasource.getUser_id()); 
    
    	dataSourceService.update(datasource);
    }
    
    @RequestMapping(value = "/datasource/disabled",method = RequestMethod.GET)
    @ResponseBody
    public void deleteInfo(@RequestParam(value="id")long id) {
//    	logger.debug("data{}",resourceService.get(username)); 
    	dataSourceService.delete(id);
    	
    }
    
    @RequestMapping(value = "/datasource/appList",method = RequestMethod.GET)
    @ResponseBody
    public List<App> getAppInfo(String user_id,String app_sname) {
//    	logger.debug("data{}",resourceService.get(username)); 
    	if(user_id.equals("")) {user_id=null;}
    	return dataSourceService.getAppList(user_id,app_sname);
    }
    
    
    @RequestMapping(value = "/datasource/enable",method = RequestMethod.GET)
    @ResponseBody
    public void enable(@RequestParam(value="id")long id) {
//    	logger.debug("data{}",resourceService.get(username)); 
    	dataSourceService.enable(id);
    	
    }
}
