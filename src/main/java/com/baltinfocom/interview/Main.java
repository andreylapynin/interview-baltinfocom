package com.baltinfocom.interview;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        GroupManager groupManager = new GroupManager();
        groupManager.printResult(groupManager.lineCombineIntoGroup(args[0]));
    }

}