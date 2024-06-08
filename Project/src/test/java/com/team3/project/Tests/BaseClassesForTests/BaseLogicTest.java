package com.team3.project.Tests.BaseClassesForTests;

public class BaseLogicTest extends BaseTest{
    private static String logName = "log_LogicTest.txt",
            mainTestName = "Back-End";

    protected static void setup() {
        BaseTest.setup(logName, mainTestName);
    }
    protected static void before() {
        BaseTest.before();
    }

    protected static void after() {
        BaseTest.after(true);
    }
    protected static void tearDown() {
        BaseTest.tearDown();
    }

}
