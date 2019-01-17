package com.chinalife.datacheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chinalife.datacheck.models.Resource;
import com.chinalife.datacheck.models.User;
import com.chinalife.datacheck.service.ResourceService;


//@CrossOrigin(origins="*",maxAge=3600)

@RestController
//@RequestMapping("/dataCheck")
public class ResourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resourceService;
	
    //@RequestMapping(value = "/Resource",method = RequestMethod.GET)
   @GetMapping("/Resource")
	@ResponseBody
    public List<Resource> getInfo(@RequestParam("username")String username) {
    	logger.debug("data{}",resourceService.get(username)); 
    	return resourceService.get(username);
    }
}
