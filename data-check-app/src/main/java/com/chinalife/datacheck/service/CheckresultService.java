
package com.chinalife.datacheck.service;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinalife.datacheck.models.Checkresult;

public interface CheckresultService {
	
	
	public List<Checkresult> query(String appid,String code, String starttime, String endtime,
			int begin,int end);
	public String total(String appid,String code, String starttime, String endtime);

}
