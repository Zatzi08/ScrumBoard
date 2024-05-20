package com.team3.project.Tests.datenbankTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAOService.DAOStartService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Tests.BaseClassesForTests.BaseDBTest;
import com.team3.project.DAO.DAORole;

public class UserTests extends BaseDBTest {
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
    DAOStartService.clearDB("User");
    DAOStartService.clearDB("Role");
    }

    @AfterAll
    public static void afterAll() {
        tearDown();
    }

    private String TestEmail = "TestEmail";
    private String TestPasword = "TestPasword";
    private String TestName = "TestName";
    private String TestName2 = "TestName2";
    private String TestPrivatDescription = "TestPrivatDescription";
    private String TestWorkDescription = "TestWorkDescription";
    private DAORole TestRole = new DAORole("TestRol");
    private DAORole TestRole2 = new DAORole("TestRol2");
    private List<DAORole> TestRoles = new ArrayList<>();

    /* Author: Marvin Prüger
     * Function: User Create Test
     * Reason:
     * UserStory/Task-ID: P1.D1
     */
    @Test 
    void CreateUserTest(){
        printWriterAddTest("CreateUserTest", "U.T1");
        TestRoles.add(TestRole);
        try {
            assertTrue(DAOUserService.createByEMail(TestEmail, TestPasword, TestName, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
        } catch (Exception e) {
            printWriterAddFailure("User was not created");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.checkByEmail(TestEmail));
        } catch (Exception e) {
            printWriterAddFailure("created dose not User exists");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.deleteById(DAOUserService.getIdByMail(TestEmail)));
        } catch (Exception e) {
            printWriterAddFailure("User was not deleted");
            throw new AssertionError(e);
        }
        try {
            assertFalse(DAOUserService.checkByEmail(TestEmail));
        } catch (Exception e) {
            printWriterAddFailure("created User exists exist after delat");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }
    /* Author: Marvin Prüger
     * Function: User Create Test
     * Reason:
     * UserStory/Task-ID: P1.D2
     */
    @Test 
    void GetByIDTest(){
        printWriterAddTest("GetByIDTest", "U.T2");
        TestRoles.add(TestRole);
        try {
            assertTrue(DAOUserService.createByEMail(TestEmail, TestPasword, TestName, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
        } catch (Exception e) {
            printWriterAddFailure("User was not created");
            throw new AssertionError(e);
        }
        try {
            assertEquals(DAOUserService.getByIdPlusRoles(DAOUserService.getIdByMail(TestEmail)).getEmail(),TestEmail);
        } catch (Exception e) {
            printWriterAddFailure("did not retrieved right User");
            throw new AssertionError(e);
        }
        try{
            assertEquals(DAOUserService.getByIdPlusRoles(DAOUserService.getIdByMail(TestEmail)).getRoles().get(0).getName(),TestRoles.get(0).getName());
        } catch (Exception e) {
            printWriterAddFailure("retrieved wrong roles");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.deleteById(DAOUserService.getIdByMail(TestEmail)));
        } catch (Exception e) {
            printWriterAddFailure("User was not deleted");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }
    
    /* Author: Marvin Prüger
     * Function: User Create Test
     * Reason:
     * UserStory/Task-ID: P2.D1
     */
    @Test 
    void UpdateUserTest(){
        printWriterAddTest("UpdateUserTest", "U.T3");
        TestRoles.add(TestRole);
        try {
            assertTrue(DAOUserService.createByEMail(TestEmail, TestName, TestPasword, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
        } catch (Exception e) {
            printWriterAddFailure("User was not created");
            throw new AssertionError(e);
        }
        TestRoles.add(TestRole2);
        try {
            assertTrue(DAOUserService.updateById(DAOUserService.getIdByMail(TestEmail), TestName2, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
            assertTrue(DAOUserService.updateById(DAOUserService.getIdByMail(TestEmail), TestName2, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
        } catch (Exception e) {
            printWriterAddFailure("did not update");
            throw new AssertionError(e);
        }
        try{
            assertEquals(DAOUserService.getByIdPlusRoles(DAOUserService.getIdByMail(TestEmail)).getName(),TestName2);
        } catch (Exception e) {
            printWriterAddFailure("did not update name");
            throw new AssertionError(e);
        }
        try{
            assertEquals(DAOUserService.getByIdPlusRoles(DAOUserService.getIdByMail(TestEmail)).getRoles().get(0).getName(),TestRoles.get(0).getName());
        } catch (Exception e) {
            printWriterAddFailure("did not update rols");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.deleteById(DAOUserService.getIdByMail(TestEmail)));
        } catch (Exception e) {
            printWriterAddFailure("User was not deleted");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }
    /* Author: Marvin Prüger
     * Function: SessionID Test
     * Reason:
     * UserStory/Task-ID: S7.D3
     */
    @Test 
    void UpadteSessionIDTest(){
        printWriterAddTest("UpadteSessionIDTest", "U.T4");
        TestRoles.add(TestRole);
        String sessionID = "5";
        String sessionDate = "5";
        try {
            assertTrue(DAOUserService.createByEMail(TestEmail, TestName, TestPasword, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
        } catch (Exception e) {
            printWriterAddFailure("User was not created");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.updateSessionIdById(DAOUserService.getIdByMail(TestEmail), sessionID, sessionDate));
        } catch (Exception e) {
            printWriterAddFailure("did not update");
            throw new AssertionError(e);
        }
        try{
            assertEquals(DAOUserService.getByIdPlusRoles(DAOUserService.getIdByMail(TestEmail)).getSessionId(),sessionID);
        } catch (Exception e) {
            printWriterAddFailure("did not update sessionID");
            throw new AssertionError(e);
        }
        try{
            assertEquals(DAOUserService.getByIdPlusRoles(DAOUserService.getIdByMail(TestEmail)).getSessionDate(),sessionDate);
        } catch (Exception e) {
            printWriterAddFailure("did not update sessionDate");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.deleteById(DAOUserService.getIdByMail(TestEmail)));
        } catch (Exception e) {
            printWriterAddFailure("User was not deleted");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }

    /* Author: Marvin Prüger
     * Function: SessionID Test
     * Reason:
     * UserStory/Task-ID: S7.D6
     */
    @Test 
    void EmptySessionIDTest(){
        printWriterAddTest("EmptySessionIDTest", "U.T5");
        TestRoles.add(TestRole);
        String sessionID = "5";
        String sessionDate = "5";
        try {
            assertTrue(DAOUserService.createByEMail(TestEmail, TestName, TestPasword, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
        } catch (Exception e) {
            printWriterAddFailure("User was not created");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.updateSessionIdById(DAOUserService.getIdByMail(TestEmail), sessionID, sessionDate));
        } catch (Exception e) {
            printWriterAddFailure("did not update");
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserService.emptySessionIdById(DAOUserService.getIdByMail(TestEmail)));
        } catch (Exception e) {
            printWriterAddFailure("did not Empty");
            throw new AssertionError(e);
        }
        try{
            assertEquals(DAOUserService.getByIdPlusRoles(DAOUserService.getIdByMail(TestEmail)).getSessionId(), null);
        } catch (Exception e) {
            printWriterAddFailure("did not remove sessionID");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.deleteById(DAOUserService.getIdByMail(TestEmail)));
        } catch (Exception e) {
            printWriterAddFailure("User was not deleted");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }
        /* Author: Marvin Prüger
     * Function: SessionID Test
     * Reason:
     * UserStory/Task-ID: S7.D7
     */
    @Test 
    void CheckSessionIDTest(){
        printWriterAddTest("CheckSessionIDTest", "U.T6");
        TestRoles.add(TestRole);
        String sessionID = "5";
        String sessionDate = "5";
        try {
            assertTrue(DAOUserService.createByEMail(TestEmail, TestName, TestPasword, TestPrivatDescription, TestWorkDescription, TestRoles, null, null, false));
        } catch (Exception e) {
            printWriterAddFailure("User was not created");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.updateSessionIdById(DAOUserService.getIdByMail(TestEmail), sessionID, sessionDate));
        } catch (Exception e) {
            printWriterAddFailure("did not update");
            throw new AssertionError(e);
        }
        try{
            assertTrue(DAOUserService.checkSessionId("5"));
            assertTrue(DAOUserService.checkSessionId("5"));
        } catch (Exception e) {
            printWriterAddFailure("is not the same");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.deleteById(DAOUserService.getIdByMail(TestEmail)));
        } catch (Exception e) {
            printWriterAddFailure("User was not deleted");
            throw new AssertionError(e);
        }
        printWriterAddPass();
    }



    // @Test
    // void UserGeneralTest(){
    //     printWriterAddTest("UserGeneral", null);

    //     printWriterAddPass();
    // }
}
