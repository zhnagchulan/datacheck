package com.chinalife.datacheck.dao;
/**
 * 对数据库中表tb_tasks进行增删改查
 * @author Administrator
 *
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;


import com.chinalife.datacheck.models.Tasks;

public interface TasksDao {
	   //查询一条校验规则代码所有的task记录
        List<Tasks> queryByCheckRuleCode(@Param("checkRule_code")String checkRule_code); 
        
        Timestamp queryMaxNextTimeOfcheckRulecode(@Param("checkRule_code")String checkRule_code);
        void add(
        		@Param("checkRule_code")String checkRule_code,
        		@Param("create_time")Timestamp create_time,
        		@Param("start_time")Timestamp start_time,
        		@Param("end_time") Timestamp end_time,
        		@Param("next_time") Timestamp next_time,
        		@Param("fail_reason")String fail_reason
        		);
        
        void delete(@Param("task_id")String task_id);
        
        @Options(useGeneratedKeys = false)
        void update(@Param("checkRule_code")String checkRule_code,
        		@Param("create_time")String create_time,
        		@Param("start_time")Timestamp start_time,
        		@Param("end_time") Timestamp end_time,
        		@Param("next_time") Timestamp next_time,
        		@Param("task_status") char task_status,
        		@Param("fail_reason")String fail_reason);
        
         String queryIdBytimeandRulecode(@Param("checkRule_code")String checkRule_code,
        		@Param("create_time")String time);
        
    
        
        
        
        
}
