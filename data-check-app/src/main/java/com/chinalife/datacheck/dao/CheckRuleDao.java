package com.chinalife.datacheck.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.ibatis.annotations.Param;

import com.chinalife.datacheck.models.CheckRule;



public interface CheckRuleDao {
     //校验规则的增加
	 public void checkRule_add(
			 @Param("checkRule_code") String checkRule_code, 
			 @Param("user_id")String user_id,
			 @Param("app_id")Integer app_id,
			 @Param("datasource_code")String datasource_code ,
			 @Param("intervals")int intervals,
			 @Param("create_time")Timestamp create_time,
			 @Param("frequency")int frequency,
			 @Param("sql_statement")String sql_statement,
			 @Param("intent")String intent,
			 @Param("is_message")char is_message,
			 @Param("is_enable")char is_enable,
			 @Param("first_time")Timestamp first_time);
	   
	 //查询所有关系人
	 public List<String> queryAllStakeholders(); 
	 
	 //删除校验规则
	  public void checkRule_delete(
			  @Param("user_id")String user_id,
			  @Param("checkRule_code")String checkRule_code);  
	  
	  //查询所有的系统
	 public  List<String> querySystems(@Param("user_id") String user_id);
	 
	 //通过系统查数据源
	 public List<String> queryDatasourceBySys( @Param("app_id")int app_id);
		
	//通过系统名称查系统id
	  public List<Integer> querySystemId( @Param("sys_name")String sys_name);
	  
	  //通过系统名称查校验规则
	  public List<CheckRule> queryBySysName(@Param("user_id")String user_id,@Param("sys_name")String sys_name,
			  @Param("begin") int begin,@Param("end") int end
			  );
	  
	  public List<CheckRule> querySome(@Param("user_id")String user_id,@Param("begin") int begin,@Param("end") int end);;
	  
	  //通过校验代码查校验规则
	  public CheckRule queryBycheckRule_code(@Param("user_id")String user_id,@Param("checkRule_code")String checkRule_code);
	  
	  //校验规则的更新
	  public void checkRule_update(
				
	  @Param("checkRule_code") String checkRule_code, 
		 @Param("user_id")String user_id,
		 @Param("intervals")int intervals,
		 @Param("create_time")Timestamp create_time,
		 @Param("frequency")int frequency,
		 @Param("sql_statement")String sql_statement,
		 @Param("intent")String intent,
		 @Param("is_message")char is_message,
		 @Param("is_enable")char is_enable,
		 @Param("first_time")Timestamp first_time,
		 @Param("update_time")Timestamp update_time);
	  //通过系统id查系统名称
	  public String querySystemName( @Param("app_id")int app_id);
	  
	  //通过系统名称查询系统简称
	  public String queryappSnameByName( @Param("app_name")String app_name);
	  
	public Map<String, Object> queryDatasource(@Param("datasource_code") String datasource_code);
	 public String totalByuser_id(@Param("user_id") String user_id);
	 public String total(@Param("user_id") String user_id,@Param("app_id")int app_id);
	 public void batchdisable(@Param("checkRules") String checkRules ) ;
	 public void enable(@Param("checkRule_code") String checkRule_code ) ;
	 
	 public Map<String,String> checkStatusBeforeExc(@Param("app_id")int app_id,
			 @Param("datasource_code") String datasource_code);
	 public List<HashMap<String,String>> checkJobstatus();
	 public void stopJob(@Param("checkRule_code") String checkRule_code,@Param("user_id") String user_id);
	 public List<String> querystakeholdersOfcheckRule(@Param("checkRule_code")String checkRule_code);
}
