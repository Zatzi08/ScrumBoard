package com.team3.project.Tests.BaseClassesForTests;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.team3.project.DAOService.DAOStartService;

public abstract class BaseTest {
    protected static PrintWriter printWriter;
    protected static Date date;
    protected static SimpleDateFormat formatter;
    protected boolean pass = true;

    static void setup(String logName, String mainTestName) {
        changeHibernateCfg(true);
        try {
            File logFile = new File(String.format("src/test/java/com/team3/project/logs/%s", logName));
            logFile.setWritable(true);
            logFile.setReadable(true);
            FileWriter fileWriter = new FileWriter(logFile, true);
            printWriter = new PrintWriter(fileWriter, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
        printWriter.append(String.format("%s-Tests:\n\n", mainTestName));
        DAOStartService.start();
    }

    static void before() {

    }

    static void after() {

    }

    static void tearDown() {
        printWriter.close();
        changeHibernateCfg(false);
    }

    void printWriterAddTest(String mainTestName, String testIDPrefix, String testName, String testId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mainTestName);
        stringBuilder.append("-Test-");
        stringBuilder.append(testName);
        stringBuilder.append("\nTest ID: ");
        stringBuilder.append(testIDPrefix);
        stringBuilder.append(".");
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
        printWriter.append("\n\n\n");
    }

    public static void changeHibernateCfg(boolean isTesting) {
        Path path = Paths.get("src/main/resources/DB/hibernate.cfg.xml");
        Charset charset = StandardCharsets.UTF_8;
        try {
            String content = new String(Files.readAllBytes(path), charset);
            String database = "database.db", testDatabase = "databaseTest.db",
                   getter = testDatabase, setter = database;
            if (isTesting) {
                getter = database;
                setter = testDatabase;
            }
            content = content.replaceAll(getter, setter);
            Files.write(path, content.getBytes(charset));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
