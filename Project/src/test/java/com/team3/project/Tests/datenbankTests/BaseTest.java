package com.team3.project.Tests.datenbankTests;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.team3.project.DAOService.DAOStartService;

public class BaseTest {
    protected static PrintWriter printWriter;
    protected static Date date;
    protected static SimpleDateFormat formatter;
    protected boolean pass = true;

    protected static void setup() {
        try {
            File logFile = new File("src/test/java/com/team3/project/logs/log_DatabaseTest.txt");
            logFile.setWritable(true);
            logFile.setReadable(true);
            FileWriter fileWriter = new FileWriter(logFile, true);
            printWriter = new PrintWriter(fileWriter, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();

        DAOStartService.start();
    }

    protected static void before() {

    }

    protected static void after() {

    }

    protected static void tearDown() {
        printWriter.close();
    }

    protected void printWriterAddTest(String testName, String testId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Datenbank-Test-");
        stringBuilder.append(testName);
        stringBuilder.append("\nTest ID: DB.");
        stringBuilder.append(testId);
        stringBuilder.append("\nDate: ");
        stringBuilder.append(formatter.format(date));
        stringBuilder.append('\n');
        printWriter.append(stringBuilder.toString());
    }

    protected void printWriterAddFailure(String failureMessage) {
        printWriter.append(String.format("fail: " + failureMessage + '\n'));
        pass = false;
    }

    protected void printWriterAddPass() {
        printWriter.append(String.format("pass: %b", pass));
    }
}
