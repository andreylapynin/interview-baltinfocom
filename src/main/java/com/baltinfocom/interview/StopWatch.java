package com.baltinfocom.interview;

import static java.lang.System.currentTimeMillis;

public class StopWatch {

    String commit;

    public static long start() {
        return currentTimeMillis();
    }

    public static long getElapsedTime(long start) {
        return currentTimeMillis() - start;
    }
}