package com.chinalife.datacheck.controller;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinalife.datacheck.models.Checkresult;

/**
 * 
 * @author gaoyy
 *
 */
//@CrossOrigin(origins="*",maxAge=3600)
@RestController
@RequestMapping("/checkresult")
public class CheckresultController {
	
	@Autowired
	com.chinalife.datacheck.service.CheckresultService checkresultService;
	
	@RequestMapping(value = "/query")
    @ResponseBody
    public List<Checkresult> query(@RequestParam(value="code")String code, @RequestParam(value="starttime")String starttime, 
    		@RequestParam(value="endtime")String endtime,
    		@RequestParam(value="begin")String begin,
    		@RequestParam(value="end")String length
    		) throws Exception {

//		if (StringUtils.isEmpty(starttime)) {
//			
//			throw new Exception("开始日期不能为空");
//		}
//		if (StringUtils.isEmpty(endtime)) {
//			
//			throw new Exception("结束日期不能为空");
//		}
		
		return checkresultService.query(null, code, starttime, endtime,
				Integer.parseInt(begin),Integer.parseInt(length));
    }

	@RequestMapping(value = "/total")
    @ResponseBody
	public String total(@RequestParam(value="code")String code, @RequestParam(value="starttime")String starttime, @RequestParam(value="endtime")String endtime) {
		//System.out.println("----------------"+checkresultService.total(null, code, starttime, endtime));
		return checkresultService.total(null, code, starttime, endtime);
	}

}
