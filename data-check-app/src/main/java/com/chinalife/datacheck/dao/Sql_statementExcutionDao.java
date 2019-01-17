package com.chinalife.datacheck.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;


//sql_statement执行

public interface Sql_statementExcutionDao {
	
 public  ArrayList<LinkedHashMap<String, Object>> excuting(@Param("sql_statement")String sql_statement);
}
