package com.dongyl.log;

/**
 * @author dongyl
 * @date 21:02 8/13/18
 * @project framework
 */
public class TraceKeyHolder {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getTraceKey() {
        return threadLocal.get();
    }

    public static void setTraceKey(String traceKey) {
        threadLocal.set(traceKey);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
