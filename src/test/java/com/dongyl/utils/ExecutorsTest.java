package com.dongyl.utils;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author dongyl
 * @date 17:56 11/14/18
 * @project framework
 */
public class ExecutorsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    @Ignore
    public void findPublicGroupByConcurrent() throws Exception {
        int tCount = 100;
        final ExecutorService executor = Executors.newFixedThreadPool(tCount);
        for (int i = 0; i < tCount; i++) {
            final String requestId  = "Thread-" + i + "-" + System.currentTimeMillis();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            long l = System.currentTimeMillis();
                            //method
                            long o1 = System.currentTimeMillis() - l;
                            if (o1 > 2000) {
                                LOGGER.info("[{}]cost:{}", o1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            executor.execute(t);
        }
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
