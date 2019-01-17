package com.chinalife.datacheck.utils;
import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.chinalife.datacheck.models.ResponseData;
import com.chinalife.datacheck.web.annotation.ResponseMessage;

public class ResponseMesageFactoryImpl {
   
    public static ResponseData getSuccess() {
    	ResponseData responseMessage = createResponseMessage();
        responseMessage.setSuccess(true);
        ResponseCodes code=ResponseCodes.SUCCESS;
        responseMessage.setCode(code.getCode());
        responseMessage.setMsg(code.getDescription());
        return responseMessage;
    }

    public static ResponseData getSuccess(Object data) {
    	ResponseData responseMessage = createResponseMessage();
        responseMessage.setSuccess(true);
        ResponseCodes code=ResponseCodes.SUCCESS;
        responseMessage.setCode(code.getCode());
        responseMessage.setMsg(code.getDescription());
        responseMessage.setData(data);
        return responseMessage;
    }

    public static ResponseData getSuccessMessage(String content) {
    	ResponseData responseMessage = createResponseMessage();
        responseMessage.setSuccess(true);
        ResponseCodes code=ResponseCodes.SUCCESS;
        responseMessage.setCode(code.getCode());
        responseMessage.setMsg(content);
        return responseMessage;
    }

    public static ResponseData getFaildMessage(Throwable exceptionInfo) {
    	ResponseData responseMessage = createResponseMessage();
        responseMessage.setSuccess(false);
        ResponseCodes messageCode = ResponseCodes.getResponseCode(exceptionInfo);
        responseMessage.setCode(messageCode.getCode());
        responseMessage.setMsg(messageCode.getDescription());
        return responseMessage;
    }

    /**
     * 构建“处理失败”的{@link ResponseMessage}.
     *
     * @param exceptionInfo 异常对象
     * @param content       消息内容
     * @return {@link ResponseMessage}
     * @date 2016-1-29 8 :57:29
     */
    public static ResponseData getFaildMessage(Throwable exceptionInfo, String content) {
    	ResponseData responseMessage = createResponseMessage();
        responseMessage.setSuccess(false);
        ResponseCodes code= ResponseCodes.getResponseCode(exceptionInfo);
        responseMessage.setCode(code.getCode());
        responseMessage.setMsg(code.getDescription());
        return responseMessage;
    }

    /**
     * 创建{@link ResponseMessage}对象，填充事务码和当前日期.
     *
     * @return {@link ResponseMessage}
     * @date 2016-1-29 10 :26:34
     */
    private static ResponseData createResponseMessage() {
    	ResponseData responseMessage = new ResponseData();
        responseMessage.setDateTime(DateFormatUtils.format(Calendar.getInstance().getTime(),"yyy/MM/dd HH:mm:ss"));
        return responseMessage;
    }
}
