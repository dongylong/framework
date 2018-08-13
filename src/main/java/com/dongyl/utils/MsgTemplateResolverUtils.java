package com.dongyl.utils;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongyl
 * @date 21:36 8/13/18
 * @project framework
 */
public class MsgTemplateResolverUtils {
    private static final String MESSAGE_KEY = "message";
    private static final String PAYLOAD_KEY = "payload";
    private static final String GROUPS_KEY = "groups";
    private static final String VALUE_KEY = "value";
    private static final String FLAGS_KEY = "flags";

    public static String getConditionVal(Map<String, Object> attrs) {
        Object valueKey = attrs.get(VALUE_KEY);
        if (valueKey!=null) {
            return attrs.get(valueKey).toString();
        }
        Map<String,Object> map = new HashMap<>(attrs);
        map.remove(MESSAGE_KEY);
        map.remove(PAYLOAD_KEY);
        map.remove(GROUPS_KEY);
        map.remove(FLAGS_KEY);
        if(map.isEmpty()){
            return "";
        }
        return map.toString();
    }
    public static String getMsgFromViolation(ConstraintViolation<?> violation){
        String interceptedMsg = Constants.DEFAULT_MSG_MAP.get(violation.getMessage());
        boolean fullMsg = false;
        if(interceptedMsg ==null){
            interceptedMsg = violation.getMessage();
            fullMsg = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(violation.getRootBeanClass().getCanonicalName()).append("字段")
                .append(violation.getPropertyPath().toString());
        sb.append(interceptedMsg);
        if(!fullMsg){
            sb.append(getConditionVal(violation.getConstraintDescriptor().getAttributes()));
        }
        sb.append(", 实际值为：");
        sb.append(violation.getInvalidValue());
        return sb.toString();
    }
}
