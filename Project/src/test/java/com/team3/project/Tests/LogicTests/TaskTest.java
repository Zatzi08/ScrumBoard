package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Task;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.TaskService;
import com.team3.project.service.UserStoryService;
import org.junit.jupiter.api.*;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTest extends BaseLogicTest {
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
        /*  Test ID: Logic.T6
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Task erstellen T3.B1
         */
    void createTask() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTask\nTest ID: Logic.T6\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService uservice = new UserStoryService();
        TaskService tservice = new TaskService();
        UserStory userStory = new UserStory("Story1", "Blah1", 1, -1);
        Task task = null;

        //Act
        uservice.saveUserStory(userStory);
        userStory = uservice.getUserStoryByName(userStory.getName());
        task = new Task(-1, "TaskT3B1", 0, userStory.getID(), "2030-10-10 10:10", 10, 50 , -1);
        tservice.saveTask(task);
        task = tservice.getTaskByDescription(task.getDescription());

        //Assert
        try{
            Assertions.assertEquals(task.getPriority(), tservice.getTaskByID(task.getID()).getPriority());
        }catch(AssertionError | Exception  e  ){
            pw.append("Fail: created Task not found\n");
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b\n", pass));
    }

    @Test
        /*  Test ID: Logic.T7
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Task editieren T4.B1
         */
    void editTask() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTask\nTest ID: Logic.T7\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService uservice = new UserStoryService();
        TaskService tservice = new TaskService();
        UserStory userStory = new UserStory("UStory1", "blaBla1", 1, -1);
        String newdescription = "newblaBla1";
        Task task = new Task(-1,"Task1", 0, -1,"2030-10-10 10:10", 10, 20,-1);
        Task task_fail = null;
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        //Act
        //functional case
        uservice.saveUserStory(userStory);
        userStory = uservice.getUserStoryByName(userStory.getName());
        task.setUserStoryID(userStory.getID());
        tservice.saveTask(task);
        task = tservice.getTaskByDescription(task.getDescription());
        task.setDescription(newdescription);
        tservice.saveTask(task);

        //Exception case
        try{
            count_correct_exception_checks++;
            tservice.saveTask(task_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        task_fail = new Task(1023426785, "Failure", 1, userStory.getID(),"2024-05-20 10:10", 10, 20, -1);

        try{
            count_correct_exception_checks++;
            tservice.saveTask(task_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            task_fail.setDescription(null);
            tservice.saveTask(task_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            task_fail.setDescription("Task-Failure");
            task_fail.setUserStoryID(-10);
            tservice.saveTask(task_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            task_fail.setUserStoryID(userStory.getID());
            task_fail.setTimeNeededA(-1);
            tservice.saveTask(task_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            task_fail.setTimeNeededA(10);
            task_fail.setTimeNeededG(-1);
            tservice.saveTask(task_fail);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //Assert
        try{
            DAOTask daoTask = DAOTaskService.getById(task.getID());
            Assertions.assertEquals(task.getDescription(),daoTask.getDescription());
        }catch(AssertionError e){
            pw.append("Fail: updated Task-description not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: wrong Exception-Handling\n");
            pass = false;
        }
        pw.append(String.format("pass = %b\n",pass));
    }

    @Test
        /*  Test ID: Logic.T8
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Task löschen T5.B1
         */
    void deleteTask() throws Exception{
        //Arrange
        pw.append("Logik-Test-deleteTask\nTest ID: Logic.T8\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService uservice = new UserStoryService();
        TaskService tservice = new TaskService();
        UserStory userStory = new UserStory("StoryName", "BlahBlah", 1, -1);
        Task task = null;
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        //Act
        //Exception case
        try{
            count_correct_exception_checks++;
            tservice.deleteTask(-1);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //functional case
        uservice.saveUserStory(userStory);
        userStory = uservice.getUserStoryByName(userStory.getName());
        task = new Task(-1,"TaskDescription",0,userStory.getID(), "2030-10-10 10:10", 20, 50,-1);
        tservice.saveTask(task);
        task = tservice.getTaskByDescription(task.getDescription());
        tservice.deleteTask(task.getID());

        //Assert
        try{
            Assertions.assertNull(DAOTaskService.getById(task.getID()));
        }catch(AssertionError e){
            pw.append("Fail: deleted Task found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: wrong Exception-Handling\n");
            pass = false;
        }
        pw.append(String.format("pass = %b\n", pass));
    }
}
