package com.team3.project.Tests.datenbankTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.Tests.BaseClassesForTests.BaseDBTest;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUser;

public class TaskTest extends BaseDBTest {
    @BeforeAll
    public static void BeforeAll() {
        setup();
    }

    @BeforeEach
    public void beforeEach() {
        before();
    }

    @AfterEach
    public void afterEach() {
        after();
    }

    @AfterAll
    public static void afterAll() {
        tearDown();
    }
    String TestUserStoryName = "TestUserStory";
    String TestUserStoryDes = "TestUserStroyDescription";
    int TestUserStoryPrio = 1;
    String TaskDes1 = "TestTask1";
    String TaskDes2 = "TestTask2";
    String TaskDes3 = "TestTask3";
    int prio = 1;
    List<DAOTask> TestTasks = new ArrayList<>();
    List<DAOUser> userList = new ArrayList<>();
    private String TestEmail = "TestEmail";
    private String TestEmail1 = "TestEmail1";
    private String TestPasword = "TestPasword";
    

    /* Author: Marvin Prüger
     * Function: create/update/delete Task
     * Reason:
     * UserStory/Task-ID: T3.D2/T4.D1/T5.D1
     */
    @Test 
    void GenerlTaskTest(){
        printWriterAddTest("GenerlTaskTest", "T.T1");
        DAOUserStoryService.create(TestUserStoryName, TestUserStoryDes, TestUserStoryPrio);
        try {
            assertTrue(DAOTaskService.create(TaskDes1, DAOUserStoryService.getByName(TestUserStoryName)));
        } catch (Exception e) {
            printWriterAddFailure("Task was not added to UserStory");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOTaskService.updateDescriptonById(DAOTaskService.getByDescription(TaskDes1).getId(), TaskDes2));
        } catch (Exception e) {
            printWriterAddFailure("Task was not updated");
            throw new AssertionError(e);
        }        
        try {
            assertTrue(DAOTaskService.deleteById(DAOTaskService.getByDescription(TaskDes2).getId()));
        } catch (Exception e) {
            printWriterAddFailure("Task was not deleated");
            throw new AssertionError(e);
        }
    }
    /* Author: Marvin Prüger
     * Function: generl Task Test
     * Reason:
     * UserStory/Task-ID: T1.D1
     */
    @Test 
    void getallTaskOfUserStory(){
        printWriterAddTest("GenerlTaskTest", "T.T2");
        DAOUserStoryService.create(TestUserStoryName, TestUserStoryDes, TestUserStoryPrio);
        try {
            assertTrue(DAOTaskService.create(TaskDes1, DAOUserStoryService.getByName(TestUserStoryName)));
        } catch (Exception e) {
            printWriterAddFailure("Task was not added to UserStory");
            throw new AssertionError(e);
        }
        DAOTaskService.create(TaskDes2, DAOUserStoryService.getByName(TestUserStoryName));
        try {
            assertEquals(DAOTaskService.getListByUserStoryId(DAOUserStoryService.getByName(TestUserStoryName).getId()).get(1).getDescription(),TaskDes2);
        } catch (Exception e) {
            printWriterAddFailure("UserStory did not have all tasks");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOTaskService.deleteById(DAOTaskService.getByDescription(TaskDes1).getId()));
        } catch (Exception e) {
            printWriterAddFailure("Task was not deleted");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOTaskService.deleteById(DAOTaskService.getByDescription(TaskDes2).getId()));
        } catch (Exception e) {
            printWriterAddFailure("Task was not deleted");
            throw new AssertionError(e);
        }
        DAOUserStoryService.deleteById(DAOUserStoryService.getByName(TestUserStoryName).getId());
        printWriterAddPass();
    }

    @Test
    /* Author: Marvin Prüger
     * Function: test priority 
     * Reason:
     * UserStory/Task-ID: T7
     */
    void prioTest(){
        printWriterAddTest("prioTest", "T.T3");
        DAOTaskService.create(TaskDes1, prio, false, null, 0, 0, null, null, null);
        try {
            assertEquals(DAOTaskService.getByDescription(TaskDes1).getPriority(),prio);
        } catch (Exception e) {
            printWriterAddFailure("prio not saved");
            throw new AssertionError(e);
        }
        prio++;
        try {
            DAOTaskService.updatePriorityById(DAOTaskService.getByDescription(TaskDes1).getId(),prio);
            assertEquals(DAOTaskService.getByDescription(TaskDes1).getPriority(),prio);
        } catch (Exception e) {
            printWriterAddFailure("prio not updated");
            throw new AssertionError(e);
        }
    }

    @Test
    /* Author: Marvin Prüger
     * Function: test conection of users and Task
     * Reason:
     * UserStory/Task-ID: T8
     */
    void userXtaskTest(){
        printWriterAddTest("userXtaskTest", "T.T4");
        DAOUserService.createByEMail(TestEmail, TestPasword, null, null, null, null, null, null, null, false);
        DAOUserService.createByEMail(TestEmail1, TestPasword, null, null, null, null, null, null, null, false);
        userList.add(DAOUserService.getById(DAOUserService.getIdByMail(TestEmail)));
        DAOTaskService.create(TaskDes1, prio, false, null, 0, 0, null, null, userList);
        try {
            assertEquals(DAOTaskService.getWithUsersById(DAOTaskService.getByDescription(TaskDes1).getId()).getUsers().get(0).getEmail(),TestEmail);
        } catch (Exception e) {
            printWriterAddFailure("users not saved");
            throw new AssertionError(e);
        }
        userList.add(DAOUserService.getById(DAOUserService.getIdByMail(TestEmail1)));
        try {
            assertTrue(DAOTaskService.updateUsersById(DAOTaskService.getByDescription(TaskDes1).getId(),userList));
            assertEquals(DAOTaskService.getWithUsersById(DAOTaskService.getByDescription(TaskDes1).getId()).getUsers().get(1).getEmail(),TestEmail1);
        } catch (Exception e) {
            printWriterAddFailure("users not updated");
            throw new AssertionError(e);
        }
    }
    @Test
    /* Author: Marvin Prüger
     * Function: test Time related things of Tasks
     * Reason:
     * UserStory/Task-ID: T9/10/11
     */
    void TaskTime(){
        printWriterAddTest("Taskfist", "T.T5");
        DAOTaskService.create(TaskDes1, prio, false, "today", 5, 6, null, null, userList);
        int taskId = DAOTaskService.getByDescription(TaskDes1).getId();
        try {
            assertEquals(DAOTaskService.getById(taskId).getDueDate(),"today");
            assertEquals(DAOTaskService.getById(taskId).getProcessingTimeEstimatedInHours(),5);
            assertEquals(DAOTaskService.getById(taskId).getProcessingTimeRealInHours(),6);
        } catch (Exception e) {
            printWriterAddFailure("dueDate or processingtime not saved");
            throw new AssertionError(e);
        }
        DAOTaskService.updateDueDateById(taskId, "AAAAAAAAAAAAAAAA");
        DAOTaskService.updateProcessingTimeEstimatedInHoursById(taskId,6);
        DAOTaskService.updateProcessingTimeRealInHoursById(taskId,5);
        try {
            assertEquals(DAOTaskService.getById(taskId).getDueDate(),"AAAAAAAAAAAAAAAA");
            assertEquals(DAOTaskService.getById(taskId).getProcessingTimeEstimatedInHours(),6);
            assertEquals(DAOTaskService.getById(taskId).getProcessingTimeRealInHours(),5);
        } catch (Exception e) {
            printWriterAddFailure("dueDate or processingtime not updated");
            throw new AssertionError(e);
        }
    }

}