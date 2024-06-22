package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Account;
import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.User;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.AccountService;
import com.team3.project.service.WebSessionService;
import org.junit.jupiter.api.*;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserTests extends BaseLogicTest {
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;
    @BeforeAll
    public static void setupTest(){
        setup();
        pw = printWriter;
        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
    }

    @BeforeEach
    @AfterEach
    public void emptyDB(){
        BaseLogicTest.wipeDb(true);
    }
    @AfterAll
    public static void closeWriter(){
        pw.close();
        tearDown();
    }

    @Test
        /*  Test ID: Logic.T9
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Registration A2.B1
         */
    void register() throws Exception{
        //Arrange
        pw.append("Logik-Test-register\nTest ID: Logic.T9\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        int userID = -1;

        //Act
        //Exception cases
        try{
            count_correct_exception_checks++;
            aservice.register(null,"dave@gmail.com", "123");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.register("Dave",null, "123");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.register("Dave","dave@gmail.com", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //functional case
        aservice.register("Dave","dave@gmail.com", "123");
        userID = DAOUserService.getIdByMail("dave@gmail.com");

        //Assert
        try{
            Assertions.assertEquals("Dave", DAOUserService.getById(userID).getName());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: User was not registered\n");
            throw new AssertionError(e);
        }
        if(count_correct_exceptions != count_correct_exception_checks){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T10
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Login  A3.B1
         */
    void login() throws Exception{
        //Arrange
        pw.append("Logik-Test-login\nTest ID: Logic.T10\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        //Act
        //Exception cases
        try{
            count_correct_exception_checks++;
            aservice.login(null, "111");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.login("dave@net.de", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //functional case
        aservice.register("Dave", "dave@net.de", "111");

        //Assert
        try{
            Assertions.assertEquals(true, aservice.login("dave@net.de", "111"));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pass = false;
            pw.append("Fail: existent Email not found\n");
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b",pass));
    }

    @Test
        /*  Test ID: Logic.T11
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: initiale reelle Rechte Zuweisung
         */
    void authority() throws Exception{
        //Arrange
        pw.append("Logik-Test-authority\nTest ID: Logic.T11\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        String sessionID = null;

        //Act
        aservice.register("Dave","dave@gmail.com", "123");
        sessionID = wservice.getSessionID("dave@gmail.com");

        //Assert
        try{
            Assertions.assertEquals(1, aservice.getAuthority(sessionID));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: User created with wrong authority\n");
            pass = false;
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b",pass));
    }

    @Test
        /*  Test ID: Logic.T12
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: reelle Rechte ändern R1.B2
         */
    void changeAuthority() throws Exception{
        //Arrange
        pw.append("Logik-Test-changeAuthority\nTest ID: Logic.T12\n" + "Date: " + formatter.format(date) + '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        String sessionID = null;
        int oldAuthority = -10;
        int uID = -1;

        //Act
        //functional case
        aservice.register("Dave", "dave@test.com", "123");
        uID = DAOUserService.getIdByMail("dave@test.com");
        sessionID = wservice.getSessionID("dave@test.com");
        oldAuthority = aservice.getAuthority(sessionID);
        aservice.setAuthority(uID, 3);
        //Exception cases
        try{
            count_correct_exception_checks++;
            aservice.setAuthority(-10,1);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.setAuthority(uID,-10);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.setAuthority(1028823,-10);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //Assert
        try{
            Assertions.assertNotEquals(oldAuthority, aservice.getAuthority(sessionID));
        }catch (Exception | AssertionError e){
            pass = false;
            pw.append("Fail: Authority was not changed\n");
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b\n", pass));
    }
}
