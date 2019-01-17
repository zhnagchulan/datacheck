package com.chinalife.datacheck.service.impl;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.chinalife.datacheck.dao.Stakeholder_to_checkRuleDao;
import com.chinalife.datacheck.service. SendEmailService;
import com.chinalife.datacheck.service. SendMessageService;



/**
 * @author: hxy
 * @description: 登录service实现类
 * @date: 2017/10/24 11:53
 */

public class Notification implements SendEmailService,SendMessageService{
	//logger的作用同CheckRuleServiceImpl.java中logger作用
	 private Logger logger = LoggerFactory.getLogger(Notification.class);
	String notiAccount="18200360";
	 private  Stakeholder_to_checkRuleDao satkeholdertocheckruleDao=null;
	private static Notification noti=null;
	//单例模式生成Notification对象，目的是避免每次使用本类对象时new出来对象，减少内存
	static public Notification getSingle() {
		if(noti==null) {
			noti=new Notification();
		}
		return noti;
	}
    
	public Stakeholder_to_checkRuleDao getSatkeholdertocheckruleDao() {
		return satkeholdertocheckruleDao;
	}

	public void setSatkeholdertocheckruleDao(Stakeholder_to_checkRuleDao satkeholdertocheckruleDao) {
		this.satkeholdertocheckruleDao = satkeholdertocheckruleDao;
	}

	//获取告警信息所对应的校验规则代码
	String getCheckRule_code(JSONObject content){
		return content.get("checkRule_code").toString();
	}
	
           //获取此条告警信息要通知的关系人工号
    public List<String> getWorknumbers(String checkRule_code) {
        return satkeholdertocheckruleDao.queryWorknumbersBycheckRule_code(checkRule_code);
    	
    }

 

	//获取将要被发送Email的关系人Email列表
    public List<String> getWorkerInfotoemail(List<String> workNumbers) {
       
    	String[] worknums=  workNumbers.toArray(new String[workNumbers.size()]);
    	List<String> info_li_email=new ArrayList<String>();
    	Map<String,Object> getinfo=new HashMap<>();
    	for(int i=0;i<worknums.length;i++){ 
    		 getinfo  = satkeholdertocheckruleDao.getWorkerInfo(worknums[i]);
       		
        	info_li_email.add((String) getinfo.get("workerEmail"));
        }
        
        return info_li_email;
        }
	
	 
    //获取将要被发送短信的关系人电话号码列表
    public List<String> getWorkerInfotophone(List<String> workNumbers) {
       
    	String[] worknums=workNumbers.toArray(new String[workNumbers.size()]);
        List<String> info_li_phone=new ArrayList<String>();
        Map<String,Object> getinfo=new HashMap<>();
    	for(int i=0;i<worknums.length;i++){ 
    		getinfo  = satkeholdertocheckruleDao.getWorkerInfo(worknums[i]);
       		info_li_phone.add((String) getinfo.get("workerPhoNum"));
       		
            }
        return info_li_phone;
    }
	
	@Override
	//此段代码详情询问俞佳蔚
	public  String sendEmail(String Account,List<String> workNumbers,List<String> info_li_email,JSONObject content){  
		//发送邮件
		// 创建默认的httpClient实例.   
		String result="发送失败";
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://notice.clic:8081/ultraNotice/zabbix/alarm");  
        //添加header
 	   JSONObject map = new JSONObject(); 
       JSONObject head = new JSONObject(); 
       head.put("token", "1530258161223"); 
       head.put("userChannel", "103"); 
       head.put("msgType", "1"); 
       head.put("sendChannel", "1"); 
       JSONObject param1 = new JSONObject(); 
       param1.put("userid",Account); 
       head.put("user", param1.toString()); 
       JSONObject bod = new JSONObject(); 
       bod.put("title", "自动化校验平台");
      String cont="\n"+
       "系统名称:"+content.getString("system_name")+"\n"+
       "校验规则代码:"+content.getString("checkRule_code")+"\n"+
       "校验规则目的:"+content.getString("intent")+"\n"+
       "告警条数:"+content.getString("alert_sum")+"\n"+
       "执行的校验规则:"+content.getString("sql_statement")+"\n"+
       "剩余执行次数:"+content.getString("left-count")+"\n"+
       "告警时间:"+content.getString("sendtime")+"\n";;	  
       bod.put("text", cont); 
       JSONObject param2 = new JSONObject(); 
       param2.put("phone", "");
       JSONObject param3 = new JSONObject(); 
       param3.put("email", info_li_email.toString().replace("[", "").replace("]", "")); 
       param3.put("copyMail", ""); 
       param3.put("blindMail", ""); 
       bod.put("MAIL", param3.toString()); 
       JSONObject param4 = new JSONObject(); 
       param4.put("cloudId", workNumbers.toString()
    		   .replace("[", "").replace("]", "").replaceAll(" ", "").replaceAll(" ", "")); 
       bod.put("CLOUD", param4); 
       bod.put("opDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        
        
        JSONObject response = null;
        try {
        	map.put("header", head.toString()); 
            map.put("body", bod.toString()); 
           
            //httpPost.setEntity(new StringEntity(map.toString(), "utf-8")); 
           // System.out.println("请求地址："+"http://10.253.129.68:80/ultraNotice/zabbix/alarm"); 
           // System.out.println("请求参数："+map.toString()); 
            StringEntity s = new StringEntity(map.toString(), "utf-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            httppost.setEntity(s);
            HttpResponse res = httpclient.execute(httppost);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
              ///String result = EntityUtils.toString(res.getEntity());// 返回json格式：
             // System.out.println("result : "+result);
            	result="发送成功";
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          }finally {  
              // 关闭连接,释放资源    
              try {  
                  httpclient.close();  
              } catch (IOException e) {  
            	  logger.error("error", e);
                  e.printStackTrace();  
              }  
          }
	       return result;  
	       //return dd.toJSONString();
	       	   } 
		
	@Override
	/*
	 * 此段代码详情询问俞佳蔚
	 * */
	public  String SendMessage(String Account,List<String> workNumbers,List<String> info_li_phone,JSONObject content){  
		//发送邮件
		// 创建默认的httpClient实例.  
	String result="发送失败";
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://notice.clic:8081/ultraNotice/zabbix/alarm");  
        //添加header
 	   JSONObject map = new JSONObject(); 
       JSONObject head = new JSONObject(); 
       head.put("token", "1530258161223"); 
       head.put("userChannel", "103"); 
       head.put("msgType", "1"); 
       head.put("sendChannel", "1"); 
       JSONObject param1 = new JSONObject(); 
       param1.put("userid", Account); 
       head.put("user", param1.toString()); 
       JSONObject bod = new JSONObject(); 
       bod.put("title", "自动化校验平台");
       String cont="\n"+
    	       "系统名称:"+content.getString("system_name")+"\n"+
    	       "校验规则代码:"+content.getString("checkRule_code")+"\n"+
    	       "校验规则目的:"+content.getString("intent")+"\n"+
    	       "告警条数:"+content.getString("alert_sum")+"\n"+
    	       "剩余执行次数:"+content.getString("left-count")+"\n"+
    	       "告警时间:"+content.getString("sendtime")+"\n";
    	       bod.put("text", cont); 
       JSONObject param2 = new JSONObject(); 
       param2.put("phone", info_li_phone.toString()
    		   .replace("[", "").replace("]", "").replaceAll(" ", ""));
       bod.put("SMS", param2); 
       JSONObject param3 = new JSONObject(); 
       param3.put("email", ""); 
       param3.put("copyMail", ""); 
       param3.put("blindMail", ""); 
       bod.put("MAIL", param3.toString()); 
       JSONObject param4 = new JSONObject(); 
       param4.put("cloudId", workNumbers.toString()
    		   .replace("[", "").replace("]", "").replaceAll(" ", "")); 
       bod.put("CLOUD", param4); 
       bod.put("opDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        
        
        JSONObject response = null;
        try {
        	map.put("header", head.toString()); 
            map.put("body", bod.toString()); 
           
            //httpPost.setEntity(new StringEntity(map.toString(), "utf-8")); 
           // System.out.println("请求地址："+"http://10.253.129.68:80/ultraNotice/zabbix/alarm"); 
           // System.out.println("请求参数："+map.toString()); 
            StringEntity s = new StringEntity(map.toString(), "utf-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            httppost.setEntity(s);
            HttpResponse res = httpclient.execute(httppost);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
              //String result = EntityUtils.toString(res.getEntity());// 返回json格式：
              ////system.out.println("result : "+result);
            	result="发送成功";
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          }finally {  
              // 关闭连接,释放资源    
              try {  
                  httpclient.close();  
              } catch (IOException e) {  
            	  logger.error("error",e);
                  e.printStackTrace();  
              }  
          }  
	       return result;  
	       //return dd.toJSONString();
	       	   } 
	   
	
       //其他类中需发短信，通过本类中getSingle()获得本类对象,再通过"对象.SendMessage(content)"执行方法	
 	    public void SendMessage(JSONObject content) {
		   String checkRule_code=getCheckRule_code(content);
		   List<String> workNumbers=getWorknumbers(checkRule_code);
		   List<String> info_li_phone=getWorkerInfotophone(workNumbers);
		      
		   if(SendMessage(notiAccount,workNumbers, info_li_phone, content).contains("失败")) {
			   try {
				Thread.sleep(10000);
				SendMessage(notiAccount,workNumbers, info_li_phone, content);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error("error",e);
				e.printStackTrace();
				//System.out.println("发送短信失败！！");
			}
			   
		   }   
	   }
	   
 	   //其他类中需发邮件，通过本类中getSingle()获得本类对象,再通过"对象.SendEmail(content)"执行方法	
	    public void SendEmail(JSONObject content) {
		   String checkRule_code=getCheckRule_code(content);
		   List<String> workNumbers=getWorknumbers(checkRule_code);
		   List<String> info_li_email=getWorkerInfotoemail(workNumbers);
		   if ( sendEmail(notiAccount,workNumbers, info_li_email, content).contains("失败")) {
			   try {
				Thread.sleep(10000);
				sendEmail(notiAccount, info_li_email,workNumbers, content);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error("error", e);
				e.printStackTrace();
				//System.out.println("发送邮件失败！！");
			}
			   
		}
		  
	   }
	   

   
}
