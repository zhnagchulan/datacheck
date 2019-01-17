package com.chinalife.datacheck.controller;

	import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
	//import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.ResponseBody;
	import org.springframework.web.bind.annotation.RestController;

	import com.chinalife.datacheck.models.Party;
	import com.chinalife.datacheck.service.PartyService;
	
     /**
	 * @author: cww
	 * @description: 关系人Controller
	 * @date: 2018/06/25 11:00
	 */
	//@CrossOrigin(origins="*",maxAge=3600)
	@RestController
	public class PartyController {
        @Autowired
	    private PartyService partyService;
        
	    //展示关系人列表
	    @GetMapping(value = "/party/listpage")
	    @ResponseBody
	    public List<Party> getInfo(
	    		@RequestParam(value="workNumber")String workNumber,
	    		@RequestParam(value="branch_name")String branch_name,
	    		@RequestParam(value="workerName")String workerName,
	    		@RequestParam(value="workerPhoNum")String workerPhoNum,
	    		@RequestParam(value="workerEmail")String workerEmail,
	    		@RequestParam(value="begin")String begin,
	    		@RequestParam(value="end")String length) {
	        return partyService.getParty(workNumber, branch_name, workerName, workerPhoNum, workerEmail,begin,length);
	    	//System.out.println(partyService.getParty(workNumber, branch_name, workerName, workerPhoNum, workerEmail).getWorkNumber());
	    }
	    
	    //前台关系人列表分页统计
	    @GetMapping(value = "/party/total")
	    @ResponseBody
	    public String total(
	    		@RequestParam(value="workNumber")String workNumber,
	    		@RequestParam(value="branch_name")String branch_name,
	    		@RequestParam(value="workerName")String workerName,
	    		@RequestParam(value="workerPhoNum")String workerPhoNum,
	    		@RequestParam(value="workerEmail")String workerEmail
	    		) {
	        return partyService.total(workNumber, branch_name, workerName, workerPhoNum,workerEmail);
	    	//System.out.println(partyService.getParty(workNumber, branch_name, workerName, workerPhoNum, workerEmail).getWorkNumber());
	    }
	    //新增关系人
	    @RequestMapping(value = "/party/add",method = RequestMethod.POST)
	    @ResponseBody
	    //@RequestBody注解常用来处理content-type不是默认的application/x-www-form-urlcoded编码的内容，比如说：application/json,通过@requestBody可以将请求体中的JSON字符串绑定到相应的bean上
	    public void addParty(@RequestBody Party party) {
	    	partyService.add(party);
	     // System.out.println(party);
	    }
	    
	    //更新关系人
	    @RequestMapping(value = "/party/edit",method = RequestMethod.POST)
	    @ResponseBody
	    public void aditParty(@RequestBody Party party) {
	    	partyService.update(party);
	    }
	    
	    //删除关系人
	    @RequestMapping(value = "/party/delete",method = RequestMethod.GET)
	    @ResponseBody
	    public void deleteParty(@RequestParam(value="workNumber")String workNumber) {
	    	partyService.delete(workNumber);
	    }
	    
	    //批量删除关系人
	    @RequestMapping(value = "/party/batchremove",method = RequestMethod.GET)
	    @ResponseBody
	    public void batchremoveParty(@RequestParam(value="workNumbers")List<String> workNumbers) {
	    	partyService.batchremove(workNumbers);
	    	//System.out.println("controller="+workNumbers);
	    }

	}

