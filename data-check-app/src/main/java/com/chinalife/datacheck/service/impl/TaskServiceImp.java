package com.chinalife.datacheck.service.impl;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSONObject;
import com.chinalife.datacheck.dao.CheckResultsDao;
import com.chinalife.datacheck.dao.CheckRuleDao;
import com.chinalife.datacheck.dao.Sql_statementExcutionDao;
import com.chinalife.datacheck.dao.Stakeholder_to_checkRuleDao;
import com.chinalife.datacheck.dao.TasksDao;
import com.chinalife.datacheck.models.CheckRule;
import com.chinalife.datacheck.service.TasksService;


@Service
public class TaskServiceImp implements TasksService {
	 private static  SqlSessionFactory sqlSessionFactory;
	 private static Logger logger; 
	    private static Reader reader;  
	    private static TaskServiceImp single=null;
	    public static  TaskServiceImp getSingle() {
	    	/*
	    	 * 创建TaskServiceImp单例,目的同Notification.java中单例模式创建Notification对象
	    	 */
	    	if(single==null) {
	    		single=new TaskServiceImp();
	    	}
	    	return single;
	    }
         //在java虚拟机中加载本类时初始化reader,sqlSessionFactory,logger
	   static {
		  logger = LoggerFactory.getLogger(TaskServiceImp.class);
	    	try {	
				reader=Resources.getResourceAsReader("com/chinalife/datacheck/config/changeDatasourceconfig.xml");
				sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
		    	reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//system.out.println("Reader");
				logger.error("error",e);
				e.printStackTrace();
				
			}
	    	
	    }
	    
	@Override
	/**
	 * 思路：
	 * （1）创建一条记录插入到tb_tasks,其中task_status默认为空，fail_reason默认为空
	 * （2）执行sql_statement,判断返回结果是否为空引用，result为null,sql_statement执行失败，task_status更新为2，fail_reason
	 * 更新为失败可能原因，如果不为null，执行成功task_status更新为1，fail_reason更新为"成功"
	 * （3）sql_statement执行成功，创建一条记录插入到tb_checkResults,取第一条执行结果作为告警内容(本项目中告警内容为告警条数)
	 * @author Mr.li
	 *
	 */
	public List<LinkedHashMap<String, Object>> excuteCheckRule(String checkRule_code) {
		// TODO Auto-generated method stub
		
//*****************************************************************************		
		//创建mysql的session
		SqlSession sess=sqlSessionFactory.openSession(true);
		CheckRuleDao chRD = sess.getMapper(CheckRuleDao.class);
//********************************************************************************    	
    	//获取对应的校验规则
    	CheckRule checkRule=chRD.queryBycheckRule_code(null, checkRule_code);
 //********************************************************************************   	
    	Map<String, String> checkstatus=chRD.checkStatusBeforeExc(
    			checkRule.getApp_id(), checkRule.getDatasource_code());
    
    	Iterator<String> status=checkstatus.values().iterator();
    	boolean OK=true;
    	if(status.hasNext()) {
    		if(!status.next().equals("1")) {
    			OK=false;}
    	}//检查用户状态，数据源状态和系统状态是否都有效，如其中至少一个无效，自毁该校验规则定时任务
//**************************************************************************************			
		if(OK) {
			TasksDao tskdao = sess.getMapper(TasksDao.class);
	    	Stakeholder_to_checkRuleDao stcDao=sess.getMapper(Stakeholder_to_checkRuleDao.class);
	    	Notification ntfi=Notification.getSingle();
	    	ntfi.setSatkeholdertocheckruleDao(stcDao);
    	//获取数据源
    	  Map<String, Object> datasource=chRD.queryDatasource(checkRule.getDatasource_code());
    	 //获取数据源连接字符串
    	 Properties properties=getconnectionstr(datasource);
    	 //获取对应数据源sql连接会话
       
		Timestamp create_time=Timestamp.valueOf(LocalDateTime.now());
		//添加task
		tskdao.add(checkRule_code,create_time, Timestamp.valueOf(LocalDateTime.now()), null, 
				new Timestamp(create_time.getTime()+checkRule.getIntervals()*1000*60), null);
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createtime=df.format(create_time);
		String task_id=tskdao.queryIdBytimeandRulecode(checkRule_code, createtime);		
		  SqlSession ses=getexecsession(properties,tskdao,checkRule_code, createtime);
		List<LinkedHashMap<String, Object>> result=new ArrayList<LinkedHashMap<String,Object>>();
//*****************************************************************************************		
		try {
			  //获取要被查询数据库的查询对象
	    	 Sql_statementExcutionDao excu = ses.getMapper(Sql_statementExcutionDao.class);
			//执行sql_statement
		   result=excu.excuting(checkRule.getSql_statement());
		   ses.close();
		  
			if (result==null) {
				tskdao.update(checkRule_code, createtime, null, Timestamp.valueOf(LocalDateTime.now()), null, '2', "查询失败！"
						+ "可能原因：（1）查询语句本身不正确（2）数据库出现故障（3）数据校验系统本身问题");
				result=null;
			}else {
				//查找task_id
				tskdao.update(checkRule_code,createtime, null, Timestamp.valueOf(LocalDateTime.now()), null, '1', "成功");
				//返回第一条记录作为告警内容
				CheckResultsDao chR = sess.getMapper(CheckResultsDao.class);
				String count=result.get(0).keySet().iterator().next();
				count=String.valueOf(result.get(0).get(count));
				chR.add(Integer.parseInt(task_id), Integer.parseInt(count), checkRule.getIntent(), Timestamp.valueOf(LocalDateTime.now()));
                  if((checkRule.getIs_message()=='1')&& (Integer.parseInt(count)>0)) {
				//调用发短信，邮件接口进行发短信和邮件
                	  Trigger tr=SchedulingTask.getsched().getTrigger(new TriggerKey(checkRule.getCheckRule_code()+"trigger"));
				 int firecount=1+(int) ((tr.getPreviousFireTime().getTime()-checkRule.getFirst_time().getTime())/1000/60/checkRule.getIntervals());
                	  JSONObject content=new JSONObject();
				content.put("system_name",chRD.querySystemName(checkRule.getApp_id()));
				content.put("checkRule_code", checkRule.getCheckRule_code());
				content.put("intent", checkRule.getIntent());
				content.put("alert_sum",count);
				content.put("sql_statement", checkRule.getSql_statement());
				content.put("sendtime", df.format(new Date()));
				content.put("left-count",checkRule.getFrequency()-firecount);
				
				ntfi.SendEmail(content);
				ntfi.SendMessage(content);	
                  }
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error",e);
			tskdao.update(checkRule_code, createtime, null, Timestamp.valueOf(LocalDateTime.now()), null, '2', "查询失败！"
					+ "可能原因：无法连接数据库，请检查数据库用户名和密码是否正确或检查数据库是否宕机,重点检查SQL语句单词是否错误！！");
		}
		
		 sess.close();
		return result;
	//************************************************************************************	
		}else {
			try {
				SchedulingTask.getsched().deleteJob(new JobKey(checkRule.getCheckRule_code()+"Job"));
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				logger.error("error",e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*
	 * 创建要切换到的数据源连接session
	 */
	public SqlSession getexecsession(Properties properties,TasksDao tskdao,String checkRule_code,String createtime) {
		SqlSessionFactory changedatasource=null;
		try {
			reader=Resources.getResourceAsReader("com/chinalife/datacheck/config/changeDatasourceconfig.xml");
			 changedatasource=new SqlSessionFactoryBuilder().
	    			build(reader,"changeDatasource",properties);
	    //	changedatasource.getConfiguration().addMapper(Sql_statementExcutionDao.class);
		      reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error",e);
        	tskdao.update(checkRule_code, createtime, null, Timestamp.valueOf(LocalDateTime.now()), null, '2', "查询失败！"
					+ "可能原因：无法连接数据库，请检查数据库用户名和密码是否正确或检查数据库是否宕机，重点检查数据库的连接性！！！");
			
		}
    		return changedatasource.openSession(true);
	}
	
	/**
	 * 按照在SchedulingTask.java中 run(ApplicationArguments args)中设置的时间间隔
	 *清理已经调度完成结束的校验规则Job释放内存,并将校验规则的状态设置为无效(停用) 
	 */
	public void checkJobstatus() {
		SqlSession sess2=sqlSessionFactory.openSession(true);
		CheckRuleDao chRD = sess2.getMapper(CheckRuleDao.class);
		
		List<HashMap<String,String>> checkRule_codeList=chRD.checkJobstatus();
		Scheduler scd=SchedulingTask.getsched();
		for (Map<String,String> map : checkRule_codeList) {
			//system.out.println(map.get("checkRule_code"));
			try {
				if(scd.getTriggerState(new TriggerKey(map.get("checkRule_code")+"trigger")).name().equals("COMPLETE")) {
					scd.deleteJob(new JobKey(map.get("checkRule_code")+"Job"));
					chRD.stopJob(map.get("checkRule_code"),map.get("user_id"));
				}
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				logger.error("error",e);
				e.printStackTrace();
			}
		}
		sess2.close();
		
		
	}

	
	/*
	 * 创建要切换到的数据源的连接字符串存放到properties对象中
	 */
	public Properties getconnectionstr(Map<String, Object> datasource) {
	    	
	    	Properties properties=new Properties();
	    	properties.setProperty("username", (String)datasource.get("username"));
	    	properties.setProperty("password", new String(Base64Utils.decodeFromString((String)datasource.get("password"))));
	    	switch (((String)datasource.get("dbType")).toLowerCase().replace(" ", "")) {
			case "oracle":
				properties.setProperty("driver","oracle.jdbc.OracleDriver");
				String url=String.format("jdbc:oracle:thin:@%s:%s/%s",(String)datasource.
						get("dbInfo"),(String)datasource.get("port"),(String)datasource.get("sid"));
				properties.setProperty("url", url);
				break;
			case "sqlserver":
				properties.setProperty("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url1=String.format("jdbc:sqlserver://%s:%s;DatabaseName=%s",
						(String)datasource.get("dbInfo"),(String)datasource.get("port"),(String)datasource.get("sid"));
				properties.setProperty("url", url1);
				break;	
			case "mysql":
				properties.setProperty("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url2=String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&"
						+ "useSSL=false&characterEncoding=UTF-8&allowMultiQueries=true",
						(String)datasource.get("dbInfo"),(String)datasource.get("port"),
						(String)datasource.get("sid"));
				properties.setProperty("url", url2);
				////system.out.println(properties);
			default:
				break;
			}
	    	
	    	return properties;
	}
	

}
