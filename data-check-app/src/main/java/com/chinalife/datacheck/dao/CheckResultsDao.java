package com.chinalife.datacheck.dao;

import java.sql.Timestamp;


import org.apache.ibatis.annotations.Param;


import com.chinalife.datacheck.models.CheckResults;

/**
 * 思路：
 * 对数据库中表tb_checkResults增删改差
 * @author Mr.li
 *
 */

public interface CheckResultsDao {
		
	void add(@Param("task_id")int task_id,@Param("alert_sum")int alert_sum,@Param("alert_content")String alert_content
			,@Param("create_time")Timestamp create_time);
	
	void delete(@Param("task_id") int task_id);
	
	public CheckResults query(@Param("task_id") int task_id);
	
	
	
}
