package com.team3.project.Tests;

import com.team3.project.Classes.*;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.service.AccountService;
import com.team3.project.service.TaskService;
import com.team3.project.service.WebSessionService;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.service.UserStoryService;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class LogicTest {

    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    @BeforeAll
    public static void setup(){
        try {
            File log = new File("src/test/java/com/team3/project/logs/log_LogicTest.txt");
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

    @BeforeEach
    public void emptyDatabase(){
        UserStoryService usService = new UserStoryService();
        TaskService taskService = new TaskService();
        AccountService accService = new AccountService();

        List<UserStory> usList = usService.getAllUserStorys();
        if(!usList.isEmpty()){
            pw.append("UserStory: not empty Database\n");
            try{
                usList.forEach(e -> {
                    try {
                        usService.deleteUserStoryAndLinkedTasks(e.getID());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        List<DAOUser> pList = DAOUserService.getAll();
        if(!pList.isEmpty()){
            pw.append("Profile: not empty Database\n");
            pList.forEach(x -> {
                try{
                    if(x != null){
                        if(x.getPrivatDescription() != null || x.getWorkDescription() != null){
                            DAOUserService.updateById(x.getUid(), x.getName(), null, null, null, x.getSessionId(), x.getSessionDate(), false);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
    }

    @AfterAll
    public static void closeWriter(){
        pw.close();
    }

    /*  Test ID: Logic.T1
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: U3.B1
     */
    @Test
    void createUserStory(){ // TODO: fix problem: wenn eine userStory mit nicht vorhandener id updated werden soll, ändert sich etwas in der DB (siehe Zeile 29)
        pw.append("Logik-Test-createUserStory\nTest ID: Logic.T1\nDate: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("null", "Blablah1", 2, -1);
        UserStory ufailure = null;
        int count_correct_exceptions = 0;
        int count_correct_exception_checks = 0;

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

    /*  Test ID: Logic.T2
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: U4.B1
     */
    @Test
    //TODO: integriere die Rolle des Product Owners; dafür fehlt DB-Ansprache zum Nutzer
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

    /*  Test ID: Logic.T3
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Profile erstellen P1.B1
     */
    @Test
    void createProfile(){
        pw.append("Logik-Test-createProfile\nTest ID: Logic.T3\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        String sessionID = null;
        Profile p1 = new Profile("Dave", "nett", "Manager");
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
            count_correct_exception_checks++;
            aservice.savePublicData(DAOUserService.getById(uid).getSessionId(), null, "", "Manager", "bin langweilig");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.savePublicData(DAOUserService.getById(uid).getSessionId(), "Dave", null, "Manager", "bin langweilig");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.savePublicData(DAOUserService.getById(uid).getSessionId(), "Dave", "", null, "bin langweilig");
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            count_correct_exception_checks++;
            aservice.savePublicData(DAOUserService.getById(uid).getSessionId(), "Dave", "", "Manager", null);
        }catch (Exception e){
            count_correct_exceptions++;
        }

        try{
            aservice.savePublicData(DAOUserService.getById(uid).getSessionId(), "Dave", "", "Manager", "bin langweilig");
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
            DAOAccountService.deleteByMail("dave@gmail.com"); //FIX: Hab hier Name statt ID genutzt, da die Delete Funktion nur auf Name funktioniert
        }catch( Exception e){
            e.printStackTrace();
        }

        if(count_correct_exception_checks != count_correct_exceptions){
            pass = false;
            pw.append("Fail: Wrong Exception-Handling\n");
        }

        pw.append(String.format("pass: %b", pass));
    }

    /*  Test ID: Logic.T4
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Profile aktualisieren P2.B1
     */
    @Test
    void updateProfile(){
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T4\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        WebSessionService wservice = new WebSessionService();
        Profile p1 = new Profile("Dave", "netter Dave", "arbeitender Dave");
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
            aservice.savePublicData(DAOUserService.getById(uid).getSessionId(),"Dave", "","Entwickler", "nett" );
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            aservice.savePublicData(DAOUserService.getById(uid).getSessionId(),"Dave", "","Entwickler", newdescription );
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(aservice.getProfileByEmail("dave@gmx.de").getUserdesc(), newdescription);
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: User-Profile-Description does not contain updated value\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            DAOUserService.deleteById(uid);
        }catch (Exception e){
            e.printStackTrace();
        }

        pw.append(String.format("pass = %b \n",pass));
    }
    /*  Test ID: Logic.T5
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: User Story löschen U6.B1
     */
    @Test
    //TODO: integriere die Rolle des Product Owners; dafür fehlt DB-Ansprache zum Nutzer

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

    /*  Test ID: Logic.T6
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Task erstellen T3.B1
     */
    @Test
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
            t1 = new Task(-1, "Task1", 0, u1.getID(), "2030-10-10 10:10", 10, 50 );
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

        try{
           Assertions.assertEquals(t1.getDueDateAsString(), tservice.getTaskByID(t1.getID()).getDueDateAsString());
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
    /*  Test ID: Logic.T7
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Task editieren T4.B1
     */
    @Test
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
            t1 = new Task(-1,"Task1", 0, u1.getID(),"2030-10-10 10:10", 10, 20);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            tservice.saveTask(t1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            t1 = tservice.getTaskByDescription("Task1");
        }catch (Exception e){
            e.printStackTrace();
        }

        t1.setDescription(newdescription);

        try{
            tservice.saveTask(t1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(newdescription,DAOTaskService.getById(t1.getID()).getDescription());
        }catch (AssertionError e){
            pass = false;
            pw.append("Fail: Task was not updated\n");
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
            tfailure = new Task(-1, null, 1, u1.getID(),"2024-05-20 10:10", 10, 20);
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
            Assertions.assertEquals(newdescription, DAOTaskService.getById(t1.getID()).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: updated Task-description not found\n");
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

        pw.append(String.format("pass = %b\n",pass));
    }

    /*  Test ID: Logic.T8
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Task löschen T5.B1
     */
    @Test
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
            t1 = new Task(-1,"TaskDescription",0,u1.getID(), "2030-10-10 10:10", 20, 50);
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
         *  Zweck: Registration ... \TODO: ... auffüllen
         */

    void register(){
        pw.append("Logik-Test-register\nTest ID: Logic.T9\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        User u = new User("Dave", -1, "BlahBlah", "workBlahBlah", Enumerations.Role.nutzer);
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
     *  Zweck: Login ... \TODO: ... auffüllen
     */

    void login(){
        pw.append("Logik-Test-login\nTest ID: Logic.T10\n" + "Date: " + formatter.format(date)+ '\n');
        AccountService aservice = new AccountService();
        User u = new User("Dave", -1, "BlahBlah", "workBlahBlah", Enumerations.Role.nutzer);
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
     *  Zweck: reelle Rechte ... \TODO: ... auffüllen
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
            Assertions.assertEquals(0, aservice.getAuthority(sessionID));
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: User created with wrong authority");
            pass = false;
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b",pass));
    }


    //TODO: Datenstruktur zur Zuodrnung von Tsks zu Taskboards fehlt
    /*@Test
    void taskboardAttribute(){
        pw.append("Logik-Test-taskboardAttribute\nTest ID: Logic.T9\n" + "Date: " + formatter.format(date)+ '\n');
        TaskService tservice = new TaskService();
        UserStoryService uservice = new UserStoryService();

        try{
            uservice.saveUserStory("UserStoryT16B1", "T16.B1",2, -1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            tservice.saveTask(-1, "TaskT16B1",0,DAOUserStoryService.getByName("UserStoryT16B1").getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO: TaskBoard erstellen; Task zuordnen; DB-Abfrage + Vergleich
        pw.append(String.format("pass = %b\n", pass));
    }*/
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

