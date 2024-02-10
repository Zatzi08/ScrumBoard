package com.team3.project.Tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserStoryService;


public class DatenbankTests {
    /* Test ID: DB.Acc.T1
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:ACC: test grundfunktionalität für account
     */
    @Test
    void accGeneral(){
        String testMail = "testmail1";
        String testPas = "testpas1";
        assertTrue(DAOAccountService.createAccount(testMail,testPas));
        assertTrue(DAOAccountService.checkmail(testMail));
        assertTrue(DAOAccountService.deleteAccount(testMail));
        assertFalse(DAOAccountService.checkmail(testMail));
    }

        /* Test ID: DB.Acc.T2
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:ACC: create mit mail/pass = null
     */
    @Test
    void wrongAccount(){
        String noMail = null;
        String noPas = null;
        try {
            DAOAccountService.createAccount(noMail,noPas);
        } catch (Exception e) {
            assertTrue(true);
        }
        assertFalse(DAOAccountService.checkmail(noMail));
        assertFalse(DAOAccountService.deleteAccount(noMail));
    }

    /* Test ID: DB.Acc.T3
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:ACC: create mit mail/pass = ""
     */
    @Test
    void wrongAccount2(){
        String noMail = "";
        String noPas = "";
        assertTrue(DAOAccountService.createAccount(noMail,noPas));
        assertTrue(DAOAccountService.checkmail(noMail));
        assertTrue(DAOAccountService.deleteAccount(noMail));
        assertFalse(DAOAccountService.checkmail(noMail));
        //oh oh das nicht gut
    }

    /* Test ID: DB.Acc.T4
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:ACC: logintcheck test if it works
     */
    @Test
    void loginTest(){
        String testMail = "testmail";
        String testPas = "testpas";
        String wrongPas = "12345";
        String noPas = null;
        DAOAccountService.createAccount(testMail,testPas);
        assertTrue(DAOAccountService.LoginCheck(testMail, testPas));
        assertFalse(DAOAccountService.LoginCheck(testMail, wrongPas));
        assertFalse(DAOAccountService.LoginCheck(testMail, noPas));
        DAOAccountService.deleteAccount(testMail);
        assertFalse(DAOAccountService.LoginCheck(testMail, testPas));
    }
    /* Test ID: DB.Acc.T5
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:ACC: updatepass test if it works
     */
    @Test
    void updatePassTest(){
        String testMail = "testmail2";
        String oldPas = "testpas2";
        String newPas = "12345";
        assertFalse(DAOAccountService.updatePassword(testMail,newPas));
        DAOAccountService.createAccount(testMail,oldPas);
        assertTrue(DAOAccountService.LoginCheck(testMail, oldPas));
        assertTrue(DAOAccountService.updatePassword(testMail, newPas));
        assertTrue(DAOAccountService.LoginCheck(testMail, newPas));
        assertFalse(DAOAccountService.LoginCheck(testMail, oldPas));
        DAOAccountService.deleteAccount(testMail);
    }
    /* Test ID: DB.Acc.T6
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:ACC: updatepass new = old
     */
    @Test
    void newOldPasTest(){
        String testMail = "testmail3";
        String oldPas = "testpas3";
        String newPas = "testpas";
        DAOAccountService.createAccount(testMail,oldPas);
        assertTrue(DAOAccountService.LoginCheck(testMail, oldPas));
        assertTrue(DAOAccountService.updatePassword(testMail, newPas));
        assertTrue(DAOAccountService.LoginCheck(testMail, newPas));
        assertTrue(DAOAccountService.LoginCheck(testMail, oldPas));
        DAOAccountService.deleteAccount(testMail);
        //soll das so?
    }
    /* Test ID: DB.US.T1
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:UserStory: test basis 
     */
    @Test
    void userstoryGeneral(){
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        assertTrue(null == DAOUserStoryService.getByName(name));
        assertTrue(DAOUserStoryService.create(name, description, priority));
        assertTrue(DAOUserStoryService.getByName(name).getName().equals(name));
        assertTrue(DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId()));
        assertTrue(null == DAOUserStoryService.getByName(name));
    }
    /* Test ID: DB.US.T2
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:UserStory: test getbyId
     */
    @Test
    void getByIDTest(){
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        DAOUserStoryService.create(name, description, priority);
        int id = DAOUserStoryService.getByName(name).getId();
        assertTrue(DAOUserStoryService.getByName(name).getName().equals(DAOUserStoryService.getByID(id).getName()));
        DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId());
    }
    /* Test ID: DB.US.T3
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:UserStory: test update + check 
     */
    @Test
    void updateTest(){
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        String newname = "hallelujah!";
        String newdescription = "irgendwas2";
        int newpriority = 2;
        DAOUserStoryService.create(name, description, priority);
        int id = DAOUserStoryService.getByName(name).getId();
        assertTrue(DAOUserStoryService.updateDescription(id, newdescription));
        assertTrue(DAOUserStoryService.updateName(id, newname));
        assertTrue(DAOUserStoryService.updatePriority(id, newpriority));
        assertTrue(DAOUserStoryService.checkName(newname));
        assertFalse(DAOUserStoryService.checkName(name));
        assertTrue(DAOUserStoryService.getByName(newname).getDescription().equals(newdescription));
        assertTrue(DAOUserStoryService.getByName(newname).getPriority() == priority);
        assertTrue(DAOUserStoryService.checkId(id));
        assertTrue(DAOUserStoryService.delete(id));
        assertFalse(DAOUserStoryService.checkId(id));
    }
        /* Test ID: DB.US.T4
     *  Author: Marvin Prüger / Tom-Malte Seep
     *  Zweck:UserStory: test getall
     */
    @Test
    void testGetAll(){
        String name = "hallelujah?";
        String description = "irgendwas";
        int priority = 2;
        String newname = "hallelujah!";
        String newdescription = "irgendwas2";
        int newpriority = 2;

        DAOUserStoryService.create(name, description, priority);
        List<DAOUserStory> storys = DAOUserStoryService.getAll();
        for (DAOUserStory daoUserStory : storys) {
            if (daoUserStory.getName().equals(name)) {
                assertEquals(name, daoUserStory.getName());
                assertEquals(description, daoUserStory.getDescription());
                assertEquals(priority, daoUserStory.getPriority());
            }
        }

        DAOUserStoryService.create(newname, newdescription, newpriority);
        storys = DAOUserStoryService.getAll();
        for (DAOUserStory daoUserStory : storys) {
            if (daoUserStory.getName().equals(name)) {
                assertEquals(name, daoUserStory.getName());
                assertEquals(description, daoUserStory.getDescription());
                assertEquals(priority, daoUserStory.getPriority());
            }
            if (daoUserStory.getName().equals(newname)) {
                assertEquals(newname, daoUserStory.getName());
                assertEquals(newdescription, daoUserStory.getDescription());
                assertEquals(newpriority, daoUserStory.getPriority());
            }
        }

        DAOUserStoryService.delete(DAOUserStoryService.getByName(name).getId());
        DAOUserStoryService.delete(DAOUserStoryService.getByName(newname).getId());
    }
}


