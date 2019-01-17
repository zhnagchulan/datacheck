package com.chinalife.datacheck.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ResponseCodes {
	SUCCESS(200,"处理成功"),
	FAIL(500,"处理失败"),
	UNAUTHORIZED(401,"未授权"),
	NOT_FOUND(404,"未找到"),
	ILLEGAL_ARGUMENT(400,"错误参数"),
	DATABASE_ERROR(501,"数据库错误"),
	CLASS_ERROR(501,"类未找到"),
	NULL_EXCEPTION(500,"空值错误"),
	FILE_NOT_FOUND(404,"文件未找到"),
	METHOD_ERROR(404,"未找到方法"),
	NUMBER_FORMAT_ERROR(500,"数值转化错误"),
	INDEX_OUT_OF_BOUNDS_ERROR(500,"数组越界"),
	UNSUPPORTED_CLASS_ERROR(500,"类版本错误"),
	EXCEPTION(500,"未知错误");
	public static final Map<String,ResponseCodes> RESPONSE_CODE;
	 private static Logger logger = LoggerFactory.getLogger(ResponseCodes.class);

	static {
		Map<String,ResponseCodes> map = new TreeMap<>();
		map.put("java.lang.Exception", EXCEPTION);
		map.put("java.lang.RuntimeException", FAIL);
		map.put("java.lang.NullPointerException", NULL_EXCEPTION);
		map.put("java.sql.SQLException", DATABASE_ERROR);
		map.put("java.io.FileNotFoundException", FILE_NOT_FOUND);
		map.put("org.apache.shiro.authc.AuthenticationException", UNAUTHORIZED);
		map.put("java.net.ConnectException", DATABASE_ERROR);
		map.put("java.lang.ClassNotFoundException", CLASS_ERROR);
		map.put("java.lang.NoSuchMethodException", METHOD_ERROR);
		map.put("java.lang.IllegalArgumentException", ILLEGAL_ARGUMENT);
		map.put("java.lang.NumberFormatException", NUMBER_FORMAT_ERROR);
		map.put("java.lang.IndexOutOfBoundsException", INDEX_OUT_OF_BOUNDS_ERROR);
		map.put("java.lang.UnsupportedClassVersionError", UNSUPPORTED_CLASS_ERROR);
		map.put("javax.servlet.jsp.JspException", FAIL);
		RESPONSE_CODE = Collections.unmodifiableMap(map);
	}
	private final int code;
	private final String description;
	private ResponseCodes(int code,String description) {
		this.code=code;
		this.description=description;
	}
	public int getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	public static ResponseCodes getResponseCode(Throwable exceptionInfo) {
		ResponseCodes generalMessageCode = RESPONSE_CODE.get(exceptionInfo.getClass().getName());
        /**
         * 如果异常类型为{@link BusinessException}, 则需要将MessgeCode与ErrorCode进行拼接
         */
//        if (exceptionInfo instanceof BusinessException) {
//            BusinessException be = (BusinessException) exceptionInfo;
//            StringBuilder sb = new StringBuilder(generalMessageCode);
//            sb.append(":");
//            sb.append(be.getErrorCode());
//            return sb.toString();
//        }
        /**
         * 如果消息码依然是空的，则判断抛出的异常是否为每个声明异常的子类。
         */
        if (generalMessageCode==null) {
            Iterator<String> enumeration = RESPONSE_CODE.keySet().iterator();
            // 迭代声明集合的每一个元素
            while (enumeration.hasNext()) {
                String typeStr = enumeration.next();
                try {
                    if (Class.forName(typeStr).isAssignableFrom(exceptionInfo.getClass())) {
                        return RESPONSE_CODE.get(typeStr);
                    }
                } catch (ClassNotFoundException e) {
                    if(logger.isDebugEnabled()) {
                    	logger.debug("类错误",e);
                    }
                }
            }
        } else {
            return generalMessageCode;
        }
        /**
         * 如果消息码依然是空的，则直接取默认值9999
         */
        return FAIL;
	}
}
