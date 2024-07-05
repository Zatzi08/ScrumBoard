package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Profile;
import com.team3.project.Classes.Role;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.AccountService;
import com.team3.project.service.RoleService;
import com.team3.project.service.WebSessionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class VisualRoleTests extends BaseLogicTest{
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    private Enumerations.Role role = Enumerations.Role.Nutzer;
    private String visualRoleName = "Good-Entwickler";

    @Spy
    private RoleService roleService;
    @Spy
    private AccountService accountService;
    @BeforeAll
    public static void setupTest(){
        setup();
        pw = printWriter;
        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
    }

    @BeforeEach
    @AfterEach
    public void emptyDB(){
        BaseLogicTest.wipeDb(true);
    }
    @AfterAll
    public static void closeWriter(){
        pw.close();
        tearDown();
    }

    @Test
        /*  Test ID: Logic.T14
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: visuelle Rolle erstellen R2.B2
         */
    void createVisualRole_createNewVisualRole_addNewVisualRoleObjectToDataBase() throws Exception{
        //Arrange
        pw.append("Logik-Test-createVisualRole\nTest ID: Logic.T14\n" + "Date: " + formatter.format(date) + '\n');
        int count_correct_exceptions = 0;
        String name_fail = "";
        AtomicInteger count_correct_exception_checks = new AtomicInteger();

        // Exception case
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            roleService.createVisualRole(name_fail, role);
        });
        count_correct_exceptions++;

        //Act
        roleService.createVisualRole(visualRoleName,role);

        //Assert
        try{
            assertEquals(visualRoleName, roleService.getAllVisualRolesByRole(role).get(0).getName());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: visual Role not found\n");
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks.get()) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T13
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: alle visuellen Rollen einer reellen Rolle wiedergeben R2.B1
         */
    void getAllVisualRoles_getAllVisualRoles_returnListOfAllRoleObjects() throws Exception{
        //Arrange
        pw.append("Logik-Test-getAllVisualRoles\nTest ID: Logic.T13\n" + "Date: " + formatter.format(date) + '\n');
        List<Role> list;
        roleService.createVisualRole(visualRoleName,role);

        //Act
        list = roleService.getAllVisualRoles();

        //Assert
        try{
            assertEquals(visualRoleName, list.get(0).getName());
            assertEquals(role, list.get(0).getAuth());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: non-existent visual Role found\n");
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T15
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: visuelle Rollennamen ändern R3.B1
         */
    void changeVisualRoleName_changeNameOfVisualRoleObject_changeNameOfVisualRoleObajetInDataBase() throws Exception{
        //Arrange
        pw.append("Logik-Test-changeVisualRoleName\nTest ID: Logic.T15\n" + "Date: " + formatter.format(date) + '\n');
        String newName = "newName";
        String newName_fail = "";
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        roleService.createVisualRole(visualRoleName,role);
        DAORole daoRole = roleService.getAllVisualRolesByRole(role).get(0);

        //Exception case
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            roleService.changeVisualRoleName(daoRole.getId(), newName_fail);
        });
        count_correct_exceptions++;

        //Act
        roleService.changeVisualRoleName(daoRole.getId(), newName);

        //Assert
        try{
            assertEquals(newName, roleService.getRoleById(daoRole.getId()).getName());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: visual Role Name was not changed\n");
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks.get()) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b", pass));

    }

    @Test
        /*  Test ID: Logic.T16
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: visuelle Rollennamen löschen R4.B1
         */
    void deleteVisualRole() throws Exception{
        pw.append("Logik-Test-deleteVisualRole\nTest ID: Logic.T16\n" + "Date: " + formatter.format(date) + '\n');
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        int nonexistentRoleID = 1247823;
        roleService.createVisualRole(visualRoleName,role);
        DAORole daoRole = roleService.getAllVisualRolesByRole(role).get(0);

        //Exception Case
        assertThrows(Exception.class, () -> {
            count_correct_exception_checks.getAndIncrement();
            roleService.deleteVisualRoleById(nonexistentRoleID);
        });
        count_correct_exceptions++;

        //Act
        roleService.deleteVisualRoleById(daoRole.getId());

        //Assert
        try{
            assertThrows(Exception.class, () ->{roleService.getRoleById(daoRole.getId());});
        }catch (AssertionError e){
            pass = false;
            pw.append("Visual Role not deleted\n");
            throw new AssertionError(e);
        }

        if (count_correct_exceptions != count_correct_exception_checks.get()) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b", pass));

    }

    @Test
    /*  Test ID: Logic.T16
     *  Author: Henry Lewis Freyschmidt
     *  Zweck:  Nutzer kann sich eine visuelle Rolle zuweisen R5.B2
     */
    void addVisualRole_giveUserVisualRole_connectUserObjectWithRoleObjectInDataBase() throws Exception{
        //Arrange
        pw.append("Logik-Test-addVisualRole\nTest ID: Logic.T16\n" + "Date: " + formatter.format(date) + '\n');
        String userName = "Dave";
        String userMail = "dave@gmail.com";
        String userPasswort = "1234";
        int userID_fail1 = 327891;
        Role visualRole_fail1 = null;
        Role visualRole_fail2 = new Role(-1, visualRoleName);
        visualRole_fail2.setAuth(role);
        Role visualRole_fail3 = new Role(112874, visualRoleName);
        visualRole_fail3.setAuth(role);
        int count_correct_exceptions = 0;
        AtomicInteger count_correct_exception_checks = new AtomicInteger();
        accountService.register(userName,userMail, userPasswort);
        int userID = accountService.getAllUser().get(0).getID();
        roleService.createVisualRole(visualRoleName, role);
        DAORole daoRole = roleService.getAllVisualRolesByRole(role).get(0);
        Role visualRole = new Role(daoRole.getId(),visualRoleName);
        visualRole.setAuth(role);

        //Exception Cases
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            roleService.addVisualRole(userID, visualRole_fail1);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            roleService.addVisualRole(userID, visualRole_fail2);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            roleService.addVisualRole(userID_fail1, visualRole);
        });
        count_correct_exceptions++;
        assertThrows(Exception.class, () ->{
            count_correct_exception_checks.getAndIncrement();
            roleService.addVisualRole(userID, visualRole_fail3);
        });
        count_correct_exceptions++;

        //Act
        roleService.addVisualRole(userID, visualRole);

        //Assert
        try{
            assertEquals(visualRole.getID(),accountService.getProfileByEmail(userMail).getRoles().get(0).getID());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: User not linked to visual Role\n");
        }

        if(count_correct_exceptions != count_correct_exception_checks.get()){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }

        pw.append(String.format("pass = %b", pass));
    }
    @Test
    /*  Test ID: Logic.T16
     *  Author: Henry Lewis Freyschmidt
     *  Zweck:  Ausgabe alle visueller Rollen einer rechtebezogenen Rolle R2.B1
     */
    void getAllVisualRolesByRole_getListOfAllVisualRolesOfARole_returnListWithRoleObjectsOfAnAuthority() throws Exception{
        //Arrange
        pw.append("Logik-Test-getAllVisualRolesByRole\nTest ID: Logic.T16\n" + "Date: " + formatter.format(date) + '\n');
        roleService.createVisualRole(visualRoleName, role);
        List<DAORole> daoRoles;

        //Act
        daoRoles = roleService.getAllVisualRolesByRole(role);

        //Assert
        try{
            assertEquals(visualRoleName, daoRoles.get(0).getName());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: visualRole not found\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));

    }
}
