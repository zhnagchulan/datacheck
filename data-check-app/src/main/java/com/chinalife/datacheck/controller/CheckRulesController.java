package com.chinalife.datacheck.controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinalife.datacheck.dao.CheckRuleDao;
import com.chinalife.datacheck.dao.TasksDao;
import com.chinalife.datacheck.service.CheckRuleService;
import com.chinalife.datacheck.service.impl.SchedulingTask;
import com.mysql.fabric.xmlrpc.base.Array;
/**
 * 实现tb_checkRules的增删改查，前台调用对应的路径，如/checkReules/add
 * @author Mr.Li
 *
 */
//@CrossOrigin(origins="*",maxAge=36000)
@RestController
public class CheckRulesController {
	private static  Logger log = LoggerFactory.getLogger(SchedulingTask.class);
   @Autowired
   private CheckRuleService CRS;
   
   @Autowired 
   private TasksDao taskkDao;
   
   @Autowired
   private CheckRuleDao crDao;
   //供前台增加校验规则按钮使用
   @PostMapping("/checkRules/add")
   public void checkRule_add(@RequestBody Map<String, String> para) {
	  // System.out.println(para.toString());
	   CRS.checkRule_add(para.get("user_id"), para.get("sys_name"),
			   para.get("datasource_code"), para.get("is_enable").toCharArray()[0], 
			   Arrays.asList(para.get("workerNames").toString().split(",")), 
			   para.get("sql_statement"), para.get("intent"), para.get("first_time"), 
			   new Integer(para.get("interval")), new Integer(para.get("frequency")),
			   para.get("is_message").toCharArray()[0]); 
   }
   
   //供前台删除校验规则按钮使用
   @PostMapping("/checkRules/delete")
 public void checkRule_delete(
		 @RequestBody Map<String,String> para
		 ) {
	 CRS.checkRule_delete(para.get("user_id"), para.get("checkRule_code"));
 }  
   
   //查询当前用户所拥有的系统供前台系统下拉框选择
   @PostMapping("/checkRules/querySystems")
   @ResponseBody
   public List<String> querySystems(@RequestBody Map<String,String> para) {
  	//return CRS.querySystems((String)para.get("user_id"));
  	 return CRS.querySystems(para.get("user_id"));
   }
   
   //查询某系统下的所有校验规则
 @PostMapping("/checkRules/queryBySysName")
 public JSONArray queryBySysName(@RequestBody Map<String,String> para) {
	 JSONArray jsa=CRS.queryBySysName(null, para.get("sys_name"),
			 Integer.parseInt(para.get("begin")), Integer.parseInt(para.get("end"))
			 );
	 jsa=addTimeandSysName(jsa);
	return jsa;
 }
 
 //供前台校验规则的查询使用
 @PostMapping("/checkRules/showOne")
 @ResponseBody
  public JSONObject showOne(
 		@RequestBody Map<String,String> para
 		 ) {
 	   
 	 JSONObject jsb=CRS.queryBycheckRule_code(para.get("user_id"), para.get("checkRule_code"));
 	  if(jsb!=null) {
 	 Timestamp time=taskkDao.queryMaxNextTimeOfcheckRulecode(para.get("checkRule_code"));
 	 if(time!=null) {
 		 jsb.put("next_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
 				   .format(taskkDao.queryMaxNextTimeOfcheckRulecode(para.get("checkRule_code"))));
 	 }else {
	   jsb.put("next_time", jsb.getString("first_time").toString());
	   }
	   jsb.put("system_name", crDao.querySystemName(jsb.getIntValue("app_id")));
 	  }
	 return jsb;
  }
 
 
@PostMapping("/checkRules/querySome")
@ResponseBody
/*（1）供前台查询使用，如果不指定checkRule_code将返回当前用户下所有的校验规则
 * （2）指定了checkRule_code将返回一条校验规则
*/
 public JSONArray query(@RequestBody Map<String,String> para ) {
	JSONArray jsa= new JSONArray();
	//不指定checkRule_code
	if(para.get("checkRule_code").equals("")) {
		jsa=CRS.querySome(para.get("user_id"), Integer.parseInt(para.get("begin")), Integer.parseInt(para.get("end")));
		jsa=addTimeandSysName(jsa);
		}else {
			//指定了checkRule_code
			JSONObject jsb=showOne(para);
			if(jsb!=null) {
			jsa.add(jsb);}
		}
	
	
	return jsa;		
			
 }
//为每条校验规则格式化时间和添加系统名称，此方法为避免代码重复抽出成单独的一个代码段供其它方法调用，可以ctrl+f查询哪个方法调用本方法
 public JSONArray addTimeandSysName(JSONArray jsa) {
		if(jsa!=null) {
			//System.out.println(jsa.size());
			JSONObject jsb=new JSONObject();
		for (int i=0;i<jsa.size();i++) {
			jsb=jsa.getJSONObject(i);
			Timestamp time=taskkDao.queryMaxNextTimeOfcheckRulecode(jsb.getString("checkRule_code"));
		 	 if(time!=null) {
		 		 jsb.put("next_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		 				   .format(taskkDao.queryMaxNextTimeOfcheckRulecode(jsb.getString("checkRule_code"))));
		 	 }else {
			   jsb.put("next_time", jsb.getString("first_time").toString());
			   }
			   jsb.put("system_name", crDao.querySystemName(jsb.getIntValue("app_id")));
			   jsa.remove(i);
			jsa.add(i,jsb);
			//System.out.println(jsa.toJSONString());
		}
		}
		return jsa;
 }
 
 //供前台校验规则的更新使用
 @PostMapping("/checkRules/update")
public void checkRule_update(@RequestBody Map<String, String> para) {
	 //System.out.println(para.toString());
	CRS.checkRule_update(para.get("user_id"), para.get("checkRule_code"), para.get("sql_statement"), para.get("is_enable").toCharArray()[0],
			 new Integer(para.get("frequency")),
			   para.get("is_message").toCharArray()[0], Integer.parseInt(para.get("interval")),
			   para.get("first_time"), para.get("intent"), 
			   Arrays.asList(para.get("workerNames").toString().split(",")));
}
 
 
 	//查询所有的关系人姓名供前台选择
	@GetMapping("/checkRules/getAllstakeholders")
	@ResponseBody
	List<String> queryAllStakeholders(){
		
		//System.out.println(CRS.queryAllStakeholders().toString());
		return CRS.queryAllStakeholders();
	}
	
	//获取某系统下的所有数据源
	@PostMapping("/checkRules/getdatasources")
	List<String> getdatasources(@RequestBody Map<String,String> para ){
		return CRS.queryDatasourceBySys(para.get("sys_name"));
		
	}
	@GetMapping("/checkRules/total")
	@ResponseBody
	//供前台校验规则页面分页统计总条数，分页数=total/pageSize,其他java文件中total作用同此
	public String total(@RequestParam("user_id") String user_id,@RequestParam("sys_name") String sys_name ) {
	String total=null;
		
	if(!sys_name.equals("")) {
		total=crDao.total(user_id,crDao.querySystemId(sys_name).get(0));
	}else {
		total=crDao.totalByuser_id(user_id);
}
	return total;
	}
	@GetMapping("/checkRules/batchdisable")
	@ResponseBody
	/*
	 * 对应于校验规则页面"批量停用按钮"
	 * 思路:
	 * (1)先删除内存中要被删除的多条校验规则对应的定时Job
	 * (2)后批量将多条校验规则的状态改为无效'2'
	 * 
	 *//******************************为步骤分割线
	 * */
	public void batchdiasble(@RequestParam("checkRule_codes") String checkRules ) {
		//System.out.println(checkRules);
	   //****************************************************************  
		String[] temp=checkRules.split(",");
		List<JobKey> jobKeys=new ArrayList<JobKey>();
		
		 checkRules="";
	  for (String string : temp) {
		checkRules=checkRules+"\""+string+"\",";
		jobKeys.add(new JobKey(string+"Job"));
	}
	  try {
		SchedulingTask.getsched().deleteJobs(jobKeys);
	} catch (SchedulerException e) {
		// TODO Auto-generated catch block
		log.error("error", e);
		e.printStackTrace();
	}
	  //********************************************************************
		//System.out.println(checkRules.substring(0, checkRules.length()-1));
	       CRS.batchdisable(checkRules.substring(0, checkRules.length()-1));    
	}
	@GetMapping("/checkRules/enable")
	@ResponseBody
	/*
	 * 对应于校验规则页面'启用'按钮，把校验规则的状态改为有效'1'
	 * 
	 * */
	public void enable(@RequestParam("checkRule_code") String checkRule_code ) {
		
	       CRS.enable(checkRule_code);  
	}
	

	//查询某条校验规则绑定的关系人列表
	@GetMapping("/checkRules/querystakeholdersOfcheckRule")
	@ResponseBody
	 public List<String> querystakeholdersOfcheckRule(@RequestParam("checkRule_code")String checkRule_code){
		 return CRS.querystakeholdersOfcheckRule(checkRule_code);
	 }
}
