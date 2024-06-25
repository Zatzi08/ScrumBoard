package com.team3.project.Tests.LogicTests;

import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.AccountService;
import com.team3.project.service.WebSessionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTests extends BaseLogicTest {
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    @Mock
    private DAOUserService daoUserService;
    @Spy
    private WebSessionService webSessionService;
    @InjectMocks
    private AccountService accountService;

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
         *  Edit: Mockito hinzugefügt - Henry van Rooyen
         */
    void register_registerNewUser_createsNewUserObject() throws Exception{
        //Arrange
        pw.append("Logik-Test-register\nTest ID: Logic.T9\n" + "Date: ")
                .append(formatter.format(date))
                .append(String.valueOf('\n'));
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        final int userID;
        final String userName = "Dave";
        final String email = "dave@gmail.com";
        final String passwort = "123";

        //Exception cases
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            accountService.register(null, email, passwort);
        });
        count_correct_exceptions++;

        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            accountService.register(userName, null, passwort);
        });
        count_correct_exceptions++;

        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            accountService.register(userName, email, null);
        });
        count_correct_exceptions++;

        //Act
        accountService.register(userName, email, passwort);
        userID = daoUserService.getIdByMail(email);

        //Assert
        try{
            assertEquals(userName, daoUserService.getById(userID).getName());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: User not registered\n");
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks.get()) {
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
    void login_userLogInToSystem_changeViewToProjectmanager() throws Exception{
        //Arrange
        pw.append("Logik-Test-login\nTest ID: Logic.T10\n" + "Date: ")
                .append(formatter.format(date))
                .append(String.valueOf('\n'));
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        int count_correct_exceptions = 0;
        final String username = "Dave";
        final String email = "dave@net.de";
        final String passwort = "1234";

        //Exception cases
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            accountService.login(null, passwort);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class,()->{
            count_correct_exception_checks.getAndIncrement();
            accountService.login(email, null);
        });

        //Act
        accountService.register(username, email, passwort);

        //Assert
        try{
            assertTrue(accountService.login(email, passwort));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pass = false;
            pw.append("Fail: existent user could not login\n");
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks.get()){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
    }

    @Test
        /*  Test ID: Logic.T11
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: initiale reelle Rechte Zuweisung
         */
    void authority_UserDefaultAuthority_userAuthorityIs1() throws Exception{
        //Arrange
        pw.append("Logik-Test-authority\nTest ID: Logic.T11\n" + "Date: ")
                .append(formatter.format(date))
                .append(String.valueOf('\n'));
        String sessionID = null;
        final String username = "Dave";
        final String email = "dave@gmail.com";
        final String passwort = "1234";

        //Act
        accountService.register(username,email, passwort);
        sessionID = webSessionService.getSessionID("dave@gmail.com");

        //Assert
        try{
            assertEquals(1, accountService.getAuthority(sessionID));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pass = false;
            pw.append("Fail: wrong default Authority\n");
            throw new AssertionError(e);
        }
    }

    @Test
        /*  Test ID: Logic.T12
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: reelle Rechte ändern R1.B2
         */
    void setAuthority_changeUsersAuthority_setUsersAuthorityToCertainValue() throws Exception{
        //Arrange
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        String sessionID;
        int oldAuthority;
        int uID;
        final int newAuthority = 3;
        final String username = "Dave";
        final String email = "dave@test.com";
        final String passwort ="1234";
        final int invalidUID = -10;
        final int nonexistentUID = 1289342;
        final int failAuthority = -10;
        final int defaultAuthority = 1;
        int finalUID;

        //Act
        accountService.register(username, email, passwort);
        uID = DAOUserService.getIdByMail(email);
        finalUID = uID;
        sessionID = webSessionService.getSessionID(email);
        oldAuthority = accountService.getAuthority(sessionID);
        accountService.setAuthority(uID, newAuthority);

        //Exception cases
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            accountService.setAuthority(invalidUID, defaultAuthority);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            accountService.setAuthority(finalUID, failAuthority);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            accountService.setAuthority(nonexistentUID, defaultAuthority);
        });
        count_correct_exceptions++;

        //Assert
        try{
            assertNotEquals(oldAuthority, accountService.getAuthority(sessionID));
        }catch (Exception | AssertionError e){
            pass = false;
            pw.append("Fail: Authority was not changed\n");
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks.get()) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
    }
}