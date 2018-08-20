package com.dongyl.log;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.dongyl.utils.LazyInitValidatorConfig;
import com.dongyl.utils.MsgTemplateResolverUtils;
import com.dongyl.utils.json.JsonUtil;
import com.dongyl.validate.bean.BaseRequest;
import com.dongyl.validate.bean.CommonDes;
import com.dongyl.validate.error.BaseErrorCode;
import com.dongyl.validate.error.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Set;

import static com.dongyl.log.Contants.CALLER_CHAIN_ID;
import static com.dongyl.log.Contants.LOG_MAX_LENGTH;

/**
 * @author dongyl
 * @date 20:36 8/13/18
 * @project framework
 */
@Activate(group = {com.alibaba.dubbo.common.Constants.PROVIDER, Constants.CONSUMER})
public class DubboLoggerFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ERROR_CODE = "CODE";
    private static final String ERROR_MSG = "MSG";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        boolean isConsumerSide = context.isConsumerSide();
        final String methodName = invoker.getInterface().getCanonicalName();
        long start = 0;
        long useTime = 0;
        String cost = null;
        try {

            Object[] args = invocation.getArguments();
            //符合规范的调用
            boolean isRegular = (args != null && args.length == 1 && args[0] instanceof BaseRequest);

            if (isConsumerSide) {
//                if(isRegular) {
//                    try {
//                        Validator validator = LazyInitValidatorConfig.getValidator();
//
//                        @SuppressWarnings("rawtypes")
//                        ConstraintViolation constraintViolation = null;
//                        Set<ConstraintViolation<Object>> set = validator.validate(args[0]);
//                        if (set != null && !set.isEmpty()) {
//                            constraintViolation = set.iterator().next();
//                        }
//                        if (constraintViolation != null) {
//                            ErrorCode errorCode = BasicErrorCode.BASIC_INPUT_PARAM_ERROR;
//                            int errorValue = errorCode.getValue();
//                            String errorMsg = errorCode.getComment() + ":" + MsgTemplateResolverUtils.getMsgFromViolation(constraintViolation);
//                            Result result = toRpcResult(invoker, invocation, errorValue, errorMsg);
//                            if (result != null) {
//                                logger.warn("请求参数错误,接口：{},错误原因：{},自动返回结果：{}",
//                                        methodName, errorMsg, result);
//                                logger.warn("错误码:{},错误原因:{}",errorValue,errorMsg);
//                                return result;
//                            }
//                            logger.warn("请求中参数有误，但是无法自动处理返回结果，将进入调用链{}", errorValue);
//                        }
//                    } catch (Throwable e) {
//                        logger.warn("验证请求中参数有误，自动处理返回结果出现异常，将进入调用链", e);
//                    }
//                }

                String reqId = TraceKeyHolder.getTraceKey();
                if (reqId != null) {
                    RpcContext.getContext().setAttachment(CALLER_CHAIN_ID, reqId);
                }
                start = System.currentTimeMillis();

            } else {
                Object value = RpcContext.getContext().getAttachment(CALLER_CHAIN_ID);
                if (null != value) {
                    TraceKeyHolder.setTraceKey(value.toString());
                    MDC.put("reqId", value.toString());
                } else if (isRegular) {
                    BaseRequest baseRequest = (BaseRequest) args[0];
                    String reqId = baseRequest.getReqId();
                    if (StringUtils.isNotEmpty(reqId)) {
                        TraceKeyHolder.setTraceKey(reqId);
                        MDC.put("reqId", reqId);
                    }
                }
                if (isRegular) {
                    try {
                        Validator validator = LazyInitValidatorConfig.getValidator();
                        @SuppressWarnings("rawtypes")
                        ConstraintViolation constraintViolation = null;
                        Set<ConstraintViolation<Object>> set = validator.validate(args[0]);
                        if (set != null && !set.isEmpty()) {
                            constraintViolation = set.iterator().next();
                        }
                        if (constraintViolation != null) {
                            ErrorCode errorCode = BaseErrorCode.BASIC_INPUT_PARAM_ILLEGAL;
                            int ErrCode = errorCode.getCode();
                            String errorMsg = errorCode.getMessage() + ":"
                                    + MsgTemplateResolverUtils.getMsgFromViolation(constraintViolation);
                            Result result = toRpcResult(invoker, invocation, ErrCode, errorMsg);
                            if (result != null) {
                                LOGGER.warn("请求参数错误,接口：{}，错误原因：{}，自动返回结果：{}",
                                        methodName, errorMsg, result);
                                LOGGER.warn("错误码:{}，错误原因:{}", ErrCode, errorMsg);
                                return result;
                            }
                            LOGGER.warn("请求中参数有误，但是无法自动处理返回结果，将进入调用链{}", ErrCode);
                        }
                    } catch (Exception e) {
                        LOGGER.warn("验证请求中参数有误，自动处理返回结果出现异常，将进入调用链", e);
                    }
                    start = System.currentTimeMillis();
                    LOGGER.info("[{}][REQUEST:{}]", methodName, args[0].toString());
                } else if (args != null && args.length == 0) {
                    start = System.currentTimeMillis();
                    LOGGER.info("[{}][REQUEST:{}]", methodName, "");
                }
            }
            Result result = invoker.invoke(invocation);
            isRegular = (result != null && result.getValue() instanceof CommonDes);
            if (isRegular) {
                useTime = System.currentTimeMillis() - start;
                double percent = useTime / 1000;
                DecimalFormat format = new DecimalFormat("0.000");
                cost = format.format(percent) + "s";
//                cost = format((useTime) / 1000.0);
            }
            if (!isConsumerSide) {
                if (isRegular) {
                    //final long useTime = System.currentTimeMillis() - start;
                    byte[] bytes = JsonUtil.objToByte(result.getValue());
                    try {
                        LOGGER.info("[{}][COST TIME:{}][RESPONSE:{}]", methodName, cost, new String(bytes, 0, Math.min(bytes.length, LOG_MAX_LENGTH), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.warn("返回参数字符集不被支持", e);
                    }
//                    byte[] bytes = JsonUtil.obj2Byte(result.getValue());
//                    try {
//                        logger.info("[RESPONSE][{}][{}]", methodName,new String(bytes,0,Math.min(bytes.length,LOG_MAX_LENGTH),"UTF-8"));
//                    } catch (UnsupportedEncodingException e) {
//                        logger.warn("返回参数字符集不被支持", e);
//                    }
//                    logger.info("[COST TIME][{}][{}]", methodName, cost);

                    try {
                        Validator validator = LazyInitValidatorConfig.getValidator();
                        @SuppressWarnings("rawtypes")
                        ConstraintViolation constraintViolation = null;
                        Set<ConstraintViolation<Object>> set = validator.validate(result.getValue());
                        if (set != null && !set.isEmpty()) {
                            constraintViolation = set.iterator().next();
                        }
                        if (constraintViolation != null) {
                            ErrorCode errorCode = BaseErrorCode.BASIC_OUTPUT_PARAM_ERROR;
                            int errCode = errorCode.getCode();
                            String errorMsg = errorCode.getMessage() + ":" + MsgTemplateResolverUtils.getMsgFromViolation(constraintViolation);
                            CommonDes response = (CommonDes) result.getValue();
                            response.setCode(errCode);
                            response.setMessage(errorMsg);
                            LOGGER.warn("本地服务返回参数错误，接口：{}，错误原因：{}，返回结果：{}",
                                    methodName, errorMsg, result);
                            return result;
                        }
                    } catch (Exception e) {
                        LOGGER.warn("验证返回中参数有误，自动处理返回结果出现异常", e);
                    }
                }
                result = handleExp(result, invoker, invocation);
                //执行后 执行其他的后置执行线程
                ThreadAfterHolder.executeAfter();
            } else {
                if (isRegular) {
                    CommonDes resp = (CommonDes) result.getValue();
                    if (!resp.success()) {
                        LOGGER.warn("远端{}接口：{}，错误码：{}，错误原因：{}", RpcContext.getContext().getRemoteAddressString(), methodName, resp.getCode(), resp.getMessage());
                    } else if (useTime > 1000) {
                        LOGGER.warn("远端{}接口：{} COST TIME {}", RpcContext.getContext().getRemoteAddressString(), methodName, cost);
                    }
                }
            }
            return result;
        } finally {
            if (isConsumerSide) {
                RpcContext.getContext().removeAttachment(CALLER_CHAIN_ID);
            } else {
                TraceKeyHolder.clear();
                MDC.remove("reqId");
            }
        }
    }


    private String format(double value) {
        return new Formatter().format("%.3f", value).toString() + "s";
    }

    /**
     * 尝试建立错误码的调用结果
     *
     * @param invoker
     * @param invocation
     * @param errorValue
     * @param errorMsg
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    private Result toRpcResult(Invoker<?> invoker, Invocation invocation, int errorValue, String errorMsg) throws NoSuchMethodException {
        Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        if (!method.getReturnType().getClass().isInstance(CommonDes.class)) {
            LOGGER.warn("方法返回类型不符，不能进行错误码的设置{}", invoker.getInterface().getCanonicalName() + "." + method.getName());
            return null;
        }
//        if(method.getReturnType().isPrimitive()){
//            logger.warn("方法返回类型为基本类型，不能进行错误码的设置{}",invoker.getInterface().getCanonicalName()+"."+method.getName());
//            return null;
//        }
//        if(method.getReturnType().isInterface()){
//            logger.warn("方法返回类型为接口，不能进行错误码的设置{}",invoker.getInterface().getCanonicalName()+"."+method.getName());
//            return null;
//        }

        Object value = getNewInstance(method);
//        try {
//            value = method.getReturnType().newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//            LOGGER.warn("不能进行实例化，必须包含无参数的构造方法", e);
//            return null;
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            LOGGER.warn("IllegalAccessException", e);
//            return null;
//        }
        PropertyDescriptor codeDescriptor;
        PropertyDescriptor msgDescriptor;
        try {
            codeDescriptor = new PropertyDescriptor(ERROR_CODE, method.getReturnType());
        } catch (IntrospectionException e) {
            e.printStackTrace();
            LOGGER.warn("无法设置错误码，必须有" + ERROR_CODE + "的get和set方法", e);
            return null;
        }
        try {
            msgDescriptor = new PropertyDescriptor(ERROR_MSG, method.getReturnType());
        } catch (IntrospectionException e) {
            e.printStackTrace();
            LOGGER.warn("无法设置错误消息提示，必须有" + ERROR_MSG + "的get和set方法", e);
            return null;
        }
        try {
            codeDescriptor.getWriteMethod().invoke(value, errorValue);
            msgDescriptor.getWriteMethod().invoke(value, errorMsg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LOGGER.warn("无法设置错误信息，code或message数据类型非String，类:" + value.getClass().getCanonicalName(), e);
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            LOGGER.warn("无法设置错误错误信息，code或message可访问的get和set方法，类：" + value.getClass().getCanonicalName(), e);
            return null;
        }
        return new RpcResult(value);
    }

    private Object getNewInstance(Method method) {
        try {
            Object value = method.getReturnType().newInstance();
            return value;
        } catch (InstantiationException e) {
            e.printStackTrace();
            LOGGER.warn("不能进行实例化，必须包含无参数的构造方法", e);
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LOGGER.warn("IllegalAccessException", e);
            return null;
        }
    }

    /**
     * 尝试建立错误码的调用结果
     *
     * @param invoker
     * @param invocation
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    private Result toRpcResult(Invoker<?> invoker, Invocation invocation) throws NoSuchMethodException, IllegalAccessException {
        Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        if (!method.getReturnType().getClass().isInstance(CommonDes.class)) {
            LOGGER.warn("方法返回类型不符，不能进行错误码的设置{}", invoker.getInterface().getCanonicalName() + "." + method.getName());
            return null;
        }
        Object value = getNewInstance(method);
        return new RpcResult(value);
    }


    /**
     * 自动处理一些异常为错误码的形式
     *
     * @param srcResult  原始结果
     * @param invoker
     * @param invocation
     * @return 返回数据为新的结果，并不是传入的srcResult
     */
    private Result handleExp(final Result srcResult, Invoker<?> invoker, Invocation invocation) {
        if (!srcResult.hasException()) {
            //无异常直接返回结果
            return srcResult;
        }
        Throwable exception = srcResult.getException();
        RuntimeException runtimeException;
        // 如果是checked异常，不自动处理，不会发生
        if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
            return srcResult;
        } else {
            runtimeException = (RuntimeException) exception;
        }
        // 在方法签名上有声明throws，不自动处理
//        try {
//            Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
//            Class<?>[] exceptionClassses = method.getExceptionTypes();
//            for (Class<?> exceptionClass : exceptionClassses) {
//                if (exception.getClass().equals(exceptionClass)) {
//                    return srcResult;
//                }
//            }
//        } catch (NoSuchMethodException e) {
//            return srcResult;
//        }

        Result wrapperResult;
        try {
            wrapperResult = toRpcResult(invoker, invocation);
            if (wrapperResult == null) {
                LOGGER.warn("异常封装出错，将异常转为统一错误码出错");
                LOGGER.error("错误详细信息", runtimeException);
                return srcResult;
            } else {
                //其他情况需要重新处理,统一包装成错误码的形式
                CommonDes resp = (CommonDes) wrapperResult.getValue();
                resp.setError(CommonDes.getBaseResponse(runtimeException));
                LOGGER.error("调用出现异常，已自动转为错误码，返回结果{}异常堆栈>>>", wrapperResult.toString(), srcResult.getException());
                LOGGER.error("错误详细信息", runtimeException);
            }
        } catch (Exception e) {
            LOGGER.warn("调用目标接口出错,自动处理返回结果失败，客户端将收到异常", e);
            return srcResult;
        }
        return wrapperResult;
    }
}
