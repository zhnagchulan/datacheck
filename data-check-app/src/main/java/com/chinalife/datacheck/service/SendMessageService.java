package com.chinalife.datacheck.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface SendMessageService {
	

	String SendMessage(String workNumber, List<String> workNumbers, List<String> info_li_phone, JSONObject content);
	
    /*获取手机号*/
	//List<String> getWorkerInfotophone(String[] workNumber);
}
