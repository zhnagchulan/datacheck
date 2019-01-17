package com.chinalife.datacheck.service;
/**
 * 思路：
 * 定义执行校验规则的方法
 * 
 * @author Administrator
 *
 */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public interface TasksService {
	
	        //把查询到的多条记录保持在Map对象中，各个Map对象再组成List
			//通过校验代码，查询到sql_statement,datasource,再执行sql
            List<LinkedHashMap<String, Object>> excuteCheckRule(String checkRule_code);
         
		
}

