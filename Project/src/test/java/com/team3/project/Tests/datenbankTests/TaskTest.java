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
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.DAO.DAOTask;

public class TaskTest extends BaseTest{
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
    List<DAOTask> TestTasks = new ArrayList<>();

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
            assertTrue(DAOTaskService.updateDescriptonById(DAOTaskService.getByDescription(TaskDes1).getTid(), TaskDes2));
        } catch (Exception e) {
            printWriterAddFailure("Task was not updated");
            throw new AssertionError(e);
        }        
        try {
            assertTrue(DAOTaskService.deleteById(DAOTaskService.getByDescription(TaskDes2).getTid()));
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
            assertTrue(DAOTaskService.deleteById(DAOTaskService.getByDescription(TaskDes1).getTid()));
        } catch (Exception e) {
            printWriterAddFailure("Task was not deleted");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOTaskService.deleteById(DAOTaskService.getByDescription(TaskDes2).getTid()));
        } catch (Exception e) {
            printWriterAddFailure("Task was not deleted");
            throw new AssertionError(e);
        }
        DAOUserStoryService.deleteById(DAOUserStoryService.getByName(TestUserStoryName).getId());
        printWriterAddPass();
    }

}