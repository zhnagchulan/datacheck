package com.chinalife.datacheck.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinalife.datacheck.models.Checkresult;

public interface CheckresultDao {
	
	
	public List<Checkresult> query(@Param("appid")  String appid, @Param("code") String code, @Param("starttime")Timestamp timestamp, @Param("endtime")Timestamp timestamp2
			,@Param("begin")int begin,@Param("end")int end); 
	public String total(@Param("appid")  String appid, @Param("code") String code, @Param("starttime")Timestamp timestamp, @Param("endtime")Timestamp timestamp2
			);
}



