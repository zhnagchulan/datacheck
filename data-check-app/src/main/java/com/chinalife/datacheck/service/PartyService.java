package com.chinalife.datacheck.service;

import java.util.List;
import com.chinalife.datacheck.models.Party;

/**
 * @author: cww
 * @description: 关系人Service
 * @date: 2018/06/26 14:00
 */
public interface PartyService {
	//展示关系人列表
	List<Party> getParty(String workNumber, String branch_name, String workerName, String workerPhoNum, String workerEmail,String begin,String end);
	//统计总关系人数目供前台关系人列表分页功能
	String total(String workNumber, String branch_name, String workerName, String workerPhoNum, String workerEmail);	
	//新增关系人
	void add(Party party);
	
	//更新关系人
	void update(Party party);
	
	//删除关系人
	void delete(String workNumber);
	
	//批量删除关系人
	void batchremove(List<String> workNumbers);
	
}
