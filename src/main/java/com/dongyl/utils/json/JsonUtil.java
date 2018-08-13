package com.dongyl.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author dongyl
 * @date 20:57 8/13/18
 * @project framework
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    public static String objToJson(Object obj) {
        if(null!=obj){
            try {
                return mapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] objToByte(Object obj) {
        if(obj!= null){
            try {
                return mapper.writeValueAsBytes(obj);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
