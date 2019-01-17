package com.chinalife.datacheck.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.chinalife.datacheck.models.Party;
import com.chinalife.datacheck.models.Stakeholder_to_checkRule;

/**
 * 
 * 
 * @author Mr.Li
 *思路：
 *实现对数据库中tb_stakeholder_to_checkRule表的增删改查
 */

public interface Stakeholder_to_checkRuleDao {
	void add(@Param("workNumber")String party,@Param("checkRule_code")String checkRule_code,@Param("create_time")Timestamp create_time);
	
	void delete(@Param("checkRule_code")String checkRule_code,@Param("workNumber")String workNumber);
	
	
	void update(
			@Param("checkRule_code")String checkRule_code,@Param("workNumber")String workNumber 
		,@Param("update_time")Timestamp update_time);
	
	List<Stakeholder_to_checkRule> query(@Param("checkRule_code")String checkRule_code);
	
   List<String> queryWorknumbersBycheckRule_code(@Param("checkRule_code")String checkRule_code);
	
   //通过工号获取关系人信息
	Map<String,Object> getWorkerInfo(@Param("workNumber") String workNumber);
	
	String getworkNumberByName(@Param("workerName")String workerName);
	
	
	
	
}
