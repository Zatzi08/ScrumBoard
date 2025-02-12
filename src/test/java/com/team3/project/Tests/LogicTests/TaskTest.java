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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
class TaskTest extends BaseLogicTest {

    @Spy
    private TaskService taskService;

    @Spy
    private UserStoryService userStoryService;

    @Spy
    private AccountService accountService;

    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;
    private UserStory userStory = new UserStory("StoryName", "BlahBlah", 1, -1);

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
        /*  Test ID: Logic.Task1
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: Task erstellen T3.B1
         */
    void createTask_createTaskObject_createTaskEntryInDatabase() throws Exception {
        // Arrange
        pw.append("Logik-Test-createTask\nTest ID: Logic.Task1\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT3B1", 0, userStoryID, "2030-10-10 10:10", 10, 50 , -1);

        // Act
        taskService.saveTask(task);
        task.setID(taskService.getTaskByDescription(task.getDescription()).getID());
        //Assert
        try{
            Assertions.assertEquals(userStoryID, taskService.getTaskByID(task.getID()).getUserStory().getID());
        }catch(AssertionError | Exception  e  ){
            pw.append("Fail: created Task not found\n");
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b\n", pass));
    }

    @Test
        /*  Test ID: Logic.Task2
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: Task editieren T4.B1
         */
    void editTask_updateValuesOfTaskObject_changeValuesOfTaskEntryInDatabase() throws Exception {
        // Arrange
        pw.append("Logik-Test-editTask\nTest ID: Logic.Task2\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        String newDescription = "newblaBla1";
        Task task_fail1 = null;
        Task task_fail2 = new Task(1023426785, "Failure", 1, userStory.getID(),"2024-05-20 10:10", 10, 20, -1);
        Task task_fail3 = new Task(1, null, 1, userStory.getID(),"2024-05-20 10:10", 10, 20, -1);
        Task task_fail4 = new Task(1, "Failure", 1, -1,"2024-05-20 10:10", 10, 20, -1);
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "Task1", 0, userStoryID, "2030-10-10 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(taskService.getTaskByDescription(task.getDescription()).getID());
        task.setDescription(newDescription);

        // Act
        taskService.saveTask(task);

        // Exception Cases
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            taskService.saveTask(task_fail1);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            taskService.saveTask(task_fail2);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            taskService.saveTask(task_fail3);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            taskService.saveTask(task_fail4);
        });
        count_correct_exceptions++;

        //Assert
        try{
            Assertions.assertEquals(newDescription, taskService.getTaskByID(task.getID()).getDescription());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task not edited\n");
            throw new AssertionError(e);
        }
        if (count_correct_exceptions != count_correct_exception_checks.get()){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b\n",pass));
    }

    @Test
        /*  Test ID: Logic.Task3
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: Task löschen T5.B1
         */
    void deleteTask_deleteTaskObject_deleteTaskEntryOutOfDatabase() throws Exception {
        // Arrange
        pw.append("Logik-Test-deleteTask\nTest ID: Logic.Task3\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        UserStory userStory = new UserStory("StoryName", "BlahBlah", 1, -1);
        userStoryService.saveUserStory(userStory);
        userStory = userStoryService.getUserStoryByName(userStory.getName());
        Task task = new Task(-1, "TaskDescription", 0, userStory.getID(), "2030-10-10 10:10", 20, 50, -1);
        taskService.saveTask(task);
        task = taskService.getTaskByDescription("TaskDescription");

        // Exception case
        assertThrows(Exception.class, ()-> taskService.deleteTask(-1));

        // Act
        taskService.deleteTask(task.getID());

        //Assert
        try{
            Assertions.assertNull(DAOTaskService.getById(task.getID()));
        }catch(AssertionError e){
            pw.append("Fail: deleted Task found\n");
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b\n", pass));
    }

    @Test
        /*  Test ID: Logic.Task4
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: Task-Erweiterung um zugeteilten Nutzern  T8
         */
    void taskWithUsers_linkUserObjectToTaskObject_createLinkBetweenUserObjectAndTaskObjectInDatabase() throws Exception {
        // Arrange
        pw.append("Logik-Test-taskWithUsers\nTest ID: Logic.Task4\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        List<Integer> uIDs = new LinkedList<>();
        List <DAOUser> daoUsers;
        accountService.register("DaveT8.1", "davet8.1@gmail.com", "T8");
        accountService.register("Dave1T8.1", "dave1t8.1@gmail.com", "T8");
        accountService.register("Dave2T8.1", "dave2t8.1@gmail.com", "T8");
        List<DAOUser> users = DAOUserService.getAllWithRoles();
        for(DAOUser daoUser: users){
            uIDs.add(daoUser.getId());
        }
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1,"TaskDescription",0,userStoryID, "2030-10-10 10:10", 20, 50,-1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        //Act
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
        /*  Test ID: Logic.Task5
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: erstelle eine Task mit einer Priorität T7.B4
         */
    void createTaskWithPriority_createTaskObjectWithAttributePriority_writeTaskEntryWithPriorityInDatabase() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithPriority\nTest ID: Logic.Task5\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT7B4", 1, userStoryID, "10-10-2030 10:10", 10, 20, -1);

        //Act
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());

        //Assert
        try{
            Assertions.assertEquals(task.getPriorityAsInt(), taskService.getTaskByID(task.getID()).getPriorityAsInt());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task with wrong priority\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.Task6
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: verändern der Priorität eine Task mit T7.B2
         */
    void editTaskWithPriority_changePriorityOfTaskObject_overwritePrirorityOfTaskEntryInDatabase() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithPriority\nTest ID: Logic.Task6\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        Enumerations.Priority newPriority = Enumerations.Priority.high;
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT7B2", 1, userStoryID, "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        task.setPriority(newPriority);

        //Act
        taskService.saveTask(task);

        //Assert
        try{
            Assertions.assertEquals(newPriority, taskService.getTaskByID(task.getID()).getPriority());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-Priority was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.Task7
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: erstelle eine Task mit einer Schätzung T10.B3
         */
    void createTaskWithEstimate_createTaskObjectWithEstimate_writeTaskEntryWithEstimateInDatabase() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithEstimate\nTest ID: Logic.Task7\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT10B3", 1, userStoryID, "10-10-2030 10:10", 10, 20, -1);

        //Act
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
        /*  Test ID: Logic.Task8
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: verändern der Schätzung eine Task mit T10.B2
         */
    void editTaskWithEstimate_changeEstimateOfTaskObject_overwriteEstimateOfTaskEntryInDataBase() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithEstimate\nTest ID: Logic.Task8\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        int newEstimate = 100;
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT9B2", 1, userStoryID, "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        task.setTimeNeededG(newEstimate);

        //Act
        taskService.saveTask(task);

        //Assert
        try{
            Assertions.assertEquals(newEstimate, DAOTaskService.getById(task.getID()).getProcessingTimeEstimatedInHours());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-Estimate was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.Task9
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: erstelle eine Task mit einer Abgabefrist T9.B3
         */
    void createTaskWithDueDate_createTaskObjectWithDueDate_writeTaskEntryWithDueDateInDatabase() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithdueDate\nTest ID: Logic.Task9\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT9B3", 1, userStoryID, "10-10-2030 10:10", 10, 20, -1);

        //Act
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
        /*  Test ID: Logic.Task10
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: verändern der Abgabefrist einer Task mit T9.B2
         */
    void editTaskWithDueDate_changeDuDateOfTaskObject_overwriteDueDateOfTaskEntryInDatabase() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithDueDate\nTest ID: Logic.Task10\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT9B2", 1, userStoryID, "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(taskService.getTaskByDescription(task.getDescription()).getID());
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String newDueDate = "2030-12-12T12:12";
        task.setDueDate(dformat.parse(newDueDate.replace("T", " ")));

        //Act
        taskService.saveTask(task);

        //Assert
        try{
            Assertions.assertEquals(newDueDate, DAOTaskService.getById(task.getID()).getDueDate());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-DueDate was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.Task11
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: verändern der Abgabefrist einer Task mit T11.B2
         */
    void editTaskWithRealTime_changeRealTimeOfTaskObject_overwriteRealTimeOfTaskEntryInDatabase() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskWithRealTime\nTest ID: Logic.Task11\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        double newTime = 10.0;
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        Task task = new Task(-1, "TaskT9B2", 1, userStoryID, "10-10-2030 10:10", 10, 20, -1);
        taskService.saveTask(task);
        task.setID(taskService.getTaskByDescription(task.getDescription()).getID());
        taskService.setRealTimeAufwand(task.getID(), newTime);

        //Act
        taskService.saveTask(task);
        //Assert
        try{
            Assertions.assertEquals(newTime, DAOTaskService.getById(task.getID()).getProcessingTimeRealInHours());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task-Realtime was not changed\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }

}