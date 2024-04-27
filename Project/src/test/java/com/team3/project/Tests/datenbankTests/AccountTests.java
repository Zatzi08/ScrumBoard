package com.team3.project.Tests.datenbankTests;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAOService.DAOAccountService;

public class AccountTests {
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
     * Function: test grundfunktionalität für account
     * Reason:
     * UserStory/Task-ID: DB.Acc.T1
     */
    @Test
    void accGeneralTest() {
        pw.append("Datenbank-Test-accGeneral\n" + "Test ID: DB.Acc.T1\n" + "Date: " + formatter.format(date) + '\n');
        String testMail = "testmail1";
        String testPas = "testpas1";
        try {
            assertTrue(DAOAccountService.create(testMail, testPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: valid Account not created\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.checkMail(testMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Mail not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.deleteByMail(testMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.checkMail(testMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail:  non-existent Mail found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass: %b", pass));
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
    * Function: create mit mail/pass = null
    * Reason:
    * UserStory/Task-ID: DB.Acc.T2
    */
    @Test
    void wrongAccountTest() {
        pw.append("Datenbank-Test-wrongAccount\n" + "Test ID: DB.Acc.T2\n" + "Date: " + formatter.format(date) + '\n');
        String nullMail = null;
        String nullPass = null;
        try {
            DAOAccountService.create(nullMail, nullPass);
        } catch (Exception e) {
            assertTrue(true);
        }
        try {
            assertFalse(DAOAccountService.checkMail(nullMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent Mail found \n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.deleteByMail(nullMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent Account was deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }


    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: create mit mail/pass = ""
     * Reason:
     * UserStory/Task-ID: DB.Acc.T3
     */
    @Test
    void wrongAccount2Test() {
        pw.append("Datenbank-Test-wrongAccount2\n" + "Test ID: DB.Acc.T3\n" + "Date: " + formatter.format(date) + '\n');
        String noMail = "";
        String noPass = "";
        try {
            assertTrue(DAOAccountService.create(noMail,noPass));
        } catch(AssertionError e) {
            pw.append(String.format("fail: valid Account not created\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.checkMail(noMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Account not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.deleteByMail(noMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.checkMail(noMail));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Mail not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: logincheck test if it works
     * Reason:
     * UserStory/Task-ID: DB.Acc.T4
     */
    @Test
    void loginTest() {
        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.Acc.T4\n" + "Date: " + formatter.format(date) + '\n');
        String testMail = "testmail";
        String testPas = "testpas";
        String wrongPas = "12345";
        String noPas = null;
        try {
            DAOAccountService.create(testMail,testPas);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
             assertTrue(DAOAccountService.loginCheck(testMail, testPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.loginCheck(testMail, wrongPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent Login-Details found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.loginCheck(testMail, noPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent Login-Details found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            DAOAccountService.deleteByMail(testMail);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            assertFalse(DAOAccountService.loginCheck(testMail, testPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent Login-Details found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: updatespass test if it works
     * Reason:
     * UserStory/Task-ID: DB.Acc.T5
     */
    @Test
    void updatePassTest() {
        pw.append("Datenbank-Test-updatePassTest\n" + "Test ID: DB.Acc.T5\n" + "Date: " + formatter.format(date) + '\n');
        String testMail = "testmail2";
        String oldPas = "testpas2";
        String newPas = "12345";
        try {
            assertFalse(DAOAccountService.updatePassword(testMail, newPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent Account found + Password updated\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            DAOAccountService.create(testMail, oldPas);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            assertTrue(DAOAccountService.loginCheck(testMail, oldPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.updatePassword(testMail, newPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Account not updated with new valid Password \n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.loginCheck(testMail, newPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.loginCheck(testMail, oldPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: non-existent Login-Details  found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            DAOAccountService.deleteByMail(testMail);
        } catch(Exception e) {
            e.printStackTrace();
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
        }
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: updatespass new = old
     * Reason:
     * UserStory/Task-ID: DB.Acc.T6
     */
    @Test
    void newOldPasTest() {
        pw.append("Datenbank-Test-newOldPasTest\n" + "Test ID: DB.Acc.T6\n" + "Date: " + formatter.format(date) + '\n');
        String testMail = "testmail3";
        String oldPas = "testpas3";
        String newPas = "testpas";
        try {
            DAOAccountService.create(testMail, oldPas);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            assertTrue(DAOAccountService.loginCheck(testMail, oldPas));
        } catch (AssertionError e) {
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.updatePassword(testMail, newPas));
        } catch (AssertionError e) {
            pw.append(String.format("fail: existent Account not with valid new Password updated\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.loginCheck(testMail, newPas));
        } catch (AssertionError e) {
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.loginCheck(testMail, oldPas));
        } catch(AssertionError e) {
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try {
            DAOAccountService.deleteByMail(testMail);
        } catch(Exception e) {
            e.printStackTrace();
        }
        pw.append(String.format("pass = %b", pass));
    }
}
