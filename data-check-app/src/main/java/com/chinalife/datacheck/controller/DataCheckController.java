package com.chinalife.datacheck.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.chinalife.datacheck.models.DataCheck;
import com.chinalife.datacheck.models.OrbpsLandQueue;
import com.chinalife.datacheck.models.User;
import com.chinalife.datacheck.service.DataCheckService;


/**
 * 数据校验相关
 * @author qlin
 *
 */
//@CrossOrigin(origins="*",maxAge=3600)
@RestController
@RequestMapping("/dataCheck")
public class DataCheckController {
    @Autowired
    private DataCheckService dataCheckService;
    /**
     * 登录
     *
     * @param requestJson
     * @return
     */
//    @PostMapping("/auth")
//    public User authLogin(@RequestBody User requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "username,password");
//        return loginService.authLogin(requestJson);
//    }
    /**
     * 查询当前登录用户的信息
     *
     * @return
     */

    @GetMapping(value = "/orbpsLandTask")
    @ResponseBody
    public List<OrbpsLandQueue> getInfo(@RequestParam(value="appl_no",required=false)String appl_no) {
        return dataCheckService.get(appl_no);

    }

//    @CrossOrigin(origins = "http://localhost:8080/")
//    @GetMapping(value = "/orbpsLandTask")
//    @ResponseBody
//    public List<OrbpsLandQueue> listInfo() {
//        return dataCheckService.list();
//    }

    

    @RequestMapping("/listInfo")
    public String index() {
        return "Hello World";
    }

    /**
     * 登出
     *
     * @return
     */
//    @PostMapping("/logout")
//    public JSONObject logout() {
//        return loginService.logout();
//    }
}
