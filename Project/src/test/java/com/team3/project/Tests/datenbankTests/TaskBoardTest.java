package com.team3.project.Tests.datenbankTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.Tests.BaseClassesForTests.BaseDBTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TaskBoardTest extends BaseDBTest {
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

    String TBname = "TestBoard";
    String TaskDes1 = "TestTask";

    @Test
    /* Author: Marvin Prüger
     * Function: Tests Create Standert TB
     * Reason:
     * UserStory/Task-ID: TB9
     */
    void CreateStandertTBTest(){
        printWriterAddTest("CreateStandertTBTest", "TB.T1");
        try {
            assertTrue(DAOTaskBoardService.create(TBname, null));
            assertEquals(DAOTaskBoardService.getByName(TBname).getName(),TBname);
        } catch (Exception e) {
            printWriterAddFailure("Taskbord was not created");
            throw new AssertionError(e);
        }
        List<String> names = Arrays.asList("freie Tasks", "Tasks in Bearbeitung", "Tasks unter Review", "Tasks unter Test", "fertiggestellte Tasks");
        try {
            DAOTaskBoardService.getWithTaskListsById(DAOTaskBoardService.getByName(TBname).getId()).getTaskLists().stream().forEach(tasklist->{
                assertTrue(names.contains(tasklist.getName()));
            });
        } catch (Exception e) {
            printWriterAddFailure("Taskbord was not created");
            throw new AssertionError(e);
        }
    }

    @Test
    /* Author: Marvin Prüger
     * Function: Test that tasks can be read form TB and that they can be saved on to them
     * Reason:
     * UserStory/Task-ID: TB2/3/T16
     */
    void SaveTasksOnTB(){
        printWriterAddTest("SaveTasksOnTB", "TB.T2");
        DAOTaskBoardService.create(TBname, null);
        DAOTaskService.create(TaskDes1, 1, false, null, 0, 0, null, null, null);
        try {
            assertTrue(DAOTaskService.updateTaskBoardIdById(DAOTaskService.getByDescription(TaskDes1).getId(),DAOTaskBoardService.getByName(TBname).getId()));
        } catch (Exception e) {
            printWriterAddFailure("task was not added to TB");
            throw new AssertionError(e);
        }
        try {
            assertEquals(DAOTaskBoardService.getWithTaskListsWithTasksById(DAOTaskBoardService.getByName(TBname).getId())
                .getTaskLists().stream().filter(tasklist->tasklist.getName().equals("freie Tasks"))
                .findFirst().orElse(null).getTasks().get(0).getDescription(),TaskDes1);
        } catch (Exception e) {
            printWriterAddFailure("task was not added to TB");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOTaskService.updateTaskListById(DAOTaskService.getByDescription(TaskDes1).getId(), DAOTaskBoardService.getWithTaskListsById(DAOTaskBoardService.getByName(TBname).getId())
                .getTaskLists().stream().filter(tasklist->tasklist.getName().equals("Tasks in Bearbeitung")).findFirst().orElse(null)));
            assertEquals(DAOTaskBoardService.getWithTaskListsWithTasksById(DAOTaskBoardService.getByName(TBname).getId())
                .getTaskLists().stream().filter(tasklist->tasklist.getName().equals("Tasks in Bearbeitung"))
                .findFirst().orElse(null).getTasks().get(0).getDescription(),TaskDes1);
        } catch (Exception e) {
            printWriterAddFailure("task was not moved");
            throw new AssertionError(e);
        }
    }

    @Test
    /* Author: Marvin Prüger
     * Function: Test del and rename
     * Reason:
     * UserStory/Task-ID: TB10/11
     */
    void delAndRanameTB(){
        printWriterAddTest("delAndRanameTB", "TB.T3");
        DAOTaskBoardService.create(TBname, null);
        try {
            assertTrue(DAOTaskBoardService.updateNameById(DAOTaskBoardService.getByName(TBname).getId(),"bogus"));
            assertNotNull(DAOTaskBoardService.getByName("bogus"));
        } catch (Exception e) {
            printWriterAddFailure("board was not renamed");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOTaskBoardService.deleteById(DAOTaskBoardService.getByName("bogus").getId()));
            assertNull(DAOTaskBoardService.getByName("bogus"));
        } catch (Exception e) {
            printWriterAddFailure("board was not renamed");
            throw new AssertionError(e);
        }
    }
}
