package com.team3.project.Tests.datenbankTests;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAO.DAOAccount;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOStartService;

public class AccountTests extends BaseTest {
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

    ////////////////////////////////
    static List<String> mails = new ArrayList<String>();
    DAOAccount account = new DAOAccount();

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: test grundfunktionalität für account
     * Reason:
     * UserStory/Task-ID: DB.Acc.T1
     */
    @Test
    void accGeneralTest() {
        printWriterAddTest("accGeneral", "Acc.T1");
        String testMail     = "testMail1@testmail.com";
        mails.add(testMail);
        String testPassword = "password";
        try { //create account
            assertTrue(DAOAccountService.create(testMail, testPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("valid Account not created");
            throw new AssertionError(e);
        }
        try { //check exists(account)
            assertTrue(DAOAccountService.checkMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Mail not found");
            throw new AssertionError(e);
        }
        try { //delete account
            assertTrue(DAOAccountService.deleteByMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Account not deleted");
            throw new AssertionError(e);
        }
        try { //check !exists(account)
            assertFalse(DAOAccountService.checkMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Mail found");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
    * Function: create mit mail/pass = null
    * Reason:
    * UserStory/Task-ID: DB.Acc.T2
    */
    @Test
    void nullAccountTest() {
        printWriterAddTest("nullAccount", "Acc.T2");
        String testMail = null;
        mails.add(testMail);
        try { //create account = null
            //TODO Wahrscheinlich obsolet, da create die Exception abfängt
            DAOAccountService.create(testMail, null);
        } catch (Exception e) {
            assertTrue(true);
        }
        try { //check exists(account = null)
            assertFalse(DAOAccountService.checkMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Mail found");
            throw new AssertionError(e);
        }
        try { //delete account = null
            assertTrue(DAOAccountService.deleteByMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Account was deleted");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: create mit mail/pass = ""
     * Reason:
     * UserStory/Task-ID: DB.Acc.T3
     */
    @Test
    void emptyAccountTest() {
        printWriterAddTest("emptyAccount", "Acc.T3");
        String testMail = "";
        mails.add(testMail);
        try { //create account.mail = ""
            assertTrue(DAOAccountService.create(testMail, ""));
        } catch(AssertionError e) {
            printWriterAddFailure("valid Account not created");
            throw new AssertionError(e);
        }
        try { //check exists(account.mail = "")
            assertTrue(DAOAccountService.checkMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Account not found");
            throw new AssertionError(e);
        }
        try { //delete account.mail = "" 
            assertTrue(DAOAccountService.deleteByMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Account not deleted");
            throw new AssertionError(e);
        }
        try { //check !exists(account.mail = "")
            assertFalse(DAOAccountService.checkMail(testMail));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Mail not found");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: checkLogin test if it works
     * Reason:
     * UserStory/Task-ID: DB.Acc.T4
     */
    @Test
    void loginTest() {
        printWriterAddTest("login", "Acc.T4");
        String testMail = "testmail";
        mails.add(testMail);
        String testPassword = "testpas";
        String wrongPassword = "12345";
        String nullPassword = null;
        try { //create account
            DAOAccountService.create(testMail, testPassword);
        } catch(Exception e) {
            printWriterAddFailure("create Mail unsuccessfull");
            throw new AssertionError(e);
        }
        try { //check account.password == testPassword
             assertTrue(DAOAccountService.checkLogin(testMail, testPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Login-Details not found");
            throw new AssertionError(e);
        }
        try { //check account.password == wrongPassword
            assertFalse(DAOAccountService.checkLogin(testMail, wrongPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Login-Details found");
            throw new AssertionError(e);
        }
        try { //check account.password == null
            assertFalse(DAOAccountService.checkLogin(testMail, nullPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Login-Details found");
            throw new AssertionError(e);
        }
        try { //delete account
            DAOAccountService.deleteByMail(testMail);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try { //check account(=null).password == testPassword
            assertFalse(DAOAccountService.checkLogin(testMail, testPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Login-Details found");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: updatespass test if it works
     * Reason:
     * UserStory/Task-ID: DB.Acc.T5
     */
    @Test
    void updatePasswordTest() {
        printWriterAddTest("updatePassword", "Acc.T5");
        String testMail = "testmail2";
        mails.add(testMail);
        String oldPassword = "testpas2";
        String newPassword = "12345";
        try { 
            assertFalse(DAOAccountService.updatePassword(testMail, newPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Account found + Password updated");
            throw new AssertionError(e);
        }
        try {
            DAOAccountService.create(testMail, oldPassword);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            assertTrue(DAOAccountService.checkLogin(testMail, oldPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Login-Details not found");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.updatePassword(testMail, newPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Account not updated with new valid Password");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.checkLogin(testMail, newPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Login-Details not found");
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.checkLogin(testMail, oldPassword));
        } catch(AssertionError e) {
            printWriterAddFailure("non-existent Login-Details found");
            throw new AssertionError(e);
        }
        try {
            DAOAccountService.deleteByMail(testMail);
        } catch(Exception e) {
            printWriterAddFailure("existent Account not deleted");
            e.printStackTrace();
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger / Tom-Malte Seep
     * Function: updatespass new = old
     * Reason:
     * UserStory/Task-ID: DB.Acc.T6
     */
    @Test
    void newOldPasswordTest() {
        printWriterAddTest("newOldPassword", null);
        printWriter.append("Datenbank-Test-newOldPasTest\n" + "Test ID: DB.Acc.T6\n" + "Date: " + formatter.format(date) + '\n');
        String testMail = "testmail3";
        mails.add(testMail);
        String oldPas = "testpas3";
        String newPas = "testpas";
        try {
            DAOAccountService.create(testMail, oldPas);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            assertTrue(DAOAccountService.checkLogin(testMail, oldPas));
        } catch (AssertionError e) {
            printWriterAddFailure("existent Login-Details not found");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.updatePassword(testMail, newPas));
        } catch (AssertionError e) {
            printWriterAddFailure("existent Account not with valid new Password updated");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOAccountService.checkLogin(testMail, newPas));
        } catch (AssertionError e) {
            printWriterAddFailure("existent Login-Details not found");
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOAccountService.checkLogin(testMail, oldPas));
        } catch(AssertionError e) {
            printWriterAddFailure("existent Login-Details not found");
            throw new AssertionError(e);
        }
        try {
            DAOAccountService.deleteByMail(testMail);
        } catch(Exception e) {
            e.printStackTrace();
        }
        printWriterAddPass();
    }
}
