package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Profile;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.AccountService;
import com.team3.project.service.WebSessionService;
import org.junit.jupiter.api.*;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProfileTests extends BaseLogicTest {
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
        /*  Test ID: Logic.T3
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Profile erstellen P1.B1
         */
    void createProfile() throws Exception{
        //Arrange
        pw.append("Logik-Test-createProfile\nTest ID: Logic.T3\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        String sessionID = "";
        String sessionID_fail = "";
        Profile profile = new Profile(0,"Dave", "dave@gmail.com", "Manager","slow thinker" ,null, 1);
        Profile profile_fail = new Profile(0,"Exception", "fail@gmail.com", "Manager","slow thinker" ,null, 1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        //Act
        //Exception-cases
        aservice.register("Exception","fail@gmail.com", "123");
        sessionID_fail = wservice.getSessionID("fail@gmail.com");
        try{
            count_correct_exception_checks++;
            aservice.getProfileByID(null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.getProfileByID("-1");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.savePublicData(null, profile_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.savePublicData("Fake-sessionID", profile_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            profile_fail.setUname(null);
            aservice.savePublicData(sessionID_fail, profile_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            profile_fail.setUname("Dave");
            profile_fail.setWorkDesc(null);
            aservice.savePublicData(sessionID_fail, profile_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            profile_fail.setWorkDesc("working");
            profile_fail.setPrivatDesc(null);
            aservice.savePublicData(sessionID_fail, profile_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //functional case
        aservice.register("Dave","dave@gmail.com", "123");
        sessionID = wservice.getSessionID("dave@gmail.com");
        aservice.savePublicData(sessionID, profile);

        //Assert
        try{
            Assertions.assertNotNull(aservice.getProfileByID(sessionID));
        }catch(AssertionError | Exception e){
            pw.append("Fail: existent User-Profile not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if(count_correct_exception_checks != count_correct_exceptions){
            pass = false;
            pw.append("Fail: Wrong Exception-Handling\n");
        }
        pw.append(String.format("pass: %b", pass));
    }

    @Test
        /*  Test ID: Logic.T4
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Profile aktualisieren P2.B1
         */
    void updateProfile() throws Exception{
        //Arrange
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T4\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        Profile profile = new Profile(0,"Dave", "dave@gmx.de","netter Dave", "arbeitender Dave", null, 1);
        String newdescription = "böse";
        String sessionID = null;

        //Act
        aservice.register("Dave", "dave@gmx.de", "passw");
        sessionID = wservice.getSessionID("dave@gmx.de");
        aservice.savePublicData(sessionID,profile);
        profile.setPrivatDesc(newdescription);
        aservice.savePublicData(sessionID,profile);

        //Assert
        try{
            Assertions.assertEquals(aservice.getProfileByEmail("dave@gmx.de").getPrivatDesc(), newdescription);
        }catch (AssertionError | Exception e){
            pw.append("Fail: User-Profile was not updated\n");
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b",pass));
    }
}
