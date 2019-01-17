package com.chinalife.datacheck.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.chinalife.datacheck.models.Party;

/**
 * @author: cww
 * @description: 关系人Dao
 * @date: 2018/06/20 10:00
 */
public interface PartyDao {
	//展示关系人列表
	List<Party> getParty (@Param("workNumber") String workNumber, @Param("branch_name") String branch_name, @Param("workerName") String workerName, @Param("workerPhoNum") String workerPhoNum, @Param("workerEmail") String workerEmail
			,@Param("begin") int begin,@Param("end") int end);
    //List<Party> count();
	String total (@Param("workNumber") String workNumber, @Param("branch_name") String branch_name, @Param("workerName") String workerName, @Param("workerPhoNum") String workerPhoNum, @Param("workerEmail") String workerEmail);
	//新增关系人
	//static void addParty(@Param("party")Party party) {}
	 void add(Party party);
	  
	//更新关系人
	 void update(Party party);
	 
	//删除关系人
	 void delete(@Param("workNumber") String workNumber);
	 
    //批量删除关系人
	 void batchremove(@Param("workNumbers") String workNumbers);
	 
}
