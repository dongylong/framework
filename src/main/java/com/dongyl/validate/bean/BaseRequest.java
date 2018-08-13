package com.dongyl.validate.bean;

import com.dongyl.utils.json.JsonUtil;

import java.io.Serializable;

/**
 * @author dongyl
 * @date 20:55 8/13/18
 * @project framework
 */
public class BaseRequest implements Serializable {
    private String reqId;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    @Override
    public String toString() {
        return JsonUtil.objToJson(this);
    }
}
