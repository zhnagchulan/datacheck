package com.chinalife.datacheck.models;

import java.io.Serializable;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.chinalife.datacheck.service.impl.TaskServiceImp;
public class JobTemplete implements Job,Serializable {
	/**
	 * 定义定时任务的job
	 * @author Mr.Li
	 */
	private static final long serialVersionUID = -9148935617853379266L;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	      JobDataMap dataMap = context.getJobDetail().getJobDataMap();
	      TaskServiceImp tsi=TaskServiceImp.getSingle();
	      if(dataMap.getString("checkJobStatus")!=null) {
	    	  tsi.checkJobstatus();
	      }else {
	      String checkRule_code=(String)dataMap.get("checkRule_code");
	      tsi.excuteCheckRule(checkRule_code);}
	
	}

}
