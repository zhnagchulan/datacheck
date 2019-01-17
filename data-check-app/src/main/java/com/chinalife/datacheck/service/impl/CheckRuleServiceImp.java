package com.chinalife.datacheck.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinalife.datacheck.dao.CheckRuleDao;
import com.chinalife.datacheck.dao.Stakeholder_to_checkRuleDao;
import com.chinalife.datacheck.models.CheckRule;
import com.chinalife.datacheck.service.CheckRuleService;
/**
 * 
 * 
 * @author Mr.Li
 * 思路：
 * （1）实现CheckRuleService接口里说明的抽象方法
 * （2）使用@Autowired注解自动装箱CheckRuleDao对象,使用@service注解本类为服务类
 * （3）调用CheckRuleDao对象方法实现接口
 *
 */

@Service
public class CheckRuleServiceImp implements CheckRuleService {
	/**  异常追踪：
	 * 把本类中的try{}catch{}中的异常写入到log文件夹中，以便于校验规则执行异常时追踪异常
	 *即调用logger.error("error",e)，在10.21.42.173生产服务器上日志保存在/app/tomcat/log/下
	 * 
	 * 
	 */
	 private Logger logger = LoggerFactory.getLogger(CheckresultServiceImpl.class);
	@Autowired
	private CheckRuleDao ChRDao;
	
	 @Autowired  
		private Stakeholder_to_checkRuleDao stc;
	
	 @Override
	 /** checkRule_add方法思路:
	  * (1)先添加校验规则，即插入数据到表tb_checkRules
	  * (2)后添加校验规则的关系人，即插入数据到表tb_stakeholder_to_checkRule中
	  * (3)根据前台页面的选择是否启用本校验规则，启用，则创建定时任务定时执行sql，否则，不创建定时任务
	  * 
	  * 以下*************分割线对应相关步骤
	  */
	public void checkRule_add(String user_id, String sys_name, String datasource_code, char is_enable,
			List<String> stakeholders, String sql_statement, String intent, String first_time, int interval,
			int frequency,char is_message) {
		// TODO Auto-generated method stub
		 //****************************************************************************
	   String checkRule_code=generateRuleCode(ChRDao.queryappSnameByName(sys_name));
	   //添加校验规则
	   if(ChRDao.querySystemId(sys_name).size()==1) {
		ChRDao.checkRule_add(checkRule_code, user_id,ChRDao.querySystemId(sys_name).iterator().next(),datasource_code,
				interval,Timestamp.valueOf(LocalDateTime.now()), frequency, sql_statement, intent,
				is_message, is_enable, Timestamp.valueOf(first_time));}
	   else {
		   logger.error("error","系统名称有重复，修改系统名称");
		   }
	   //*******************************************************************************
	   //添加校验规则的关系人
		for (String party : stakeholders) {
			stc.add(stc.getworkNumberByName(party), checkRule_code, Timestamp.valueOf(LocalDateTime.now()));
		}
		//*****************************************************************************
		if(is_enable=='1') {
			Map<String, Object> map=new HashMap<>();
			map.put("jobkey",new JobKey(checkRule_code+"Job") );
			map.put("first_time",first_time );
			map.put("intervals", interval);
			map.put("frequency", frequency);
		SchedulingTask.startScheduling();
		SchedulingTask.schedulingTask(checkRule_code, map,SchedulingTask.getsched());}
	}

	@Override
	/** checkRule_delete方法思路:
	  * (1)首先不管该规则是否创建了对应的定时任务都先删除内存中此校验规则的定时Job
	  * (2)然后按照数据库中删除原则，先删除子表中的数据，再删除父表中的数据
	  * 即，先删除表tb_stakeholder_to_checkRule中相关的数据，再删除表tb_checkRules中的数据
	  * 
	  * 以下*************分割线对应相关步骤
	  */
	public void checkRule_delete(String user_id, String checkRule_code) {
		// TODO Auto-generated method stub
		//删除校验规则对应的关系人
		//********************************************************************************
		user_id=null;
		if(ChRDao.queryBycheckRule_code(user_id, checkRule_code).getIs_enable()=='1') {
			JobKey jobkey=new JobKey(checkRule_code+"Job");
	try {
		SchedulingTask.getsched().deleteJob(jobkey);
	} catch (SchedulerException e) {
		// TODO Auto-generated catch block
		logger.error("error",e);
		e.printStackTrace();
	}
	}
		//*******************************************************************************
		stc.delete(checkRule_code, null);			
	    ChRDao.checkRule_delete(user_id, checkRule_code);
		
	}

	@Override
	//通过系统名称查询该系统下的校验规则，再通过循环对校验规则中的时间做格式化处理
	//（查询出来的时间是格林威治格式且精确到毫秒，需要格式化到东八区时间区域并精确到秒）
	//以下*************分割线对应相关步骤
	public JSONArray queryBySysName(String user_id, String sys_name,int begin,int end) {
		// TODO Auto-generated method stub
	//*****************************************************************************************	
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONArray jsa=JSONArray.parseArray((JSON.toJSONString(ChRDao.queryBySysName(user_id, sys_name,begin,end))));
   //******************************************************************************************		
	  if(jsa!=null) {
		for (int i = 0; i < jsa.size(); i++) {
			JSONObject jsb=jsa.getJSONObject(i);
			// 格式化JSonObject中的时间为yyyy-mm-dd HH:MM:SS再存进对应JSonObject
			 if (jsb.get("create_time")!=null) {
				  jsb.replace("create_time",df.format(new Date(Long.parseLong(jsb.get("create_time").toString()))));
			}
			if(jsb.get("update_time")!=null) {
			jsb.replace("update_time",df.format(new Date(Long.parseLong(jsb.get("update_time").toString()))));
			}
			if(jsb.get("first_time")!=null) {jsb.replace("first_time",df.format(new Date(Long.parseLong(jsb.get("first_time").toString()))));
			}
		}
	  }
		return jsa;
		
		
	}

	@Override
	//通过user_id,checkRule_code查询具体的某一条校验规则，再对时间做格式化处理，返回时间格式化后的校验规则
	public JSONObject queryBycheckRule_code(String user_id, String checkRule_code) {
		// TODO Auto-generated method stub
		//********************************************************************************************
		JSONObject jsb= JSONObject.parseObject(JSON.toJSONString(ChRDao.queryBycheckRule_code(user_id, checkRule_code)));
		
		
		//********************************************************************************************
		if(jsb!=null) {
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  //格式化JSonObject中的时间为yyyy-mm-dd HH:MM:SS再存进对应JSonObject
		 
		  if (jsb.get("create_time")!=null) {
			  jsb.replace("create_time",df.format(new Date(Long.parseLong(jsb.get("create_time").toString()))));
		}
		if(jsb.get("update_time")!=null) {
		jsb.replace("update_time",df.format(new Date(Long.parseLong(jsb.get("update_time").toString()))));
		}
		if(jsb.get("first_time")!=null) {jsb.replace("first_time",df.format(new Date(Long.parseLong(jsb.get("first_time").toString()))));
		}
		  
		}
		  
		return jsb;
		
	}

	@Override
	
	/**checkRule_update方法思路:
	 *  (1)先删除该校验规则的定时任务
	 * (2)然后更新tb_checkRules表，更新tb_stakeHolder_to_checkRule表，
	 * 添加新选的关系人，删除不再需要勾选（即之前勾选过，现在取消勾选）的关系人
	 * (3)最后，重新添加校验规则定时任务
	 * 
	 * 
	 * 以下*************分割线对应相关步骤
	 */
	public void checkRule_update(String user_id, String checkRule_code, String sql_statement, char is_enable, int frequency,
			char is_message, int interval, String first_time, String intent, List<String> stakeholders) {
		// TODO Auto-generated method stub
	  user_id=null;
	  
	 //**********************************************************************************
		JobKey jobkey=new JobKey(checkRule_code+"Job");
		if(ChRDao.queryBycheckRule_code(user_id, checkRule_code).getIs_enable()=='1') {
				
			try {
				SchedulingTask.getsched().deleteJob(jobkey);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				logger.error("error",e);
				e.printStackTrace();
			}}
		//******************************************************************************
		
		ChRDao.checkRule_update(checkRule_code, user_id,interval, null, frequency, sql_statement,
				intent, is_message, is_enable, Timestamp.valueOf(first_time), Timestamp.valueOf(LocalDateTime.now()));
	
		//查询当前校验规则所有的关系人工号
		 List<String> list=stc.queryWorknumbersBycheckRule_code(checkRule_code);
		 //判断前台输入的关系人工号是否在数据库中，不在就添加此关系人
		for (String party : stakeholders) {
			if(!list.contains(stc.getworkNumberByName(party))) {
			stc.add(stc.getworkNumberByName(party),checkRule_code,Timestamp.valueOf(LocalDateTime.now()));
		}
			
		
		List<String> listr = new LinkedList<String>();
		for (String stakeholder : stakeholders) {
			 listr.add(stc.getworkNumberByName(stakeholder));
		}
         
			//判断当前校验规则在数据库中存有的关系人是否在前台传入的关系人中，不在就删除该关系人。
			for (String workNumber : list) {
				if(!listr.contains(workNumber)) {
					stc.delete(checkRule_code, workNumber);
				}
			}
		}
		//*********************************************************************************
		if(is_enable=='1') {
			Map<String, Object> map=new HashMap<>();
			map.put("jobkey",jobkey);
			map.put("first_time",first_time );
			map.put("intervals", interval);
			map.put("frequency", frequency);
			SchedulingTask.startScheduling();
			SchedulingTask.schedulingTask(checkRule_code, map,SchedulingTask.getsched());}
		
	}

	

	//生成"系统简称_时间"校验规则代码
	public String generateRuleCode(String app_sname) {
		return app_sname+"_"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date().getTime());
	}

	@Override
	//对应于前台校验规则页面选择关系人多选框
	public List<String> queryAllStakeholders() {
		// TODO Auto-generated method stub
		return ChRDao.queryAllStakeholders();
	}

	@Override
	//通过应用系统名称查询该应用系统中所有校验规则，响应进度报告会议上刘珊提出的需求
	public List<String> queryDatasourceBySys(String sys_name) {
		// TODO Auto-generated method stub
		return ChRDao.queryDatasourceBySys(ChRDao.querySystemId(sys_name).get(0));
	}

	@Override
	//对应于前台创建校验规则页面选择系统下拉框
	//通过user_id查询某个用户所分配到的所有系统供前台校验规则页面选择系统
	public List<String> querySystems(String user_id) {
		// TODO Auto-generated method stub
		return ChRDao.querySystems(user_id);
	}

	

	@Override
	/**对应于前台页面分页查询校验规则
	 * 思路：
	 *  (1)先通过mysql limit查询，查询出校验规则
	 *  (2)然后对每一条校验规则中的时间做格式化
	 *  (3)返回对时间格式化后的校验规则JSONArray
	 *  
	 *  
	 *  以下*************分割线对应相关步骤
	 */
	public JSONArray querySome(String user_id,int begin,int end) {
		// TODO Auto-generated method stub
		//*************************************************************************
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSONArray jsa=JSONArray.parseArray((JSON.toJSONString(ChRDao.querySome(user_id,begin,end))));
		//*************************************************************************	
			if(jsa!=null) {
			for (int i = 0; i < jsa.size(); i++) {
				JSONObject jsb=jsa.getJSONObject(i);
				// 格式化JSonObject中的时间为yyyy-mm-dd HH:MM:SS再存进对应JSonObject
				 if (jsb.get("create_time")!=null) {
					  jsb.replace("create_time",df.format(new Date(Long.parseLong(jsb.get("create_time").toString()))));
				}
				if(jsb.get("update_time")!=null) {
				jsb.replace("update_time",df.format(new Date(Long.parseLong(jsb.get("update_time").toString()))));
				}
				if(jsb.get("first_time")!=null) {jsb.replace("first_time",df.format(new Date(Long.parseLong(jsb.get("first_time").toString()))));
				}
			}
			}
			//***********************************************************************
			return jsa;
			
	}

	@Override
	//对应于前台页面"批量停用按钮"
	public void batchdisable(String checkRules) {
		// TODO Auto-generated method stub
		
		ChRDao.batchdisable(checkRules);
		//SchedulingTask.getsched().
	}

	@Override
	//对应于校验规则前台页面"启用"按钮启用校验规则
	//先修改校验规则的状态为'1'，后创建该校验规则的定时任务
	public void enable(String checkRule_code) {
		// TODO Auto-generated method stub
		ChRDao.enable(checkRule_code);
		//SchedulingTask.startScheduling();
		CheckRule chr=ChRDao.queryBycheckRule_code(null, checkRule_code);
		Map<String, Object> map=new HashMap<>();
		map.put("jobkey",new JobKey(checkRule_code+"Job") );
		map.put("first_time",chr.getFirst_time().toString());
		map.put("intervals", chr.getIntervals());
		map.put("frequency", chr.getFrequency());
		SchedulingTask.startScheduling();
		SchedulingTask.schedulingTask(checkRule_code, map,SchedulingTask.getsched());
	}
	@Override
	//通过checkRule_code查询某条校验规则下的所有被短信通知的关系人，
	//对应于前台校验规则"创建"，"编辑"页面展示哪些关系人被选中。
	 public List<String> querystakeholdersOfcheckRule(String checkRule_code){
		 return ChRDao.querystakeholdersOfcheckRule(checkRule_code);
	 }
	
}
