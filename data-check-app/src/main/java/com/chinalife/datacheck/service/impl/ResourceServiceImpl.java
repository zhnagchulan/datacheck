package com.chinalife.datacheck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.chinalife.datacheck.dao.ResourceDao;
import com.chinalife.datacheck.models.Resource;
import com.chinalife.datacheck.service.ResourceService;
@Service
public class ResourceServiceImpl implements ResourceService{
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public List<Resource> get(String username){
		return resourceDao.get(username);
	}

}
