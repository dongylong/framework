package com.dongyl.validate.exception;

import com.dongyl.validate.error.BaseErrorCode;
import com.dongyl.validate.error.ErrorCode;

/**
 * @author dongyl
 * @date 22:15 8/13/18
 * @project framework
 */
public class UnknownException extends BaseException {

    private static final ErrorCode errorCode = BaseErrorCode.errorCodeFor(-1);

    public UnknownException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnknownException(Exception e) {
        super(e,errorCode);
    }

    public UnknownException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UnknownException(String message, Exception e, ErrorCode errorCode) {
        super(message, e, errorCode);
    }

    public UnknownException(Exception e, ErrorCode errorCode) {
        super(e, errorCode);
    }
}
