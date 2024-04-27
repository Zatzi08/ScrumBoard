package com.team3.project.Tests.datenbankTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAO.DAOUserStory;

import com.team3.project.DAOService.DAOUserStoryService;

public class UserStoryTests {
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    @BeforeAll
    public static void setup() {
        try {
            File log = new File("src/test/java/com/team3/project/logs/log.txt");
            log.setWritable(true);
            log.setReadable(true);
            FileWriter fw = new FileWriter(log, true);
            pw = new PrintWriter(fw, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
    }

    @BeforeEach
    public void before() {
        pw.append("\n\n");
        pass = true;
    }

    @AfterAll
    public static void closeWriter() {
        pw.close();
    }
    
    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test basis
     * Reason:
     * UserStory/Task-ID: DB.US.T1
     */
    @Test
    void userstoryGeneral() {
        pw.append("Datenbank-Test-userstoryGeneral\n" + "Test ID: DB.US.T1\n" + "Date: " + formatter.format(date) + '\n');
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        try {
            assertTrue(null == DAOUserStoryService.getByName(name));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent User-Story found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.create(name, description, priority));
        } catch(AssertionError e) {
            pw.append(String.format("fail: valid User-Story not created\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.getByName(name).getName().equals(name));
        } catch(AssertionError e) {
            pw.append(String.format("fail: wrong name in Database\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId()));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent User-Story not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(null == DAOUserStoryService.getByName(name));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent User-Story found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test getbyId
     * Reason:
     * UserStory/Task-ID: DB.US.T2
     */
    @Test
    void getByIDTest() {
        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.US.T2\n" + "Date: " + formatter.format(date) + '\n');
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
            assertTrue(DAOUserStoryService.getByName(name).getName().equals(DAOUserStoryService.getByID(id).getName()));
        } catch(AssertionError e) {
            pw.append(String.format("fail: wrong Name\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId());
        } catch(Exception e) {
            e.printStackTrace();
            pw.append("Fail: existent User-Story not found\n");
            pass = false;
        }
        pw.append(String.format("pass = %b", pass));
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test update + check
     * Reason:
     * UserStory/Task-ID: DB.US.T3
     */
    @Test
    void updateTest() {



        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.US.T3\n" + "Date: " + formatter.format(date) + '\n');
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
            pw.append(String.format("fail: existent User-Story with valid new Description not updated\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.updateName(id, newname));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent User-Story found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.updatePriority(id, newpriority));
        } catch(AssertionError e) {
            pw.append(String.format("fail: priority did not update to new priority\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {

            assertTrue(DAOUserStoryService.checkName(newname));
        } catch(AssertionError e) {
            pw.append(String.format("fail: new name not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOUserStoryService.checkName(name));
        } catch(AssertionError e) {
            pw.append(String.format("fail: old name found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.getByName(newname).getDescription().equals(newdescription));
        } catch(AssertionError e) {
            pw.append(String.format("fail: description did not update to new description\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.getByName(newname).getPriority() == priority);
        } catch(AssertionError e) {
            pw.append(String.format("fail: wrong priority\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.checkId(id));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent User-ID not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserStoryService.delete(id));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent User-Story not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOUserStoryService.checkId(id));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent User-Story-ID found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test getAll
     * Reason:
     * UserStory/Task-ID: DB.US.T4
     */
    @Test
    void testGetAll() {
        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.US.T4\n" + "Date: " + formatter.format(date) + '\n');
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
                    pw.append(String.format("fail: wrong description\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
                try {
                    assertEquals(priority, daoUserStory.getPriority());
                } catch(AssertionError e) {
                    pw.append(String.format("fail: wrong priority\n"));
                    pass = false;
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
                    pw.append(String.format("fail: wrong description\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
                try {
                    assertEquals(priority, daoUserStory.getPriority());
                } catch (AssertionError e) {
                    pw.append(String.format("fail: wrong priority\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
            }

            if (daoUserStory.getName().equals(newname)) {//redundanten Test gelöscht
                try {
                    assertEquals(newdescription, daoUserStory.getDescription());
                } catch (AssertionError e) {
                    pw.append(String.format("fail: wrong description\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
                try {
                    assertEquals(newpriority, daoUserStory.getPriority());
                } catch (AssertionError e) {
                    pw.append(String.format("fail: wrong priority\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
            }
        }
        try {
            DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            DAOUserStoryService.delete(DAOUserStoryService.getByName(newname).getId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        pw.append(String.format("pass = %b", pass));
    }
}
