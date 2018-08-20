package com.dongyl.validate.exception;

import com.dongyl.validate.error.ErrorCode;

/**
 * @author dongyl
 * @date 21:43 8/15/18
 * @project framework
 */
public class BizLayerException extends BaseException {

    public BizLayerException(String msg, ErrorCode errorCode) {
        super(msg, errorCode);
    }

    public BizLayerException(Exception e, ErrorCode errorCode) {
        super(e, errorCode);
    }

    public BizLayerException(String msg, Exception e, ErrorCode errorCode) {
        super(msg, e, errorCode);
    }

    public BizLayerException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

}
