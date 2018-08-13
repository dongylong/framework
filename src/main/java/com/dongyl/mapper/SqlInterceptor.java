package com.dongyl.mapper;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

/**
 * @author dongyl
 * @date 07:14 8/14/18
 * @project framework
 */
@Intercepts({
        @Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})
public class SqlInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Properties properties;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();
        Object returnValue;
        long start = System.currentTimeMillis();
        returnValue=invocation.proceed();
        long end = System.currentTimeMillis();
        long time = end-start;
        if(time >1){
            LOGGER.debug("+++++ SQL Time = " + Double.toString((double)(end - start) / 1000.0D) + "s == " + sqlId);
        }
        return returnValue;
    }

    /**
     *
     * @param o target
     * @return
     */
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    /**
     *
     * @param properties properties0
     */
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
