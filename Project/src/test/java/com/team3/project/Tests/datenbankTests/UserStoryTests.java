package com.team3.project.Tests.datenbankTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.Tests.BaseClassesForTests.BaseDBTest;

public class UserStoryTests extends BaseDBTest {
    @BeforeAll
    public static void BeforeAll() {
        setup();
        wipeDb(true);
    }

    @BeforeEach
    public void beforeEach() {
        wipeDb(false);
    }

    @AfterEach
    public void afterEach() {
        wipeDb(true);
    }

    @AfterAll
    public static void afterAll() {
        
        tearDown();
    }
    
    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test basis
     * Reason:
     * UserStory/Task-ID: DB.US.T1
     */
    @Test
    void userstoryGeneral() {
        printWriterAddTest("userStoryGeneral", "US.T1");
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        try {
            assertTrue(null == DAOUserStoryService.getByName(name));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent User-Story found");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.create(name, description, priority));
        } catch(AssertionError e) {
            printWriterAddFailure("valid User-Story not created");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.getByName(name).getName().equals(name));
        } catch(AssertionError e) {
            printWriterAddFailure("wrong name in Database");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.deleteById(DAOUserStoryService.getByName(name).getId()));
        } catch(AssertionError e) {
            printWriterAddFailure("existent User-Story not deleted");
            throw new AssertionError(e);
        }
        try {
            assertTrue(null == DAOUserStoryService.getByName(name));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent User-Story found");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test getbyId
     * Reason:
     * UserStory/Task-ID: DB.US.T2
     */
    @Test
    void getByIDTest() {
        printWriterAddTest("getById", "US.T2");
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        try {
            DAOUserStoryService.create(name, description, priority);
            System.out.println("create successfull");
        } catch(Exception e) {
            e.printStackTrace();
        }
        int id = DAOUserStoryService.getByName(name).getId();
        try {
            assertTrue(DAOUserStoryService.getByName(name).getName().equals(DAOUserStoryService.getById(id).getName()));
        } catch(AssertionError e) {
            printWriterAddFailure("wrong Name");
            throw new AssertionError(e);
        }
        try {
            DAOUserStoryService.deleteById(DAOUserStoryService.getByName(name).getId());
        } catch(Exception e) {
            e.printStackTrace();
            printWriterAddFailure("existent User-Story not found");
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test update + check
     * Reason:
     * UserStory/Task-ID: DB.US.T3
     */
    @Test
    void updateTest() {
        printWriterAddTest("update", "US.T3");
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        String newname = "hallelujah!";
        String newdescription = "irgendwas2";
        int newpriority = 2;
        try {
            DAOUserStoryService.create(name, description, priority);
        } catch(Exception e) {
            e.printStackTrace();
        }
        int id = DAOUserStoryService.getByName(name).getId();
        try {
            assertTrue(DAOUserStoryService.updateDescription(id, newdescription));
        } catch(AssertionError e) {
            printWriterAddFailure("existent User-Story with valid new Description not updated");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.updateName(id, newname));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent User-Story found");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.updatePriority(id, newpriority));
        } catch(AssertionError e) {
            printWriterAddFailure("priority did not update to new priority");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.checkName(newname));
        } catch(AssertionError e) {
            printWriterAddFailure("new name not found");
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOUserStoryService.checkName(name));
        } catch(AssertionError e) {
            printWriterAddFailure("old name found");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.getByName(newname).getDescription().equals(newdescription));
        } catch(AssertionError e) {
            printWriterAddFailure("description did not update to new description");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.getByName(newname).getPriority() == priority);
        } catch(AssertionError e) {
            printWriterAddFailure("wrong priority");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.checkId(id));
        } catch(AssertionError e) {
            printWriterAddFailure("existent User-ID not found");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.deleteById(id));
        } catch(AssertionError e) {
            printWriterAddFailure("existent User-Story not deleted");
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOUserStoryService.checkId(id));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent User-Story-ID found");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test getAll
     * Reason:
     * UserStory/Task-ID: DB.US.T4
     */
    @Test
    void getAllTest() {
        printWriterAddTest("getAll", "US.T4");
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        String newname = "hallelujah!";
        String newdescription = "irgendwas2";
        int newpriority = 2;
        try {
            DAOUserStoryService.create(name, description, priority);
        } catch(Exception e) {
            e.printStackTrace();
        }

        List<DAOUserStory> storys = DAOUserStoryService.getAll();

        for (DAOUserStory daoUserStory : storys) {
            if (daoUserStory.getName().equals(name)) { //redundanten Test gelöscht
                try {
                    assertEquals(description, daoUserStory.getDescription());
                } catch(AssertionError e) {
                    printWriterAddFailure("wrong description");
                    throw new AssertionError(e);
                }
                try {
                    assertEquals(priority, daoUserStory.getPriority());
                } catch(AssertionError e) {
                    printWriterAddFailure("wrong priority");
                    throw new AssertionError(e);
                }
            }
        }
        try {
            DAOUserStoryService.create(newname, newdescription, newpriority);
        } catch (Exception e) {
            e.printStackTrace();
        }

        storys = DAOUserStoryService.getAll();

        for (DAOUserStory daoUserStory : storys) {
            if (daoUserStory.getName().equals(name)) { //redundanten Test gelöscht
                try {
                    assertEquals(description, daoUserStory.getDescription());
                } catch (AssertionError e) {
                    printWriterAddFailure("wrong description");
                    throw new AssertionError(e);
                }
                try {
                    assertEquals(priority, daoUserStory.getPriority());
                } catch (AssertionError e) {
                    printWriterAddFailure("wrong priority");
                    throw new AssertionError(e);
                }
            }

            if (daoUserStory.getName().equals(newname)) {//redundanten Test gelöscht
                try {
                    assertEquals(newdescription, daoUserStory.getDescription());
                } catch (AssertionError e) {
                    printWriterAddFailure("wrong description");
                    throw new AssertionError(e);
                }
                try {
                    assertEquals(newpriority, daoUserStory.getPriority());
                } catch (AssertionError e) {
                    printWriterAddFailure("wrong priority");
                    throw new AssertionError(e);
                }
            }
        }
        try {
            DAOUserStoryService.deleteById(DAOUserStoryService.getByName(name).getId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            DAOUserStoryService.deleteById(DAOUserStoryService.getByName(newname).getId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        printWriterAddPass();
    }
}