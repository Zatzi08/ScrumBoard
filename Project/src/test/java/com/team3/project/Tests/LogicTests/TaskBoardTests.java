package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Task;
import com.team3.project.Classes.TaskBoard;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOTaskListService;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.TaskBoardService;
import com.team3.project.service.TaskService;
import com.team3.project.service.UserStoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TaskBoardTests extends BaseLogicTest {
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;
    private UserStory userStory = new UserStory("UserStoryT16B2", "T16B2", 1, -1);

    @Spy
    private TaskBoardService taskBoardService = new TaskBoardService();
    @Spy
    private TaskService taskService = new TaskService();
    @Spy
    private UserStoryService userStoryService = new UserStoryService();
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
    /*  Test ID: Logic.TaskBoard1
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: erstellen eines TaskBoards
     */
    void createTaskBoard_createTaskBoardObject_createEntryOfTaskBoardObjectInDataBase() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskBoard\nTest ID: Logic.TaskBoard1\n" + "Date: " + formatter.format(date) + '\n');
        String taskBoardName = "TaskBoardT16B3";
        final String taskBoardName_fail1 = null;
        final String taskBoardName_fail2 = "";
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();

        //Exception cases
        assertThrows(Exception.class, ()->{
            count_correct_exception_checks.getAndIncrement();
            taskBoardService.createTaskBoard(taskBoardName_fail1);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, ()->{
            count_correct_exception_checks.getAndIncrement();
            taskBoardService.createTaskBoard(taskBoardName_fail2);
        });
        count_correct_exceptions++;

        //Act
        taskBoardService.createTaskBoard(taskBoardName);

        //Assert
        try{
            Assertions.assertEquals(taskBoardName, taskBoardService.getAllTaskBoards().get(0).getName());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: taskBoard not created\n");
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks.get()){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b", pass));

    }
    @Test
        /*  Test ID: Logic.TaskBoard2
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstellen einer Task erweitern um TaskBoardID + Verknüpfung an TaskList T16.B3
         */
    void createTaskWithTaskBoardID_createTaskObjectLinkedToTaskBoard_createTaskEntryInDataBaseAndLinkToTaskBoard() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithTaskBoardID\nTest ID: Logic.TaskBoard2\n" + "Date: " + formatter.format(date) + '\n');
        List<DAOTaskList> taskLists;
        DAOTaskList daoTaskList = null;
        String taskBoardName = "TaskBoardT16B3";
        userStoryService.saveUserStory(userStory);
        int userStoryID = userStoryService.getUserStoryByName(userStory.getName()).getID();
        taskBoardService.createTaskBoard(taskBoardName);
        TaskBoard taskBoard = taskBoardService.getAllTaskBoards().get(0);
        Task task = new Task(-1, "TaskT16B2", 1, userStoryID, "10-10-2030 10:10", 10, 20, taskBoard.getID());
        taskLists = DAOTaskListService.getByTaskBoardId(taskBoard.getID());
        for(DAOTaskList taskListObject : taskLists){
            if(taskListObject.getSequence() == 1){
                daoTaskList = taskListObject;
                break;
            }
        }
        //Act
        taskService.saveTask(task);

        //Assert
        DAOTask daoTask = DAOTaskService.getByDescription(task.getDescription());
        try{
            Assertions.assertEquals(daoTaskList.getId(), daoTask.getTaskList().getId());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task not linked to TaskBoard\n");
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.TaskBoard3
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: bearbeiten einer Task erweitern um TaskBoardID T16.B2
         */
    void editTaskBoardIDInTask_changeLinkedTaskBoardOfUserObject_changeLinkToTaskBardOfUserObjectInDatabase() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskBoardIDInTask\nTest ID: Logic.TaskBoard3\n" + "Date: " + formatter.format(date) + '\n');
        Task task = new Task(-1, "TaskT16B2", 1, -1, "10-10-2030 10:10", 10, 20, -1);
        DAOTaskList taskList = null;
        List<DAOTaskList> taskLists;
        DAOTask daoTask;
        String taskBoardName1 = "TaskBoard1T16B2";
        String taskBoardName2 = "TaskBoard2T16B2";
        int tb1ID;
        int tb2ID;

        //Act

        //create Task with link to UserStory and TaskBoard1
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        taskBoardService.createTaskBoard(taskBoardName1);
        tb1ID = DAOTaskBoardService.getAll().get(0).getId();
        task.setUserStoryID(userStory.getID());
        task.setTbID(tb1ID);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());

        //create TaskBoard2 and link Task to it
        taskBoardService.createTaskBoard(taskBoardName2);
        tb2ID = DAOTaskBoardService.getAll().get(1).getId();
        task.setTbID(tb2ID);
        taskService.saveTask(task);
        //get TaskList where Task is in
        taskLists = DAOTaskListService.getByTaskBoardId(tb2ID);
        for(DAOTaskList taskListObject : taskLists){
            if(taskListObject.getSequence() == 1){
                taskList = taskListObject;
                break;
            }
        }
        daoTask = DAOTaskService.getById(task.getID());

        //Assert
        try{
            Assertions.assertEquals(daoTask.getTaskList().getId(), taskList.getId());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task did not switch TaskBoards\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
    }
}
