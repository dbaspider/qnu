package com.ltt.wp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtils {

    private static Logger log = LoggerFactory.getLogger(ThreadUtils.class);

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            // do nothing
            log.error("sleep error", e);
        }
    }

}
