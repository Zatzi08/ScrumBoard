package com.team3.project.Tests.datenbankTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team3.project.DAO.DAORole;
import com.team3.project.DAOService.DAORoleService;
import com.team3.project.DAOService.DAOUserService;

import com.team3.project.Tests.BaseClassesForTests.BaseDBTest;

public class RoleTest extends BaseDBTest {
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

    private String TestEmail = "TestEmail";
    private String TestPasword = "TestPasword";
    private String TestRole = "dev";
    private String TestRole1 = "admin";
    private List<DAORole> TestRoles = new ArrayList<>();

    @Test
    /* Author: Marvin Prüger
     * Function: base Role Test
     * Reason:
     * UserStory/Task-ID: von R1 bis R5 ;)
     */
    void baseRoleTest() {
        printWriterAddTest("baseRoleTest", "R.T1");
        DAOUserService.createByEMail(TestEmail, TestPasword, null, null, null, null, null, null, null, false);
        DAORoleService.create(TestRole, 1);
        try {
            assertEquals(DAOUserService.getByMail(TestEmail).getAuthorization().getAuthorization(),1);
            assertTrue(DAOUserService.getWithRolesById(DAOUserService.getIdByMail(TestEmail)).getRoles().isEmpty());
        } catch (Exception e) {
            printWriterAddFailure("does not have right authorization/role");
            throw new AssertionError(e);
        }

        TestRoles.add(DAORoleService.getByName(TestRole));
        try {
            assertTrue(DAOUserService.updateRolesById(DAOUserService.getIdByMail(TestEmail), TestRoles));
            assertEquals(TestRole, DAOUserService.getWithRolesById(DAOUserService.getIdByMail(TestEmail)).getRoles().get(0).getName());
        } catch (Exception e) {
            printWriterAddFailure("did not add role");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAORoleService.updateNameById(DAORoleService.getByName(TestRole).getId(),TestRole1));
            assertEquals(TestRole1, DAOUserService.getWithRolesById(DAOUserService.getIdByMail(TestEmail)).getRoles().get(0).getName());
        } catch (Exception e) {
            printWriterAddFailure("update role name dose not work");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAORoleService.deleteById(DAORoleService.getByName(TestRole1).getId()));
            assertTrue(DAOUserService.getWithRolesById(DAOUserService.getIdByMail(TestEmail)).getRoles().isEmpty());
        } catch (Exception e) {
            printWriterAddFailure("can not delete role");
            throw new AssertionError(e);
        }
        TestRoles.clear();

    }

    @Test
        /* Author: Marvin Prüger
     * Function: test Athorication (here because i dont want to make a extra file¯\_(ツ)_/¯)
     * Reason:
     * UserStory/Task-ID: von R1 bis R5 ;)
     */
    void atuhTest(){
        printWriterAddTest("atuhTest", "R.T2");
        DAOUserService.createByEMail(TestEmail, TestPasword, null, null, null, null, null, null, null, false);
        DAORoleService.create(TestRole1, 4);
        
        
        try {
            assertFalse(DAOUserService.updateRolesById(DAOUserService.getIdByMail(TestEmail), DAORoleService.getByAuthorization(4)));
            assertNotNull(DAORoleService.getByAuthorization(4));
        } catch (Exception e) {
            printWriterAddFailure("athorisation exists and works in context of roles");
            throw new AssertionError(e);
        }
        try {
            assertTrue(DAOUserService.updateAuthorizationById(DAOUserService.getIdByMail(TestEmail), 4));
            assertTrue(DAOUserService.updateRolesById(DAOUserService.getIdByMail(TestEmail), DAORoleService.getByAuthorization(4)));
        } catch (Exception e) {
            printWriterAddFailure("athorisation can be changed");
            throw new AssertionError(e);
        }
        DAORoleService.create(TestRole, 4);
        TestRoles.add(DAORoleService.getByName(TestRole));
        TestRoles.add(DAORoleService.getByName(TestRole1));
        try {
            assertEquals(DAORoleService.getByAuthorization(4).size(),TestRoles.size());
        } catch (Exception e) {
            printWriterAddFailure("athorisation and roles are not linked");
            throw new AssertionError(e);
        }
    }
}
