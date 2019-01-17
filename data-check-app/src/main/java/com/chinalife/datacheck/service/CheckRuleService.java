package com.chinalife.datacheck.service;


import java.util.List;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Mr.Li
 *思路：
 *（1）声明校验规则的增删改查方法
 *（2）声明校验规则的其他方法
 */
public interface CheckRuleService {
    public void checkRule_add(String user_id,String sys_name,String datasource_code,char is_enable,List<String> stakeholders,String sql_statement,
    		String intent,String first_time,int interval,int frequency,char is_message);
    
  public void checkRule_delete(String user_id,String checkRule_code);  
  

  
  public JSONObject queryBycheckRule_code(String user_id,String checkRule_code);
  

void checkRule_update(String user_id, String checkRule_code, String sql_statement, char is_enable, int frequency,
		char is_message, int interval, String first_time, String intent, List<String> list);
  
 List<String> queryAllStakeholders();
 


List<String> queryDatasourceBySys(String sys_name);

List<String> querySystems(String user_id );


JSONArray querySome(String user_id, int begin, int end);

 

void batchdisable(String checkRules);


void enable(String checkRule_code);

JSONArray queryBySysName(String user_id, String sys_name, int begin, int end);
public List<String> querystakeholdersOfcheckRule(String checkRule_code);

}
