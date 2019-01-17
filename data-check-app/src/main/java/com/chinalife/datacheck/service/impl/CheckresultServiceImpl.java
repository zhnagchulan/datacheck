package com.chinalife.datacheck.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinalife.datacheck.dao.CheckresultDao;
import com.chinalife.datacheck.models.Checkresult;
import com.chinalife.datacheck.service.CheckresultService;


@Service
public class CheckresultServiceImpl implements CheckresultService{
	
	@Autowired
	CheckresultDao checkresultDao;
      
	@Override
	//前台页面校验结果查询根据开始时间，结束时间之间的时间段来查询校验结果
	public List<Checkresult> query(String appid, String code, String starttime, String endtime,int begin,int end) {
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		//starttime=df.format(starttime);
		//endtime=df.format(endtime);
		return checkresultDao.query(appid, code, Timestamp.valueOf(starttime), Timestamp.valueOf(endtime),begin,end);
	}
    //供前台页面校验结果查询分页使用，分页页数=total/前台页面PageSize(默认为20)
	@Override
	public String total(String appid, String code, String starttime, String endtime) {
		// TODO Auto-generated method stub
		return checkresultDao.total(appid, code, Timestamp.valueOf(starttime), Timestamp.valueOf(endtime));
	}

}
