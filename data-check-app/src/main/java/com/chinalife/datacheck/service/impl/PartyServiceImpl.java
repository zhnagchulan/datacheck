package com.chinalife.datacheck.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinalife.datacheck.dao.PartyDao;
import com.chinalife.datacheck.models.Party;
import com.chinalife.datacheck.service.PartyService;

/**
 * @author: cww
 * @description: 关系人service实现类
 * @date: 2018/06/20 10:10
 */
@Service
public class PartyServiceImpl implements PartyService {
    private Logger logger = LoggerFactory.getLogger(PartyServiceImpl.class);

    @Autowired
    private PartyDao partyDao;
    
  //展示关系人列表
    @Override
    public List<Party> getParty(String workNumber, String branch_name, String workerName, String workerPhoNum, String workerEmail,String begin,String end) {
        return partyDao.getParty(workNumber, branch_name, workerName, workerPhoNum, workerEmail,Integer.parseInt(begin),Integer.parseInt(end));
    }
    @Override
    public String total(String workNumber, String branch_name, String workerName, String workerPhoNum, String workerEmail) {
        return partyDao.total(workNumber, branch_name, workerName, workerPhoNum, workerEmail);
    }
    
    
  //新增关系人
    @Override
    public void add(Party party) {
    	partyDao.add(party);
    }
    
    //更新关系人
    @Override
    public void update(Party party) {
    	partyDao.update(party);
    }
    
    //删除关系人
    @Override
    public void delete(String workNumber) {
    	partyDao.delete(workNumber);
    }
    
    //批量删除关系人
    @Override
    public void batchremove(List<String> workNumbers) {
    	for (int i=0;i<workNumbers.size();i++) {
    		//workNumbers.get(i)
    		partyDao.batchremove(workNumbers.get(i));
    	}
    	//System.out.println("serviceimpl="+workNumbers);
    }
	
        
}
