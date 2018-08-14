package com.dongyl.validate.bean;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.dongyl.log.TraceKeyHolder;
import com.dongyl.utils.NetWorkUtil;
import org.slf4j.MDC;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;

/**
 * @author dongyl
 * @date 08:37 8/14/18
 * @project framework
 */
public class MQRequest implements Serializable {
    String requestId;
    String projectName = "UNKNOWN";
    String from = "0.0.0.0";

    public MQRequest() {
        String traceKey = TraceKeyHolder.getTraceKey();
        if(StringUtils.isNotEmpty(traceKey)){
            requestId = traceKey;
        }
        String projectName = System.getProperty("projectName");
        if(StringUtils.isNotEmpty(projectName)){
            this.projectName = projectName;
        }
        List<String> localIPV4 = NetWorkUtil.getLocalIPV4();
        if(CollectionUtils.isNotEmpty(localIPV4)){
            from = localIPV4.get(0);
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
        initRequestId();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 创建对象后调用
     * @return
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException{
        initRequestId();
        return this;
    }

    private void initRequestId() {
        TraceKeyHolder.setTraceKey(requestId);
        MDC.put("reqId",requestId);
    }
}
