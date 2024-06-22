package com.team3.project.Tests.LogicTests;

import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.AccountService;
import com.team3.project.service.WebSessionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @Mock
    private WebSessionService webSessionServiceMock;
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
    void register_registerNewUser_CreatesNewUserObject() throws Exception{
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
        assertEquals(userName, daoUserService.getById(userID).getName());

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
    void login() throws Exception{
        //Arrange
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
            assertTrue(aservice.login("dave@net.de", "111"));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pass = false;
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pass = false;
        }
    }

    @Test
        /*  Test ID: Logic.T11
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: initiale reelle Rechte Zuweisung
         */
    void authority() throws Exception{
        //Arrange
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        String sessionID = null;

        //Act
        aservice.register("Dave","dave@gmail.com", "123");
        sessionID = wservice.getSessionID("dave@gmail.com");

        //Assert
        try{
            assertEquals(1, aservice.getAuthority(sessionID));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pass = false;
            throw new AssertionError(e);
        }
    }

    @Test
        /*  Test ID: Logic.T12
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: reelle Rechte ändern R1.B2
         */
    void changeAuthority() throws Exception{
        //Arrange
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
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks) {
            pass = false;
        }
    }
}