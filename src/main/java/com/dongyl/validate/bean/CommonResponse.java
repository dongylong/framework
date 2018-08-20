package com.dongyl.validate.bean;

import com.dongyl.validate.error.ErrorCode;

/**
 * @author dongyl
 * @date 21:32 8/14/18
 * @project framework
 */
public class CommonResponse<T> extends CommonDes {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data){
        CommonResponse response = new CommonResponse();
        response.setData(data);
        return response;
    }

    public static <T> CommonResponse<T> failure(ErrorCode errorCode){
        CommonResponse response = new CommonResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return response;
    }

}
