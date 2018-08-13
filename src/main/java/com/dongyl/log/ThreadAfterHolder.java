package com.dongyl.log;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author dongyl
 * @date 22:23 8/13/18
 * @project framework
 */
public class ThreadAfterHolder {
    private static ScheduledExecutorService scheduledExecutorService;

    private static ThreadLocal<List<Thread>> threadLocal = new ThreadLocal<>();

    public static void executeAfter() {
        synchronized (Thread.currentThread()){
            List<Thread> list = threadLocal.get();
            threadLocal.remove();
            if(!CollectionUtils.isEmpty(list)){
                list.stream().forEach(thread -> scheduledExecutorService.execute(thread));
            }
        }
    }
}
