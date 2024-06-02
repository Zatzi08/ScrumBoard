package com.team3.project.Tests;

import com.team3.project.Classes.*;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAOService.*;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.*;
import org.junit.jupiter.api.*;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class LogicTest extends BaseLogicTest{

    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    @BeforeAll
    public static void setupTest(){
        setup();
        pw = printWriter;
        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
    }

    @BeforeEach
    public void beforeTest(){
        before();
    }

    @AfterEach
    public void afterTest(){
        after();
    }



    @AfterAll
    public static void closeWriter(){
        pw.close();
        tearDown();
    }

    @Test
    /*  Test ID: Logic.T1
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: erstellen einer User-Story U3.B1
     */
    void createUserStory(){ //
        pw.append("Logik-Test-createUserStory\nTest ID: Logic.T1\nDate: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("null", "Blablah1", 2, -1);
        UserStory ufailure = null;
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        try {
            usService.saveUserStory(u1);
        } catch (Exception e){
            e.printStackTrace();
        }

        u1 = usService.getUserStoryByName(u1.getName());

        try {
            Assertions.assertEquals(u1.getDescription(), usService.getUserStory(u1.getID()).getDescription());
        } catch (AssertionError|Exception e){
            pw.append("Fail: created User-Story not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try {
            count_correct_exception_checks++;
            usService.saveUserStory(ufailure);
        } catch (Exception e){
            count_correct_exceptions ++;
        }

        try {
            count_correct_exception_checks++;
            ufailure = new UserStory(null, "Failure", 1, -1);
            usService.saveUserStory(ufailure);
        } catch (Exception e){
            count_correct_exceptions ++;
        }

        try {
            count_correct_exception_checks++;
            ufailure.setDescription(null);
            ufailure.setName("Fail");
            usService.saveUserStory(ufailure);
        } catch (Exception e){
            count_correct_exceptions ++;
        }

        try{
            usService.deleteUserStoryAndLinkedTasks(u1.getID());
        }catch (Exception e){
            e.printStackTrace();
        }

        if (count_correct_exceptions != count_correct_exception_checks){
            pass = false;
            pw.append("Fail: Wrong Exception-Handling\n");
        }
        pw.append(String.format("pass: %b", pass));
    }

    @Test
    /*  Test ID: Logic.T2
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: ändern einer User-Story U4.B1
     */
    void updateUserStory(){
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T2\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", 1, -1);
        String newdescription = "Blah";
        try {
            usService.saveUserStory(u1);
        } catch (Exception e){
            e.printStackTrace();
        }

        u1 = usService.getUserStoryByName(u1.getName());
        u1.setDescription(newdescription);

        try{
            usService.saveUserStory(u1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(u1.getDescription(), DAOUserStoryService.getById(u1.getID()).getDescription());
        }catch (AssertionError | Exception e){
            pass = false;
            pw.append("Fail: UserStory not updated");
            throw new AssertionError(e);
        }

        try{
            usService.deleteUserStoryAndLinkedTasks(u1.getID());
        }catch (Exception e){
            e.printStackTrace();
        }

        pw.append(String.format("pass: %b", pass));
    }

    @Test
    /*  Test ID: Logic.T3
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Profile erstellen P1.B1
     */
    void createProfile(){
        pw.append("Logik-Test-createProfile\nTest ID: Logic.T3\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        String sessionID = null;
        Profile profile = new Profile(0,"Dave", "dave@gmail.com", "Manager","slow thinker" ,null, 1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        try{
            aservice.register("Dave","dave@gmail.com", "123");
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            sessionID = wservice.getSessionID("dave@gmail.com");
        }catch (Exception e){
            e.printStackTrace();
        }

        int uid = DAOUserService.getIdByMail("dave@gmail.com");


        try{
            aservice.savePublicData(sessionID, profile);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotNull(aservice.getProfileByEmail("dave@gmail.com"));
        }catch(AssertionError | Exception e){
            pw.append("Fail: existent User-Profile not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            count_correct_exception_checks++;
            aservice.savePublicData(null, profile);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.savePublicData("Fake-sessionID", profile);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            profile.setUname(null);
            aservice.savePublicData(sessionID, profile);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            profile.setUname("Dave");
            profile.setWorkDesc(null);
            aservice.savePublicData(sessionID, profile);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            profile.setWorkDesc("working");
            profile.setPrivatDesc(null);
            aservice.savePublicData(sessionID, profile);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        DAOAccountService.deleteByMail("dave@gmail.com");

        if(count_correct_exception_checks != count_correct_exceptions){
            pass = false;
            pw.append("Fail: Wrong Exception-Handling\n");
        }

        pw.append(String.format("pass: %b", pass));
    }

    @Test
    /*  Test ID: Logic.T4
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Profile aktualisieren P2.B1
     */
    void updateProfile(){
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T4\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        Profile profile = new Profile(0,"Dave", "dave@gmx.de","netter Dave", "arbeitender Dave", null, 1);
        String newdescription = "böse";

        try{
            aservice.register("Dave", "dave@gmx.de", "passw");
        }catch (Exception e){
            e.printStackTrace();
        }

        String sessionID = null;

        try{
            sessionID = wservice.getSessionID("dave@gmx.de");
        }catch (Exception e){
            e.printStackTrace();
        }

        int uid = DAOUserService.getIdByMail("dave@gmx.de");

        try{
            aservice.savePublicData(sessionID,profile);
        }catch (Exception e){
            e.printStackTrace();
        }

        profile.setPrivatDesc(newdescription);

        try{
            aservice.savePublicData(sessionID,profile);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(aservice.getProfileByEmail("dave@gmx.de").getPrivatDesc(), newdescription);
        }catch (AssertionError | Exception e){
            pw.append("Fail: User-Profile was not updated\n");
            pass = false;
            throw new AssertionError(e);
        }

        DAOUserService.deleteById(uid);

        pw.append(String.format("pass = %b \n",pass));
    }

    @Test
    /*  Test ID: Logic.T5
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: User Story löschen U6.B1
     */

    void deleteUserStory(){
        pw.append("Logik-Test-deleteUserStory\nTest ID: Logic.T5\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", 1, -1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        try{
            usService.saveUserStory(u1);
        }catch(Exception e){
            e.printStackTrace();
        }

        u1 = usService.getUserStoryByName(u1.getName());

        try{
            count_correct_exception_checks++;
            usService.deleteUserStoryAndLinkedTasks(-1);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            usService.deleteUserStoryAndLinkedTasks(u1.getID());
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertFalse(DAOUserStoryService.checkId(u1.getID()));
        }catch (AssertionError e){
            pw.append("Fail: deleted User Story found\n");
            pass = false;
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: Wrong Exception-Handling\n");
            pass = false;
        }

        pw.append(String.format("pass = %b\n", pass));
    }

    @Test
    /*  Test ID: Logic.T6
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Task erstellen T3.B1
     */
    void createTask(){
        pw.append("Logik-Test-createTask\nTest ID: Logic.T6\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService uservice = new UserStoryService();
        TaskService tservice = new TaskService();
        UserStory u1 = new UserStory("Story1", "Blah1", 1, -1);
        Task t1 = null;

        try{
            uservice.saveUserStory(u1);
        }catch (Exception e){
            e.printStackTrace();
        }

        u1 = uservice.getUserStoryByName(u1.getName());

        try{
            t1 = new Task(-1, "Task1", 0, u1.getID(), "2030-10-10 10:10", 10, 50 , -1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            tservice.saveTask(t1);
        }catch (Exception e){
            e.printStackTrace();
        }

        int t1ID = -10;

        try{
            t1ID = tservice.getTaskByDescription(t1.getDescription()).getID();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
           Assertions.assertEquals(t1.getPriority(), tservice.getTaskByID(t1ID).getPriority());
        }catch(AssertionError | Exception  e  ){
            pw.append("Fail: created Task not found\n");
            pass = false;
            throw new AssertionError(e);
        }
        try {
            uservice.deleteUserStoryAndLinkedTasks(u1.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }


        pw.append(String.format("pass = %b\n", pass));
    }
    @Test
    /*  Test ID: Logic.T7
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Task editieren T4.B1
     */
    void editTask(){
        pw.append("Logik-Test-editTask\nTest ID: Logic.T7\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService uservice = new UserStoryService();
        TaskService tservice = new TaskService();
        UserStory u1 = new UserStory("UStory1", "blaBla1", 1, -1);
        String newdescription = "newblaBla1";
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;


        try{
            uservice.saveUserStory(u1);
        }catch (Exception e){
            e.printStackTrace();
        }

        u1 = uservice.getUserStoryByName(u1.getName());
        Task t1 = null;

        try{
            t1 = new Task(-1,"Task1", 0, u1.getID(),"2030-10-10 10:10", 10, 20,-1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            tservice.saveTask(t1);
        }catch (Exception e){
            e.printStackTrace();
        }

        int t1ID = -10;

        try{
            t1ID = tservice.getTaskByDescription(t1.getDescription()).getID();
        }catch (Exception e){
            e.printStackTrace();
        }

        Task tCopy = null;

        try{
            tCopy = new Task(t1ID, newdescription, t1.getPriorityAsInt(), t1.getUserStoryID(), t1.getDueDateAsString(), t1.getTimeNeededG(), t1.getTimeNeededA(),t1.getTbID());
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            tservice.saveTask(tCopy);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            DAOTask daoTask = DAOTaskService.getById(tCopy.getID());
            Assertions.assertEquals(newdescription,daoTask.getDescription());
        }catch(AssertionError e){
            pw.append("Fail: updated Task-description not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        Task tfailure = null;

        try{
            count_correct_exception_checks++;
            tservice.saveTask(tfailure);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            tfailure = new Task(1023426785, "Failure", 1, u1.getID(),"2024-05-20 10:10", 10, 20, -1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            count_correct_exception_checks++;
            tservice.saveTask(tfailure);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            tfailure.setDescription(null);
            tservice.saveTask(tfailure);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            tfailure.setDescription("Task-Failure");
            tfailure.setUserStoryID(-10);
            tservice.saveTask(tfailure);
        }catch (Exception e){
            count_correct_exceptions++;
        }


        /*
        try{
            count_correct_exception_checks++;
            tfailure.setUserStoryID(usid);
            tfailure.setdueDate("2010-10-10 10:10"); //Date in der Vergangenheit soll exception thrown
            tservice.saveTask(tfailure);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            tfailure.setdueDate("2030-10-10 10:10);
            tfailure.settimeNeededA(-1); //timeNeeded kann nicht negativ sein
            tservice.saveTask(tfailure);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            tfailure.settimeNeededA(10);
            tfailure.settimeNeededG(-1); // timeNeeded kann nicht negativ sein
            tservice.saveTask(tfailure);
        }catch (Exception e){
            count_correct_exceptions++;
        }
        */

        try{
            uservice.deleteUserStoryAndLinkedTasks(u1.getID());
        }catch (Exception e){
            e.printStackTrace();
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: wrong Exception-Handling\n");
            pass = false;
        }

        pw.append(String.format("pass = %b\n",pass));
    }

    @Test
    /*  Test ID: Logic.T8
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Task löschen T5.B1
     */
    void deleteTask(){
        pw.append("Logik-Test-deleteTask\nTest ID: Logic.T8\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService uservice = new UserStoryService();
        TaskService tservice = new TaskService();
        UserStory u1 = new UserStory("StoryName", "BlahBlah", 1, -1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;


        try{
            uservice.saveUserStory(u1);
        }catch (Exception e){
            e.printStackTrace();
        }

        u1 = uservice.getUserStoryByName(u1.getName());
        Task t1 = null;

        try{
            t1 = new Task(-1,"TaskDescription",0,u1.getID(), "2030-10-10 10:10", 20, 50,-1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            tservice.saveTask(t1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            t1 = tservice.getTaskByDescription(t1.getDescription());
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            tservice.deleteTask(t1.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            count_correct_exception_checks++;
            tservice.deleteTask(-1);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            Assertions.assertNull(DAOTaskService.getById(t1.getID()));
        }catch(AssertionError e){
            pw.append("Fail: deleted Task found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            uservice.deleteUserStoryAndLinkedTasks(u1.getID());
        }catch (Exception e){
            e.printStackTrace();
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: wrong Exception-Handling\n");
            pass = false;
        }

        pw.append(String.format("pass = %b\n", pass));
    }

    @Test
    /*  Test ID: Logic.T9
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Registration A2.B1
     */
    void register(){
        pw.append("Logik-Test-register\nTest ID: Logic.T9\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        User u = new User("Dave", -1, List.of(Enumerations.Role.Nutzer),1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        try{
            count_correct_exception_checks++;
            aservice.register(null,"dave@gmail.com", "123");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.register("Dave",null, "123");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.register("Dave","dave@gmail.com", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            aservice.register("Dave","dave@gmail.com", "123");
        }catch (Exception e){
            e.printStackTrace();
        }

        int userID = DAOUserService.getIdByMail("dave@gmail.com");

        try{
            Assertions.assertEquals("Dave", DAOUserService.getById(userID).getName());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: User was not registered");
            throw new AssertionError(e);
        }

        DAOUserService.deleteById(userID);

        if(count_correct_exceptions != count_correct_exception_checks){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
    /*  Test ID: Logic.T10
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Login  A3.B1
     */
    void login(){
        pw.append("Logik-Test-login\nTest ID: Logic.T10\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        User u = new User("Dave", -1, List.of(Enumerations.Role.Nutzer), 1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        try{
            aservice.register("Dave", "dave@net.de", "111");
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            count_correct_exception_checks++;
            aservice.login(null, "111");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.login("dave@net.de", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            Assertions.assertEquals(true, aservice.login("dave@net.de", "111"));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pass = false;
            pw.append("Fail: existent Email not found\n");
            throw new AssertionError(e);
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b",pass));
    }

    @Test
    /*  Test ID: Logic.T11
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: initiale reelle Rechte Zuweisung
     */
    void authority(){
        pw.append("Logik-Test-authority\nTest ID: Logic.T11\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        String sessionID = null;

        try{
            aservice.register("Dave","dave@gmail.com", "123");
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            sessionID = wservice.getSessionID("dave@gmail.com");
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(1, aservice.getAuthority(sessionID));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: User created with wrong authority");
            pass = false;
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b",pass));
    }

    @Test
        /*  Test ID: Logic.T12
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: reelle Rechte ändern R1.B2
         */
    void changeAuthority() {
        pw.append("Logik-Test-changeAuthority\nTest ID: Logic.T12\n" + "Date: " + formatter.format(date) + '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        RoleService rservice = new RoleService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        String sessionID = null;
        int oldAuthority = -10;

        try{
            aservice.register("Dave", "dave@test.com", "123");
        }catch (Exception e){
            e.printStackTrace();
        }

        int uID = DAOUserService.getIdByMail("dave@test.com");

        try{
            sessionID = wservice.getSessionID("dave@test.com");
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            oldAuthority = aservice.getAuthority(sessionID);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            rservice.changeAuthority(uID, 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotEquals(oldAuthority, aservice.getAuthority(sessionID));
        }catch (Exception | AssertionError e){
            pass = false;
            pw.append("Fail: Authority was not changed\n");
            throw new AssertionError(e);
        }

        try{
            count_correct_exception_checks++;
            rservice.changeAuthority(-10,1);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.changeAuthority(uID,-10);
        }catch (Exception e){
            count_correct_exceptions++;
        }


        if (count_correct_exceptions != count_correct_exception_checks) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }

        DAOUserService.deleteById(uID);

        pw.append(String.format("pass = %b\n", pass));
    }

    @Test
        /*  Test ID: Logic.T13
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: alle visuellen Rollen einer reellen Rolle wiedergeben R2.B1
         */
    void showAllVisualRole() {//Annahme: neu hinzugefügte Elemente werden am Ende der Liste von Rollen sein
        pw.append("Logik-Test-showVisualRole\nTest ID: Logic.T13\n" + "Date: " + formatter.format(date) + '\n');
        RoleService rservice = new RoleService();
        LinkedList<Role> list = null;

        try{
            rservice.createVisualRole("Entwickler1", Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            list = rservice.getAllRolesByRole(Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotEquals("Entwickler2", list.getLast());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: non-existent visual Role found\n");
            throw new AssertionError(e);
        }

        try{
            rservice.createVisualRole("Entwickler2", Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            list = rservice.getAllRolesByRole(Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals("Entwickler2", list.getLast());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: existent visual Role not found\n");
        }

        try{
            rservice.deleteVisualRole("Entwickler1", Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            rservice.deleteVisualRole("Entwickler2", Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T14
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: visuelle Rolle erstellen R2.B2
         */
    void createVisualRole() { //Annahme: neu hinzugefügte Elemente werden am Ende der Liste von Rollen sein
        pw.append("Logik-Test-createVisualRole\nTest ID: Logic.T14\n" + "Date: " + formatter.format(date) + '\n');
        RoleService rservice = new RoleService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        try{
            rservice.createVisualRole("Entwickler", Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals("Entwickler", rservice.getAllRolesByRole(Enumerations.Role.Nutzer).getLast());
        }catch (AssertionError | Exception e){
            pass = false;
            pw.append("Fail: visual Role was not created\n");
            throw new AssertionError(e);
        }

        try{
            rservice.deleteVisualRole("Entwickler", Enumerations.Role.Nutzer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            count_correct_exception_checks++;
            rservice.createVisualRole(null, Enumerations.Role.Nutzer);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.createVisualRole("", Enumerations.Role.Nutzer);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.createVisualRole("Failure", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        if (count_correct_exceptions != count_correct_exception_checks) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b", pass));
    }

    @Test
    /*  Test ID: Logic.T15
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: visuelle Rollennamen ändern R3.B1
     */
    void editVisualRoleName(){//Annahme: Rollen in der Liste im FIFO-Prinzip eingespeichert
        pw.append("Logik-Test-changeVisualRoleName\nTest ID: Logic.T15\n" + "Date: " + formatter.format(date) + '\n');
        RoleService rservice = new RoleService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        Role oldRole = null;
        Enumerations.Role role = Enumerations.Role.Nutzer;

        try{
            rservice.createVisualRole("Developer", role);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            oldRole = rservice.getAllRolesByRole(role).getLast();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            rservice.changeVisualRoleName(oldRole.getName(),"newDeveloper",role);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotEquals(oldRole.getName(), rservice.getRoleById(oldRole.getID()).getName());
        }catch (AssertionError | Exception e){
            pass = false;
            pw.append("Fail: visual Role Name was not changed\n");
        }

        try{
            count_correct_exception_checks++;
            rservice.changeVisualRoleName(null, "Failure", role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.changeVisualRoleName("", "Failure", role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.changeVisualRoleName("Failure", null, role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.changeVisualRoleName("Failure", "", role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.changeVisualRoleName("Failure", "newFailure", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }


        if (count_correct_exceptions != count_correct_exception_checks) {
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
    void deleteVisualRole(){//Annahme: Ausgabe von getAllRoles ist im FIFO-Prinzip
        pw.append("Logik-Test-deleteVisualRole\nTest ID: Logic.T16\n" + "Date: " + formatter.format(date) + '\n');
        RoleService rservice = new RoleService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        Enumerations.Role role = Enumerations.Role.Nutzer;
        Role visualRole = null;

        try{
            rservice.createVisualRole("TestRole", role);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            visualRole = rservice.getAllRolesByRole(role).getLast();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            rservice.deleteVisualRole(visualRole.getName(), role);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotEquals(visualRole, rservice.getAllRolesByRole(role).getLast());
        }catch (AssertionError | Exception e){
            pass = false;
            pw.append("Fail: visual Role was not deleted \n");
            throw new AssertionError(e);
        }

        try{
            count_correct_exception_checks++;
            rservice.deleteVisualRole(null, Enumerations.Role.Nutzer);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.deleteVisualRole("", Enumerations.Role.Nutzer);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.deleteVisualRole("Failure", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        if (count_correct_exceptions != count_correct_exception_checks) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }
        pw.append(String.format("pass = %b", pass));

    }

    @Test
    /*  Test ID: Logic.T17
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: visuelle Rollennamen ändern R5.B2
     */
    void changeVisualRole(){
        pw.append("Logik-Test-changeVisualRole\nTest ID: Logic.T17\n" + "Date: " + formatter.format(date) + '\n');
        RoleService rservice = new RoleService();
        AccountService aservice = new AccountService();
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;
        Enumerations.Role role = Enumerations.Role.Nutzer;
        Role visualRole = null;

        try{
            aservice.register("daveT17", "daveT17@gmail.com", "T17");
        }catch (Exception e){
            e.printStackTrace();
        }

        int uID = DAOUserService.getIdByMail("daveT17@gmail.com");
        User user = new User("daveT17", uID, null, 1);

        try{
            rservice.createVisualRole("Haupt-Entwickler", role);
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO getAllRolesByRole -> Immer null ?
        /*
        try{
            visualRole = rservice.getAllRolesByRole(role).getLast();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        try{
            rservice.saveVisualRole(visualRole, user, role);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(visualRole, aservice.getProfileByEmail("daveT17@gmail.com").getRoles().getLast());
        }catch (Exception | AssertionError e){
            pass = false;
            pw.append("Fail: visual Role was not attributed to User\n");
            throw new AssertionError(e);
        }

        DAOUserService.deleteById(uID);

        try{
            rservice.deleteVisualRole(visualRole.getName(), role);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            count_correct_exception_checks++;
            rservice.saveVisualRole(null, user,role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.saveVisualRole(new Role(-10, "Fail"), user,role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.saveVisualRole(visualRole, null,role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;

            rservice.saveVisualRole(null, new User("daveT17", -1, null, 1),role);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            rservice.saveVisualRole(visualRole, user,null);
        }catch (Exception e){
            count_correct_exceptions++;
        }


        if (count_correct_exceptions != count_correct_exception_checks) {
            pass = false;
            pw.append("Fail: wrong Exception-Handling\n");
        }

        pw.append(String.format("pass = %b", pass));

    }

    @Test
    /*  Test ID: Logic.T18
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: alle Profile ausgeben P4.B1
     */
    void getAllProfiles(){
        pw.append("Logik-Test-getAllProfiles\nTest ID: Logic.T18\n" + "Date: " + formatter.format(date) + '\n');
        AccountService aservice = new AccountService();
        int size = 0;

        size = aservice.getAllUser().size();

        try{
            aservice.register("DaveT18", "daveT18@gmail.com", "T18");
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotEquals(size, aservice.getAllUser().size());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: did not return all Users\n");
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));

    }

    @Test
    /*  Test ID: Logic.T19
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Task-Erweiterung um "done" T6.B1
     */
    void addDoneToTask(){
        pw.append("Logik-Test-addDoneToTask\nTest ID: Logic.T19\n" + "Date: " + formatter.format(date) + '\n');


        pw.append(String.format("pass = %b", pass));
    }


    @Test
        /*  Test ID: Logic.T20
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: bearbeiten einer Task erweitern um TaskBoardID T16.B2
         */
    void editTaskBoardIDInTask(){
        pw.append("Logik-Test-editTaskBoardIDInTask\nTest ID: Logic.T20\n" + "Date: " + formatter.format(date) + '\n');
        TaskBoardService taskBoardService = new TaskBoardService();
        TaskService taskService = new TaskService();
        UserStoryService userStoryService = new UserStoryService();
        UserStory userStory = new UserStory("UserStoryT16B2", "T16B2", 1, -1);
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

        try{
            userStoryService.saveUserStory(userStory);
        }catch (Exception e){
            e.printStackTrace();
        }

        userStory.setID(userStoryService.getUserStoryByName(userStory.getName()).getID());

        try{
            taskBoardService.createTaskBoard("TaskBoard1T16B2");
        }catch (Exception e){
            e.printStackTrace();
        }

        int tb1ID = DAOTaskBoardService.getAll().get(0).getId();
        Task task = null;

        try{
            task = new Task(-1, "TaskT16B2", 1, userStory.getID(), "10-10-2030 10:10", 10, 20, tb1ID);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            taskService.saveTask(task);
        }catch (Exception e){
            e.printStackTrace();
        }

        task.setID(DAOTaskService.getByDescription(task.getDescription()).getId());

        try{
            taskBoardService.createTaskBoard("TaskBoard2T16B2");
        }catch (Exception e){
            e.printStackTrace();
        }

        int tb2ID = DAOTaskBoardService.getAll().get(1).getId();
        task.setTbID(tb2ID);

        try{
            taskService.saveTask(task);
        }catch (Exception e){
            e.printStackTrace();
        }

        DAOTaskBoardService.getById(tb2ID);
        List <DAOTaskList> taskLists = DAOTaskListService.getByTaskBoardId(tb2ID);
        DAOTaskList taskList = null;
        for(int i = 0; i < taskLists.size(); i++){
            taskList = taskLists.get(i);
            if(taskList.getSequence() == 1) break;
        }

        try{
            Assertions.assertEquals(task.getID(), taskList.getTasks().get(0).getId());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task did not switch TaskBoards\n");
            throw new AssertionError(e);
        }

        try{
            count_correct_exception_checks++;
            task.setTbID(103782342);
            taskService.saveTask(task);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        if(count_correct_exceptions != count_correct_exception_checks){
            pw.append("Fail: wrong Exception-Hanlding\n");
        }

        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T21
         *  Author: Henry Lewis Freyschmidt
         *  Zweck: erstellen einer Task erweitern um TaskBoardID + Verknüpfung an TaskList T16.B3
         */
    void createTaskWithTaskBoardID(){
        pw.append("Logik-Test-createTaskWithTaskBoardID\nTest ID: Logic.T21\n" + "Date: " + formatter.format(date) + '\n');


        pw.append(String.format("pass = %b", pass));
    }








}

/* erster Draft: Erfüllungsbedingungen für die User-Storys vom Sprint 2
* Test A4:
* A4.B1 - Sicherheitscode erstellen: trivial
* A4.B2 - Mail-Erstellung: erfüllt, wenn E-Mail mit korrekten Daten generiert wurde
* A4.B3 - Mail-Sendung: manuell??
*
* Test A5:
* A5.B1 - Passwort-Wiederherstellung: erfüllt, wenn 1. bei korrekten Angaben die Passwort-Speicherung einleitet
*                                                   2. bei falschen Angaben abbricht
*
* Test P1:
* P1.B1 - Profil-Anzeigen: erfüllt, wenn Profil-Daten des Nutzers ans Front-end geschickt werden
*
* Test P2:
* P2.B1 - Profil-Editieren: erfüllt, wenn geänderte Daten im korrekten Profil gespeichert werden
*
* Test U3:
* U3.B1 - User-Story-Erstellung: erfüllt, wenn 1. User-Story mit korrekten Daten generiert wird
*                                             2. User-Story kann nur von gewissen Rollen erstellt werden
* U3.B2 - Rechteverwaltung: erfüllt, wenn 1. Nutzern können Rollen/Rechte zugeordnet werden
*                                         2. nur gewisse Nutzer können die Rechte anderer bestimmen
*
* Test U4:
* U4.B1 - User-Story-Editieren: erfüllt, wenn 1. gewünschte Änderungen werden in der korrekten User-Story abgespeichert
*                                             2. nur der Product-Owner kann die User-Story editieren
* Test U6:
* U6.B1 - User-Story-Löschen: erfüllt, wenn 1. korrekte User-Story aus der DB gelöscht wird
*                                           2. nur der Product-Owner kann die User-Story löschen
* Test T1:
* T1.B1 - Alle Tasks Anzeigen: erfüllt, wenn alle Tasks der DB ans front-end weitergeleitet werden
*
* Test T3:
* T3.B1 - Task-Erstellen: erfüllt, wenn 1. die Task in der DB eingespeichert wird
*                                       2. Task an die korrekte User-Story verknüpft ist
* Test T4:
* T4.B1 - Task-Löschen: erfüllt, wenn   1.die gewünschte Task aus der DB entfernt wird
*                                       2. beim Löschen der verknüpften user-Story die Tasks gelöscht wird
* Test T16:
* T16.B1 - Task-Erstellen + Task-Board-Zuorndung: erfüllt, wenn betrachtete Task an gewünschte Task-Board verknüpft ist
* T.16.B2 - Task-Editieren + Task-Board-Zuordung: erfüllt, wenn gewünschte Task-Board-Zuordnung in der korrekten Task geändert wird
*
* Test TB2:
* TB2.B1 - Task-Board-Anzeigen: erfüllt, wenn gewünschtes Task-Board aus der DB geholt und ans front-end geschickt wird
 */

