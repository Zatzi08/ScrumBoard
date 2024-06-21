package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Task;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.AccountService;
import com.team3.project.service.TaskService;
import com.team3.project.service.UserStoryService;
import org.junit.jupiter.api.*;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    @Test
        /*  Test ID: Logic.T19
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: Task-Erweiterung um zugeteilten Nutzern  T8
         */
        //TODO: löse Problem: Assert wird erfüllt, wenn beim debuggen man in die Methode DAOTaskService.getById(task.getID()) in Zeile 1450 hineingeht
    void TaskWithUsers() throws Exception{
        //Arrange
        pw.append("Logik-Test-TaskWithUsers\nTest ID: Logic.T19\n" + "Date: " + formatter.format(date) + '\n');
        AccountService accountService = new AccountService();
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT8", "T19T8", 2, -1);
        List<Integer> uIDs = new LinkedList<>();
        List<DAOUser> users;
        Task task = null;
        List <DAOUser> daoUsers;
        //Act
        accountService.register("DaveT8.1", "davet8.1@gmail.com", "T8");
        accountService.register("Dave1T8.1", "dave1t8.1@gmail.com", "T8");
        accountService.register("Dave2T8.1", "dave2t8.1@gmail.com", "T8");
        users = DAOUserService.getAllWithRoles();
        for(DAOUser daoUser: users){
            uIDs.add(daoUser.getId());
        }
        userStoryService.saveUserStory(userStory);
        userStory.setID(DAOUserStoryService.getByName(userStory.getName()).getId());
        task = new Task(-1,"TaskDescription",0,userStory.getID(), "2030-10-10 10:10", 20, 50,-1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        taskService.setUsers(task.getID(), uIDs);

        //Assert
        try{
            DAOTask daoTask = DAOTaskService.getWithUsersById(task.getID());
            if (daoTask == null) throw new AssertionError();
            daoUsers = daoTask.getUsers();
            Assertions.assertEquals(uIDs.get(0),daoUsers.get(0).getId());
            Assertions.assertEquals(uIDs.get(1),daoUsers.get(1).getId());
            Assertions.assertEquals(uIDs.get(2),daoUsers.get(2).getId());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: none or wrong user(s) connected to task\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T22
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstelle eine Task mit einer Priorität T7.B4
         */
    void createTaskWithPriority() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithPriority\nTest ID: Logic.T22\n" + "Date: " + formatter.format(date) + '\n');
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT7B4", "T7B4", 1, -1);
        Task task = null;

        //Act
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        task = new Task(-1, "TaskT7B4", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());

        //Assert
        try{
            Assertions.assertEquals(task.getPriorityAsInt(), DAOTaskService.getById(task.getID()).getPriority());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task with wrong priority\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T23
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: verändern der Priorität eine Task mit T7.B2
         */
    void editTaskWithPriority() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithPriority\nTest ID: Logic.T23\n" + "Date: " + formatter.format(date) + '\n');
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT7B2", "T7B2", 1, -1);
        Task task = null;

        //Act
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        task = new Task(-1, "TaskT7B2", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        task.setPriority(Enumerations.Priority.high);
        taskService.saveTask(task);

        //Assert
        try{
            Assertions.assertEquals(3, DAOTaskService.getById(task.getID()).getPriority());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-Priority was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T24
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstelle eine Task mit einer Schätzung T10.B3
         */
    void createTaskWithEstimate() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithEstimate\nTest ID: Logic.T24\n" + "Date: " + formatter.format(date) + '\n');
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT10B3", "T10B3", 1, -1);
        Task task = null;

        //Act
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        task = new Task(-1, "TaskT10B3", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());

        //Assert
        try{
            Assertions.assertEquals(task.getTimeNeededG(), DAOTaskService.getById(task.getID()).getProcessingTimeEstimatedInHours());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task with wrong estimate\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T25
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: verändern der Schätzung eine Task mit T10.B2
         */
    void editTaskWithEstimate() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithEstimate\nTest ID: Logic.T25\n" + "Date: " + formatter.format(date) + '\n');
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT10B2", "T10B2", 1, -1);
        Task task = null;

        //Act
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        task = new Task(-1, "TaskT9B2", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        task.setTimeNeededG(100);
        taskService.saveTask(task);

        //Assert
        try{
            Assertions.assertEquals(100, DAOTaskService.getById(task.getID()).getProcessingTimeEstimatedInHours());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-Estimate was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T26
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstelle eine Task mit einer Abgabefrist T9.B3
         */
    void createTaskWithDueDate() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithdueDate\nTest ID: Logic.T24\n" + "Date: " + formatter.format(date) + '\n');
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT9B3", "T9B3", 1, -1);
        Task task = null;

        //Act
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        task = new Task(-1, "TaskT9B3", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());

        //Assert
        try{
            Assertions.assertEquals(task.getDueDateAsString(), DAOTaskService.getById(task.getID()).getDueDate());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task with wrong due date\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T27
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: verändern der Abgabefrist einer Task mit T9.B2
         */
    void editTaskWithDueDate() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithDueDate\nTest ID: Logic.T25\n" + "Date: " + formatter.format(date) + '\n');
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT9B2", "T9B2", 1, -1);
        Task task = null;

        //Act
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        task = new Task(-1, "TaskT9B2", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dueDate = "2030-12-12T12:12";
        task.setDueDate(dformat.parse(dueDate.replace("T", " ")));
        taskService.saveTask(task);

        //Assert
        try{
            String dbDueDate = DAOTaskService.getById(task.getID()).getDueDate();
            Assertions.assertEquals(dueDate, DAOTaskService.getById(task.getID()).getDueDate());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-DueDate was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T28
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: verändern der Abgabefrist einer Task mit T11.B2
         */
    void editTaskWithRealTime() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithRealTime\nTest ID: Logic.T28\n" + "Date: " + formatter.format(date) + '\n');
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT9B2", "T9B2", 1, -1);
        Task task = null;

        //Act
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        task = new Task(-1, "TaskT9B2", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(taskService.getTaskByDescription(task.getDescription()).getID());
        taskService.setRealTimeAufwand(task.getID(), 10.0);

        //Assert
        try{
            Assertions.assertEquals(10.0, DAOTaskService.getById(task.getID()).getProcessingTimeRealInHours());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-Realtime was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

}
