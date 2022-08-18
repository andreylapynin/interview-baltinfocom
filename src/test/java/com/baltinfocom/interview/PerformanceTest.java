package com.baltinfocom.interview;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.baltinfocom.interview.StopWatch.getElapsedTime;


public class PerformanceTest {

    @Test
    public void groupManagerPerformanceTest() throws IOException {
        long start = StopWatch.start();
        GroupManager groupManager = new GroupManager();
        groupManager.printResult(groupManager.lineCombineIntoGroup("lng.txt"));
        System.out.println("PerformanceTest.groupManagerPerformanceTest: " + getElapsedTime(start) + " ms");
    }

}