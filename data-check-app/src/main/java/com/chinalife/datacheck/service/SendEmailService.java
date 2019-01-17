package com.chinalife.datacheck.service;

import java.util.List;
import com.alibaba.fastjson.JSONObject;


/**
 * http客户端
 * 
 * 
 * @author：WangYuanJun
 * @date：2017年12月20日 下午8:26:51
 */
public interface SendEmailService {
	
	
	String sendEmail(String workNumber,List<String> workNumbers,List<String> info_li_email,JSONObject content);
	
    /*获取邮箱*/
	//List<String> getWorkerInfotoemail(String[] workNumber);
}