package com.chinalife.datacheck.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinalife.datacheck.dao.DataCheckDao;
import com.chinalife.datacheck.models.DataCheck;
import com.chinalife.datacheck.models.OrbpsLandQueue;
import com.chinalife.datacheck.models.User;
import com.chinalife.datacheck.service.DataCheckService;
@Service
public class DataCheckServiceImpl implements DataCheckService {
	@Autowired
	private DataCheckDao dataCheckDao;
	
	@Override
	public List<OrbpsLandQueue> get(@Param("appl_no")String appl_no) {
		// TODO Auto-generated method stub
		 return dataCheckDao.get(appl_no);
	}
	@Override
	public List<OrbpsLandQueue> get() {
		// TODO Auto-generated method stub
		 return dataCheckDao.get();
	}
}
