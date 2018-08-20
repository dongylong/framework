package com.dongyl.validate.exception;

import com.dongyl.validate.error.ErrorCode;

/**
 * @author dongyl
 * @date 22:09 8/13/18
 * @project framework
 */
public class BaseException extends RuntimeException{
    protected ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message,Exception e, ErrorCode errorCode) {
        super(message,e);
        this.errorCode = errorCode;
    }

    public BaseException(Exception e, ErrorCode errorCode) {
        super(e);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage(){
        StringBuilder builder = new StringBuilder();
        String message = builder.append(super.getMessage() ==null?
                "":super.getMessage())
                .append(this.errorCode.getMessage()).toString();
        return message;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getErrorCodeValue(){
        return this.errorCode.getCode();
    }
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
