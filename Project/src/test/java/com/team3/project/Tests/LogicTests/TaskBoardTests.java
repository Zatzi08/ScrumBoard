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

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskBoardTests extends BaseLogicTest {
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
        /*  Test ID: Logic.T20
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: bearbeiten einer Task erweitern um TaskBoardID T16.B2
         */
    void editTaskBoardIDInTask() throws Exception{
        //Arrange
        pw.append("Logik-Test-editTaskBoardIDInTask\nTest ID: Logic.T20\n" + "Date: " + formatter.format(date) + '\n');
        TaskBoardService taskBoardService = new TaskBoardService();
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT16B2", "T16B2", 1, -1);
        Task task = null;
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        int tb1ID;
        int tb2ID;

        //Act
        //functional case
        userStoryService.saveUserStory(userStory);
        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());
        taskBoardService.createTaskBoard("TaskBoard1T16B2");
        tb1ID = DAOTaskBoardService.getAll().get(0).getId();
        task = new Task(-1, "TaskT16B2", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, tb1ID);
        taskService.saveTask(task);
        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());
        taskBoardService.createTaskBoard("TaskBoard2T16B2");
        tb2ID = DAOTaskBoardService.getAll().get(1).getId();
        task.setTbID(tb2ID);
        taskService.saveTask(task);
        DAOTaskBoardService.getById(tb2ID);
        List<DAOTaskList> taskLists = DAOTaskListService.getByTaskBoardId(tb2ID);
        DAOTaskList taskList = null;
        for(int i = 0; i < taskLists.size(); i++){
            taskList = taskLists.get(i);
            if(taskList.getSequence() == 1) break;
        }

        DAOTask daoTask = DAOTaskService.getById(task.getID());

        //Exception case
        try{
            count_correct_exception_checks++;
            task.setTbID(103782342);
            taskService.saveTask(task);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //Assert
        try{
            Assertions.assertEquals(daoTask.getTaskList().getId(), taskList.getId());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task did not switch TaskBoards\n");
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: wrong Exception-Hanlding\n");
        }
        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T21
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstellen einer Task erweitern um TaskBoardID + Verknüpfung an TaskList T16.B3
         */
    void createTaskWithTaskBoardID() throws Exception{
        //Arrange
        pw.append("Logik-Test-createTaskWithTaskBoardID\nTest ID: Logic.T21\n" + "Date: " + formatter.format(date) + '\n');
        TaskBoardService taskBoardService = new TaskBoardService();
        String taskBoardName = "TaskBoardT16B3";
        List<TaskBoard> taskBoards;
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        //Act
        //Exception cases
        try{
            count_correct_exception_checks++;
            taskBoardService.createTaskBoard(null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            taskBoardService.createTaskBoard("");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        //functional case
        taskBoardService.createTaskBoard(taskBoardName);

        //Assert
        taskBoards = taskBoardService.getAllTaskBoards();
        if (taskBoards != null){
            try{
                Assertions.assertEquals(taskBoardName, taskBoards.get(taskBoards.size()-1).getName());
            }catch (AssertionError e){
                pass = false;
                pw.append("Fail: TaskBoard not created\n");
                throw new AssertionError(e);
            }
        }else{
            pass = false;
            pw.append("Fail: TaskBoard not created\n");
        }

        if (count_correct_exceptions != count_correct_exception_checks){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b", pass));
    }
}
