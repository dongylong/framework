package com.dongyl.validate.bean;

import com.dongyl.utils.json.JsonUtil;
import com.dongyl.validate.error.BaseErrorCode;
import com.dongyl.validate.exception.BaseException;
import com.dongyl.validate.exception.UnknownException;

import java.io.Serializable;

/**
 * @author dongyl
 * @date 22:06 8/13/18
 * @project framework
 */
public class CommonDes implements Serializable {
    private int code;
    private String message = "success";

    public boolean success(){
        return code ==0;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    /**
     *
     */
    public void setError(CommonDes failure){
        code = failure.getCode()!=0?failure.getCode():-1;
        if(code!=-1){
            message = failure.getMessage();
        }else {
            message = BaseErrorCode.errorCodeFor(code).getComment();
        }
    }

    @Override
    public String toString() {
        return JsonUtil.objToJson(this);
    }

    public static CommonDes getBaseResponse(RuntimeException exception){
        BaseException baseException;
        if(exception instanceof BaseException){
            baseException = (BaseException) exception;
        }else {
            baseException = new UnknownException(exception);
        }
        CommonDes exceptionDes = new CommonDes();
        exceptionDes.setCode(baseException.getErrorCodeValue());
        exceptionDes.setMessage(baseException.getMessage());
        return exceptionDes;
    }
    public static CommonDes makeErrorResult(){
        return new CommonDes();
    }
}
