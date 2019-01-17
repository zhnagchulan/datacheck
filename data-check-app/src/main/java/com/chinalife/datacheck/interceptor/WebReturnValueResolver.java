package com.chinalife.datacheck.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.view.AbstractView;

import com.alibaba.fastjson.JSON;
import com.chinalife.datacheck.models.ResponseData;
import com.chinalife.datacheck.utils.ResponseMesageFactoryImpl;
import com.chinalife.datacheck.web.annotation.ResponseMessage;

/**
 * 对每个方法的返回值上挂载@ResponseMessage注解的，封装自定义的Message类型.
 *
 * @author maeagle
 * @date 2016-2-5 16 :46:32
 */
public class WebReturnValueResolver implements HandlerMethodReturnValueHandler {
	 private final HandlerMethodReturnValueHandler delegate;

	    public WebReturnValueResolver(HandlerMethodReturnValueHandler delegate) {
	        this.delegate = delegate;
	    }

    /**
     * 日志管理.
     */
//    private static Logger logger = LoggerFactory.getLogger(WebReturnValueResolver.class);
    /**
     * WebResponseMessage的构建工具.
     */
//    private ResponseMesageFactory responseMesageFactory = SpringContextHolder.getBean(ResponseMesageFactory.class);

    /**
     * 根据传入参数，判断是否能够对返回值进行WebResponseMessage封装
     * <p>
     * 目前全部返回参数都要转为WebResponseMessage格式的JSON
     * <p>
     * Whether the given {@linkplain MethodParameter method return type} is
     * supported by this handler.
     *
     * @param returnType the method return type to check
     * @return {@code true} if this handler supports the supplied return type;
     * {@code false} otherwise
     * @author maeagle
     * @date 2016-2-5 16 :46:32
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
//        return (AnnotationUtils.findAnnotation(returnType.getContainingClass(), ResponseMessage.class) != null ||
//                returnType.getMethodAnnotation(ResponseMessage.class) != null);
    	return true;
    }

    /**
     * 将每个controller上的方法返回值都封装为一个WebResponseMessage类型消息做返回
     * <p>
     * Handle the given return value by adding attributes to the api and
     * setting a view or setting the
     * {@link ModelAndViewContainer#setRequestHandled} flag to {@code true}
     * to indicate the response has been handled directly.
     *
     * @param returnValue  the value returned from the handler method
     * @param returnType   the type of the return value. This type must have
     *                     previously been passed to {@link #supportsReturnType} which must
     *                     have returned {@code true}.
     * @param mavContainer the ModelAndViewContainer for the current request
     * @param webRequest   the current request
     * @throws Exception if the return value handling results in an error
     * @author maeagle
     * @date 2016-2-5 16 :46:32
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        ResponseData webMessage = null;
       
        if (returnValue instanceof Boolean)
            webMessage = (Boolean) returnValue ? ResponseMesageFactoryImpl.getSuccess() : ResponseMesageFactoryImpl.getFaildMessage(new Exception());
        else {
            webMessage = ResponseMesageFactoryImpl.getSuccess(returnValue);
        }

        /** 校验返回值是否符合验证器要求**/
//        validateReturnValue(returnValue, returnType);
        /**
         * 尝试查找日期格式文本
         */
        String dateformat =null;
        ResponseMessage rm=returnType.getMethodAnnotation(ResponseMessage.class);
        if(rm!=null) {
        	returnType.getMethodAnnotation(ResponseMessage.class).dateFormat();
        }

        /** 构建视图，将消息对象以JSON形式发出**/
        final ResponseData finalWebMessage = webMessage;
        mavContainer.setView(new AbstractView() {
            @Override
            protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//                httpServletResponse.setCharacterEncoding(PropertiesUtils.getProperty("system.encoding"));
                if (StringUtils.isNotEmpty(dateformat))
                    httpServletResponse.getWriter().print(JSON.toJSONStringWithDateFormat(finalWebMessage, dateformat));
                else
                    httpServletResponse.getWriter().print(JSON.toJSONString(finalWebMessage));
            }
        });
        delegate.handleReturnValue(finalWebMessage, returnType, mavContainer, webRequest);
    }


    /**
     * 验证返回值是否符合验证器要求.
     *
     * @param returnValue the return value
     * @param returnType  the return type
     * @throws ValidateException the exception
     */
//    private void validateReturnValue(Object returnValue, MethodParameter returnType) {
//
//        if (returnValue == null)
//            throw new NullPointerException("controller的返回值不能为空!");
//
//        Validator validator = ((LocalValidatorFactoryBean) SpringContextHolder.getBean("validatorService")).getValidator();
//        /**
//         * 检查方法返回值本身的验证器
//         */
//        Set<ConstraintViolation<Object>> returnViolations = validator.forExecutables()
//                .validateReturnValue(parseContainerInstance(returnType), returnType.getMethod(), returnValue);
//        if (CollectionUtils.isNotEmpty(returnViolations))
//            throw new ValidateException(returnViolations.stream().map(v -> String.format("ReturnValue of Method[%s.%s]:%s;", returnType.getMethod().getDeclaringClass().getName(), returnType.getMethod().getName(), v.getMessage())).collect(Collectors.joining("\n")));
//
//        /**
//         * 检查返回值类型对象中的验证器
//         */
//        boolean responseNeedValidate = Arrays.stream(returnType.getParameterAnnotations()).anyMatch(ann -> AnnotationUtils.getAnnotation(ann, Validated.class) != null);
//        Set<ConstraintViolation<Object>> returnObjectViolations = validator.validate(returnValue);
//        if (CollectionUtils.isNotEmpty(returnObjectViolations))
//            throw new ValidateException(returnObjectViolations.stream().map(this::buildMessage).collect(Collectors.joining("\n")));
//    }

//    private String buildMessage(ConstraintViolation<Object> violation) {
//        StringBuilder sb = new StringBuilder("ReturnValue of Method[");
//        sb.append(violation.getLeafBean().getClass().getName());
//        sb.append(".");
//        sb.append(((PathImpl) violation.getPropertyPath()).getLeafNode().getName());
//        sb.append("]:");
//        sb.append(violation.getMessage());
//        sb.append(";");
//        return sb.toString();
//    }

    /**
     * 通过返回值类型对象，解析出{@link org.springframework.web.servlet.mvc.Controller}实例
     *
     * @param returnType 返回值类型对象
     * @return {@link org.springframework.web.servlet.mvc.Controller}实例
     */
//    private Object parseContainerInstance(MethodParameter returnType) {
//        try {
//            return SpringContextHolder.getBean(returnType.getContainingClass());
//        } catch (Exception e) {
//            if (logger.isDebugEnabled())
//                logger.debug(e.getMessage(), e);
//            String className = returnType.getContainingClass().getSimpleName();
//            return SpringContextHolder.getBean(Arrays.stream(SpringContextHolder.get().getBeanNamesForAnnotation(Controller.class))
//                    .filter(beanName -> StringUtils.indexOfIgnoreCase(beanName, className) != -1)
//                    .findFirst().orElse(null));
//        }
//    }
}
