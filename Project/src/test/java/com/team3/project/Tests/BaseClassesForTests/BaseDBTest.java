package com.team3.project.Tests.BaseClassesForTests;

public class BaseDBTest extends BaseTest {
    private static String logName = "log_DatabaseTest.txt",
                     mainTestName = "Datenbank",
                     testIDPrefix = "DB";

    protected static void setup() {
        BaseTest.setup(logName, mainTestName);
    }
    protected static void before() {
        BaseTest.before();
    }

    static void after() {
        BaseTest.after();
    }
    protected static void tearDown() {
        BaseTest.tearDown();
    }

    protected void printWriterAddTest(String testName, String testId) {
        super.printWriterAddTest(mainTestName, testIDPrefix, testName, testId);
    }

    protected void printWriterAddFailure(String failureMessage) {
        super.printWriterAddFailure(failureMessage);
    }

    protected void printWriterAddPass() {
        super.printWriterAddPass();
    }
}
