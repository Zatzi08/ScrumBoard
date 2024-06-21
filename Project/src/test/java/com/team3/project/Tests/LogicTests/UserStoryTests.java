package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.UserStory;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.UserStoryService;
import org.junit.jupiter.api.*;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserStoryTests extends BaseLogicTest {
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
        /*  Test ID: Logic.T1
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstellen einer User-Story U3.B1
         */
    void createUserStory() throws Exception{
        //Arrange
        pw.append("Logik-Test-createUserStory\nTest ID: Logic.T1\nDate: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory userStory = new UserStory("null", "Blablah1", 2, -1);
        UserStory userStory_fail = null;
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        //Act
        //Exception-Cases sinnvoll?
        try {
            count_correct_exception_checks++;
            usService.saveUserStory(userStory_fail);
        } catch (Exception e){
            count_correct_exceptions ++;
        }

        try {
            count_correct_exception_checks++;
            userStory_fail = new UserStory(null, "Failure", 1, -1);
            usService.saveUserStory(userStory_fail);
        } catch (Exception e){
            count_correct_exceptions ++;
        }

        try {
            count_correct_exception_checks++;
            userStory_fail.setDescription(null);
            userStory_fail.setName("Fail");
            usService.saveUserStory(userStory_fail);
        } catch (Exception e){
            count_correct_exceptions ++;
        }

        //functional case
        usService.saveUserStory(userStory);
        userStory = usService.getUserStoryByName(userStory.getName());

        //Assert
        try {
            Assertions.assertEquals(userStory.getDescription(), usService.getUserStory(userStory.getID()).getDescription());
        } catch (AssertionError e){
            pw.append("Fail: created User-Story not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks){
            pass = false;
            pw.append("Fail: Wrong Exception-Handling\n");
        }
        pw.append(String.format("pass: %b", pass));
    }

    @Test
        /*  Test ID: Logic.T2
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: ändern einer User-Story U4.B1
         */
    void updateUserStory() throws Exception{
        //Arrange
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T2\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory userStory = new UserStory("UserStory1", "Blablah1", 1, -1);
        String newdescription = "Blah";

        //Act
        usService.saveUserStory(userStory);
        userStory = usService.getUserStoryByName(userStory.getName());
        userStory.setDescription(newdescription);
        usService.saveUserStory(userStory);

        //Assert
        try{
            Assertions.assertEquals(userStory.getDescription(), DAOUserStoryService.getById(userStory.getID()).getDescription());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: UserStory not updated\n");
            throw new AssertionError(e);
        }
        pw.append(String.format("pass: %b", pass));
    }

    @Test
        /*  Test ID: Logic.T5
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: User Story löschen U6.B1
         */

    void deleteUserStory() throws Exception{
        //Arrange
        pw.append("Logik-Test-deleteUserStory\nTest ID: Logic.T5\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory userStory = new UserStory("UserStory1", "Blablah1", 1, -1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        //Act
        //Exception case
        try{
            count_correct_exception_checks++;
            usService.deleteUserStoryAndLinkedTasks(-1);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //functional case
        usService.saveUserStory(userStory);
        userStory = usService.getUserStoryByName(userStory.getName());
        usService.deleteUserStoryAndLinkedTasks(userStory.getID());

        //Assert
        try{
            Assertions.assertFalse(DAOUserStoryService.checkId(userStory.getID()));
        }catch (AssertionError e){
            pw.append("Fail: deleted User Story found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: Wrong Exception-Handling\n");
            pass = false;
        }
        pw.append(String.format("pass = %b\n", pass));
    }
}
