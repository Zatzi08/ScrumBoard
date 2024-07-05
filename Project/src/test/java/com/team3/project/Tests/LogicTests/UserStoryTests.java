package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.UserStoryService;
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
public class UserStoryTests extends BaseLogicTest {
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;
    private UserStory userStory = new UserStory("UserStory", "UserStoryTests", 1, -1);

    @Spy
    private UserStoryService userStoryService;
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
        /*  Test ID: Logic.UserStory1
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstellen einer User-Story U3.B1
         */
    void saveUserStory_addNewUserStory_createNewUserStoryObjectInDB() throws Exception{
        //Arrange
        pw.append("Logik-Test-addNewUserStory\nTest ID: Logic.UserStory1\nDate: " + formatter.format(date)+ '\n');
        UserStory failUserStory1 = null;
        UserStory failUserStory2 = new UserStory(null, "Failure", 1, -1);
        UserStory failUserStory3 = new UserStory("null", null, 1, -1);
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        int uID;

        //Exception-Cases
        assertThrows(Exception.class, ()->{
            count_correct_exception_checks.getAndIncrement();
            userStoryService.saveUserStory(failUserStory1);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, ()->{
            count_correct_exception_checks.getAndIncrement();
            userStoryService.saveUserStory(failUserStory2);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, ()->{
            count_correct_exception_checks.getAndIncrement();
            userStoryService.saveUserStory(failUserStory3);
        });
        count_correct_exceptions++;

        //Act
        userStoryService.saveUserStory(userStory);
        uID = userStoryService.getUserStoryByName(userStory.getName()).getID();

        //Assert
        try {
            assertEquals(userStory.getDescription(), userStoryService.getUserStory(uID).getDescription());
        } catch (AssertionError e){
            pw.append("Fail: created User-Story not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks.get()){
            pass = false;
            pw.append("Fail: Wrong Exception-Handling\n");
        }
        pw.append(String.format("pass: %b", pass));
    }

    @Test
        /*  Test ID: Logic.UserStory2
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: ändern einer User-Story U4.B1
         */
    void saveUserStory_updateUserStory_updateChangedValuesOfUserStoryInDB() throws Exception{
        //Arrange
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.UserStory2\n" + "Date: " + formatter.format(date)+ '\n');
        final String newdescription = "Blah";
        int uID;
        final int oldUID = userStory.getID();
        String oldDescription = userStory.getDescription();

        //Act
        userStoryService.saveUserStory(userStory);
        uID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        userStory.setID(uID);
        userStory.setDescription(newdescription);
        userStoryService.saveUserStory(userStory);
        userStory.setDescription(oldDescription);
        userStory.setID(oldUID);

        //Assert
        try{
            assertEquals(newdescription, userStoryService.getUserStory(uID).getDescription());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: UserStory not updated\n");
            throw new AssertionError(e);
        }
        pw.append(String.format("pass: %b", pass));
    }

    @Test
        /*  Test ID: Logic.UserStory3
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: User Story löschen U6.B1
         */

    void deleteUserStory_deleteUserStory_deleteUserStoryObjectFromDB() throws Exception{
        //Arrange
        pw.append("Logik-Test-deleteUserStory\nTest ID: Logic.UserStory3\n" + "Date: " + formatter.format(date)+ '\n');
        int uID;
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        final int invalidUID = -10;
        final int nonexistentUID = 19204;

        //Exception case
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            userStoryService.deleteUserStoryAndLinkedTasks(invalidUID);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            userStoryService.deleteUserStoryAndLinkedTasks(nonexistentUID);
        });
        count_correct_exceptions++;

        //Act
        userStoryService.saveUserStory(userStory);
        uID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        userStoryService.deleteUserStoryAndLinkedTasks(uID);

        //Assert
        try{
            assertNull(DAOUserStoryService.getById(uID));
        }catch (AssertionError e){
            pw.append("Fail: deleted User Story found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks.get()){
            pw.append("Fail: Wrong Exception-Handling\n");
            pass = false;
        }
        pw.append(String.format("pass = %b\n", pass));
    }
}
