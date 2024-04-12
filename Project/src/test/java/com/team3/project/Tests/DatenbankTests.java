package com.team3.project.Tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.aopalliance.aop.AspectException;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserStoryService;


public class DatenbankTests {
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    @BeforeAll
    public static void setup(){
        try {
            File log = new File("src/test/java/com/team3/project/logs/log.txt");
            log.setWritable(true);
            log.setReadable(true);
            FileWriter fw = new FileWriter(log,true);
            pw = new PrintWriter(fw,true);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
    }

    @BeforeEach
    public void before(){
        pw.append("\n\n");
        pass = true;
    }

    @AfterAll
    public static void closeWriter(){
        pw.close();
    }
    @Test
        /*  Test ID : DB.Acc.T1
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:ACC: test grundfunktionalität für account
         */
    void accGeneral(){
        pw.append("Datenbank-Test-accGeneral\n" + "Test ID: DB.Acc.T1\n" + "Date: " + formatter.format(date)+ '\n');
        String testMail = "testmail1";
        String testPas = "testpas1";
        try {
            assertTrue(DAOAccountService.createAccount(testMail, testPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: valid Account not created\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.checkmail(testMail));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Mail not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.deleteAccount(testMail));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOAccountService.checkmail(testMail));
        }catch(AssertionError e){
            pw.append(String.format("fail:  non-existent Mail found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass: %b", pass));
    }

    @Test
        /* Test ID: DB.Acc.T2
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:ACC: create mit mail/pass = null
     */
    void wrongAccount(){
        pw.append("Datenbank-Test-wrongAccount\n" + "Test ID: DB.Acc.T2\n" + "Date: " + formatter.format(date)+ '\n');
        String noMail = null;
        String noPas = null;
        try {
            DAOAccountService.createAccount(noMail,noPas);
        } catch (Exception e) {
            assertTrue(true);
        }
        try{
            assertFalse(DAOAccountService.checkmail(noMail));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent Mail found \n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOAccountService.deleteAccount(noMail));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent Account was deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b",pass));
    }


    @Test
        /* Test ID: DB.Acc.T3
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:ACC: create mit mail/pass = ""
         */
    void wrongAccount2(){
        pw.append("Datenbank-Test-wrongAccount2\n" + "Test ID: DB.Acc.T3\n" + "Date: " + formatter.format(date)+ '\n');
        String noMail = "";
        String noPas = "";
        try{
            assertTrue(DAOAccountService.createAccount(noMail,noPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: valid Account not created\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.checkmail(noMail));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Account not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.deleteAccount(noMail));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOAccountService.checkmail(noMail));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Mail not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b",pass));
    }

    @Test
        /* Test ID: DB.Acc.T4
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:ACC: logintcheck test if it works
         */
    void loginTest(){
        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.Acc.T4\n" + "Date: " + formatter.format(date)+ '\n');
        String testMail = "testmail";
        String testPas = "testpas";
        String wrongPas = "12345";
        String noPas = null;
        try{
            DAOAccountService.createAccount(testMail,testPas);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: valid Account not created\n"));
            pass = false;
        }
        try{
            assertTrue(DAOAccountService.LoginCheck(testMail, testPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOAccountService.LoginCheck(testMail, wrongPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent Login-Details found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOAccountService.LoginCheck(testMail, noPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent Login-Details found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            DAOAccountService.deleteAccount(testMail);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
        }
        try{
            assertFalse(DAOAccountService.LoginCheck(testMail, testPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent Login-Details found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /* Test ID: DB.Acc.T5
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:ACC: updatepass test if it works
         */
    void updatePassTest(){
        pw.append("Datenbank-Test-updatePassTest\n" + "Test ID: DB.Acc.T5\n" + "Date: " + formatter.format(date)+ '\n');
        String testMail = "testmail2";
        String oldPas = "testpas2";
        String newPas = "12345";
        try{
            assertFalse(DAOAccountService.updatePassword(testMail,newPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent Account found + Password updated\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            DAOAccountService.createAccount(testMail,oldPas);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: valid Account not created\n"));
            pass = false;
        }
        try{
            assertTrue(DAOAccountService.LoginCheck(testMail, oldPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.updatePassword(testMail, newPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Account not updated with new valid Password \n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.LoginCheck(testMail, newPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOAccountService.LoginCheck(testMail, oldPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent Login-Details  found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            DAOAccountService.deleteAccount(testMail);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
        }







    }

    @Test
        /* Test ID: DB.Acc.T6
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:ACC: updatepass new = old
         */
    void newOldPasTest(){
        pw.append("Datenbank-Test-newOldPasTest\n" + "Test ID: DB.Acc.T6\n" + "Date: " + formatter.format(date)+ '\n');
        String testMail = "testmail3";
        String oldPas = "testpas3";
        String newPas = "testpas";
        try{
            DAOAccountService.createAccount(testMail,oldPas);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: valid Account not created\n"));
            pass = false;
        }
        try{
            assertTrue(DAOAccountService.LoginCheck(testMail, oldPas));
        }catch (AssertionError e){
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.updatePassword(testMail, newPas));
        }catch (AssertionError e){
            pw.append(String.format("fail: existent Account not with valid new Password updated\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.LoginCheck(testMail, newPas));
        }catch (AssertionError e){
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOAccountService.LoginCheck(testMail, oldPas));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent Login-Details not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            DAOAccountService.deleteAccount(testMail);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: existent Account not deleted\n"));
            pass = false;
        }
        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /* Test ID: DB.US.T1
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:UserStory: test basis
         */
    void userstoryGeneral(){
        pw.append("Datenbank-Test-userstoryGeneral\n" + "Test ID: DB.US.T1\n" + "Date: " + formatter.format(date)+ '\n');
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        try{
            assertTrue(null == DAOUserStoryService.getByName(name));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent User-Story found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.create(name, description, priority));
        }catch(AssertionError e){
            pw.append(String.format("fail: valid User-Story not created\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.getByName(name).getName().equals(name));
        }catch(AssertionError e){
            pw.append(String.format("fail: wrong name in Database\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId()));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent User-Story not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(null == DAOUserStoryService.getByName(name));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent User-Story found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b",pass));
    }

    @Test
        /* Test ID: DB.US.T2
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:UserStory: test getbyId
         */
    void getByIDTest(){
        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.US.T2\n" + "Date: " + formatter.format(date)+ '\n');
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        try{
            DAOUserStoryService.create(name, description, priority);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: valid User-Story not created\n"));
            pass = false;
        }
        int id = DAOUserStoryService.getByName(name).getId();
        try{
            assertTrue(DAOUserStoryService.getByName(name).getName().equals(DAOUserStoryService.getByID(id).getName()));
        }catch(AssertionError e){
            pw.append(String.format("fail: wrong Name\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId());
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: existent User-Story not found\n"));
            pass = false;
        }
        pw.append(String.format("pass = %b",pass));
    }

    @Test
        /* Test ID: DB.US.T3
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:UserStory: test update + check
         */
    void updateTest(){
        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.US.T3\n" + "Date: " + formatter.format(date)+ '\n');
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        String newname = "hallelujah!";
        String newdescription = "irgendwas2";
        int newpriority = 2;
        try{
            DAOUserStoryService.create(name, description, priority);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: valid User-Story not created\n"));
            pass = false;
        }
        int id = DAOUserStoryService.getByName(name).getId();
        try{
            assertTrue(DAOUserStoryService.updateDescription(id, newdescription));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent User-Story with valid new Description not updated\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.updateName(id, newname));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent User-Story found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.updatePriority(id, newpriority));
        }catch(AssertionError e){
            pw.append(String.format("fail: priority did not update to new priority\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.checkName(newname));
        }catch(AssertionError e){
            pw.append(String.format("fail: new name not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOUserStoryService.checkName(name));
        }catch(AssertionError e){
            pw.append(String.format("fail: old name found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.getByName(newname).getDescription().equals(newdescription));
        }catch(AssertionError e){
            pw.append(String.format("fail: description did not update to new description\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.getByName(newname).getPriority() == priority);
        }catch(AssertionError e){
            pw.append(String.format("fail: wrong priority\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.checkId(id));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent User-ID not found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserStoryService.delete(id));
        }catch(AssertionError e){
            pw.append(String.format("fail: existent User-Story not deleted\n"));
            pass = false;
            throw new AssertionError(e);
        }
        try{
            assertFalse(DAOUserStoryService.checkId(id));
        }catch(AssertionError e){
            pw.append(String.format("fail: non-existent User-Story-ID found\n"));
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b",pass));
    }

    @Test
        /* Test ID: DB.US.T4
         *  Author: Marvin Prüger / Tom-Malte Seep
         *  Zweck:UserStory: test getall
         */
    void testGetAll(){
        pw.append("Datenbank-Test-loginTest\n" + "Test ID: DB.US.T4\n" + "Date: " + formatter.format(date)+ '\n');
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        String newname = "hallelujah!";
        String newdescription = "irgendwas2";
        int newpriority = 2;
        try{
            DAOUserStoryService.create(name, description, priority);
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: valid User-Story not created\n"));
            pass = false;
        }

        List<DAOUserStory> storys = DAOUserStoryService.getAll();

        for (DAOUserStory daoUserStory : storys) {
            if (daoUserStory.getName().equals(name)){ //redundanten Test gelöscht
                try{
                    assertEquals(description, daoUserStory.getDescription());
                }catch(AssertionError e){
                    pw.append(String.format("fail: wrong description\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
                try{
                    assertEquals(priority, daoUserStory.getPriority());
                }catch(AssertionError e){
                    pw.append(String.format("fail: wrong priority\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
            }
        }
        try{
            DAOUserStoryService.create(newname, newdescription, newpriority);
        }catch (Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: valid User-Story not created\n"));
            pass = false;
        }

        storys = DAOUserStoryService.getAll();

        for (DAOUserStory daoUserStory : storys) {
            if (daoUserStory.getName().equals(name)){ //redundanten Test gelöscht
                try{
                    assertEquals(description, daoUserStory.getDescription());
                }catch (AssertionError e){
                    pw.append(String.format("fail: wrong description\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
                try{
                    assertEquals(priority, daoUserStory.getPriority());
                }catch (AssertionError e){
                    pw.append(String.format("fail: wrong priority\n"));
                    pass = false;
                    throw new AssertionError(e);
                }



            }
            if (daoUserStory.getName().equals(newname)) {//redundanten Test gelöscht
                try{
                    assertEquals(newdescription, daoUserStory.getDescription());
                }catch (AssertionError e){
                    pw.append(String.format("fail: wrong description\n"));
                    pass = false;
                    throw new AssertionError(e);
                }
                try{
                    assertEquals(newpriority, daoUserStory.getPriority());
                }catch (AssertionError e){
                    pw.append(String.format("fail: wrong priority\n"));
                    pass = false;
                    throw new AssertionError(e);
                }

            }
        }
        try{
            DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId());
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: existent User-Story not deleted\n"));
            pass = false;
        }
        try{
            DAOUserStoryService.delete(DAOUserStoryService.getByName(newname).getId());
        }catch(Exception e){
            e.printStackTrace();
            pw.append(String.format("fail: existent User-Story not deleted\n"));
            pass = false;
        }
        pw.append(String.format("pass = %b",pass));
    }
}


