package com.chinalife.datacheck.service.impl;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chinalife.datacheck.dao.CheckRuleDao;
import com.chinalife.datacheck.dao.Sql_statementExcutionDao;
import com.chinalife.datacheck.models.CheckRule;
import com.chinalife.datacheck.models.JobTemplete;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

import java.io.Serializable;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 思路：
 * （1）实现定时任务，先 判断校验规则在不在数据库，再判断校验规则is_enable是否启用
 * 如果校验规则存在数据库中并is_enable为'1'则执行，否则不执行
 * （2）创建调度对象，创建调度触发器，创建要被调度的任务
 * （3）用调度对象绑定触发器和要被调度的任务，定时执行sql_statement
 * 
 * @author Mr.Li
 *
 */
@Component
public class SchedulingTask implements Serializable,ApplicationRunner {
    /**
	 * 
	 */
	
	private static final long serialVersionUID = -3305782652911795436L;
	
	private static final Logger log = LoggerFactory.getLogger(SchedulingTask.class);


   
    static boolean is_scheduling=false;
    //创建调度对象
    static Scheduler sched=null;
	
    //单例模式创建一个调度对象，作用同Notification.java中单例模式创建Notification对象
    public static Scheduler getsched() {
	   SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	   try {if(sched==null) {
	 sched = schedFact.getScheduler();
	   }
	} catch (SchedulerException e) {
		// TODO Auto-generated catch block
		log.error("error",e);
		e.printStackTrace();
	}
		return sched;
    	
    	
    }
    
    /**多线程互斥启动调度对象，解决问题:哪个校验规则来启动调度主进程，主进程一旦被启动
     * 其他校验规则不必再重复启动调度主进程。
     
    */
	synchronized public static void  startScheduling() {
		
		if(!is_scheduling) {
	try {
			
			getsched().start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("error",e);
			e.printStackTrace();
		}
		is_scheduling=true;
		}
		
	}
	//多线程互斥停止调度对象，即停止所有被创建的job
synchronized public static void  shutdownScheduling() {
		
		if(is_scheduling) {
	try {
			
			getsched().shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("error",e);
			e.printStackTrace();
		}
		is_scheduling=false;
		}
		
	}
	
	
       //调度任务
  /**
   * 思路:
   * (1)创建校验规则定时任务的Job
   * (2)创建该Job执行的触发器
   * (3)将job与触发器绑定在一起
   */
	public static void schedulingTask(String checkRule_code,Map<String,Object> para,Scheduler sched) {
	//	CheckRule ckR=chRD.queryBycheckRule_code(null, checkRule_code);
		Map<String, Object> jobdatamap=new HashMap<String,Object>();
		jobdatamap.put("checkRule_code", checkRule_code);
		//if (ckR!=null&& ckR.getIs_enable()=='1') {
			
			//创建要执行sql_statemnt的job
			JobDetail job=newJob(JobTemplete.class)
					.withIdentity((JobKey)para.get("jobkey"))
					.usingJobData(new JobDataMap(jobdatamap))
					.build();
			//为job确定触发器即：开始的时间，间隔，频率
			Trigger trigger=newTrigger()
					.withIdentity(checkRule_code+"trigger")
					.startAt(new Date(Timestamp.valueOf(String.valueOf(para.get("first_time"))).getTime()))
					.withSchedule(simpleSchedule().withIntervalInMinutes((Integer)para.get("intervals"))
							.withRepeatCount((Integer)para.get("frequency")-1)
							.withMisfireHandlingInstructionNextWithRemainingCount()
							)
					.build();
					 
			//将job和触发器绑定
			try {
				sched.scheduleJob(job, trigger);	
				
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				log.error("error",e);
				e.printStackTrace();
			}
					
//}
			
	}

	/**思路:
	 * (1)开机时重新调度之前因为突然"宕机"导致调度丢失的Job
	 * (2)定义一个定时任务定时查询已经调度完成的校验规则(即:trigger的状态为complete)释放其内存并
	 * 将校验规则的状态设置为无效(停用)
	 * 
	 * 分割线***************分割以上步骤 
	 */
	 @Autowired private CheckRuleDao cRD;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("恢复Job");
		
		//*************************************************************
		 Scheduler sc=SchedulingTask.getsched();
		 SchedulingTask.startScheduling();
		List<HashMap<String,String>> checkruleList=cRD.checkJobstatus();
		 for (Map<String, String> map : checkruleList) {
			CheckRule chru=cRD.queryBycheckRule_code(map.get("user_id"),map.get("checkRule_code"));
			Map<String, Object> jobmap=new HashMap<>();
			jobmap.put("jobkey",new JobKey(chru.getCheckRule_code()+"Job") );
			jobmap.put("first_time",chru.getFirst_time() );
			jobmap.put("intervals", chru.getIntervals());
			jobmap.put("frequency", chru.getFrequency());
			
		SchedulingTask.schedulingTask(chru.getCheckRule_code(), jobmap,sc);
		
		 }
	  //********************************************************************	
		 
try {
			//每20分钟扫描一次数据库找出状态为有效的校验规则并判断是否调度完成
			//如果调度完成，将状态改为无效
			JobDetail job=newJob(JobTemplete.class)
					.withIdentity(new JobKey("checkJobStatus"))
					.usingJobData("checkJobStatus", "YES")
					.build();
			
			Trigger trigger=newTrigger()
					.withIdentity("checkjobStatustrigger")
					.startNow()
					.withSchedule(repeatMinutelyForever(20))
					.build();
				sched.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.error("error",e);
			e.printStackTrace();
		}
		
	}
	 
   
}
