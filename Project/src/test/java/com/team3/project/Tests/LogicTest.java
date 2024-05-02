package com.team3.project.Tests;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.User;
import com.team3.project.Classes.UserStory;
import com.team3.project.Classes.Profile;
import com.team3.project.Classes.Task;
import com.team3.project.Classes.Enumerations;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.service.AccountService;
import com.team3.project.service.TaskService;
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
import java.util.concurrent.ExecutionException;

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

    @BeforeEach @AfterEach
    public void emptyDatabase(){
        UserStoryService usService = new UserStoryService();
        TaskService taskService = new TaskService();
        AccountService accService = new AccountService();

        List<UserStory> usList = usService.getAllUserStorys();
        if(usList != null){
            pw.append("UserStory: not empty Database\n");
            try{
                usList.forEach(e -> {
                    try {
                        usService.deleteUserStory(e.getID());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        List<Task> taskList = taskService.getAllTask();
        if(taskList != null){
            pw.append("Task: not empty Database\n");
            taskList.forEach(x -> {
                try {
                    taskService.deleteTask(x.getID());
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        }

        List<DAOUser> pList = DAOUserService.getAll();
        while(pList != null){
            if(pList.get(0).getPrivatDescription()!=null || pList.get(0).getWorkDescription()!=null){
                pw.append("Profile: not empty Database\n");
                DAOUserService.updateById(pList.get(0).getUid(), pList.get(0).getName(),"","",null,pList.get(0).getSessionId(), pList.get(0).getSessionDate(), false);
                pList.remove(pList.get(0));
            }
        }
    }

    @AfterAll
    public static void closeWriter(){
        pw.close();
    }

    // TODO: Tests atomarer gestalten -> z.B. Test auf DB empty als BeforeEach und oder AfterEach statt in jedem Test

    /*  Test ID: Logic.T1
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: U3.B1
     */
    @Test
    //TODO: integriere die Rolle des Product Owners; dafür fehlt DB-Ansprache zum Nutzer
    void createUserStory(){ // TODO: fix problem: wenn eine userStory mit nicht vorhandener id updated werden soll, ändert sich etwas in der DB (siehe Zeile 29)
        pw.append("Logik-Test-createUserStory\nTest ID: Logic.T1\nDate: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();

        try {
            usService.saveUserStory("UserStory1", "Blablah1", 1, -1);
        } catch (Exception e){
            e.printStackTrace();
        }

        List<UserStory> list = usService.getAllUserStorys();

        try {
            usService.saveUserStory("UserStory2", "Blablah2", 3, -1);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            Assertions.assertNotSame(list, usService.getAllUserStorys());
        } catch (AssertionError e){
            pw.append("Fail: no Userstory created\n");
            pass = false;
            throw new AssertionError(e);
        }

        DAOUserStoryService.deleteById(1);
        DAOUserStoryService.deleteById(2);
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

        try {
            usService.saveUserStory("UserStory1", "Blablah1", 1, -1);
            usService.saveUserStory("UserStory2", "Blablah2", 3, -1);
        } catch (Exception e){
            e.printStackTrace();
        }
        List<UserStory> list = usService.getAllUserStorys();
        //Assertions.assertSame(list, usService.getAllUserStorys()); // prüfen aktuell gleich
        try {
            usService.saveUserStory("UserStory3", "Blablah3", 2, 1); //u1 wird bearbeitet und mit Werten von u3 ersetzt
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Assertions.assertNotSame(list, usService.getAllUserStorys());//prüfen ob Userstory verändert wurde
        } catch (AssertionError e){
            pw.append("Fail: no Userstory created\n");
            pass = false;
            throw new AssertionError(e);
        }

        DAOUserStoryService.deleteById(1);
        DAOUserStoryService.deleteById(2);

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

        try{
            aservice.createUser("dave@gmail.com", "123","Dave","","");
        }catch (Exception e){
            e.printStackTrace();
        }

        int uid = DAOUserService.getIdByMail("dave@gmail.com");

        try{
            Assertions.assertNull(aservice.getProfileByEmail("dave@gmail.com"));
        }catch(AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: non-existent User-Profile-description found\n");
            pass = false;
            throw new AssertionError(e);
        }
        //TODO: sessionID, sessionDate sollen nicht null sein. Woher kann ich SessionDate kriegen?
        try{
            DAOUserService.updateById(uid, "Dave", "bin langweilig", "Manager", null,DAOUserService.getById(uid).getSessionId(), DAOUserService.getById(uid).getSessionDate(), true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotNull(aservice.getProfileByEmail("dave@gmail.com"));
        }catch(AssertionError | Exception e){
            pw.append("Fail: existent User-Profile-description not found\n");
            pass = false;
            throw new AssertionError(e);
        }
        try{
            DAOAccountService.deleteByMail("dave@gmail.com"); //FIX: Hab hier Name statt ID genutzt, da die Delete Funktion nur auf Name funktioniert
        }catch( Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNull(aservice.getProfileByEmail("dave@gmail.com"));
        }catch(AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: non-existent User-Profile-description found\n");
            pass = false;
            throw new AssertionError(e);
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
        String newdescription = "new Manager";

        try{
            aservice.createUser("dave@gmx.de", "passw","Dave" ,"","");
        }catch (Exception e){
            e.printStackTrace();
        }

        int uid = DAOUserService.getIdByMail("dave@gmx.de");
        String sessionID = DAOUserService.getById(uid).getSessionId();

        try{
            DAOUserService.updateById(uid, "Dave", "Langweiler", "nett", null, sessionID, DAOUserService.getBySessionId(sessionID).getSessionDate(), false);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotEquals(aservice.getProfileByEmail("dave@gmx.de").getUserdesc(), newdescription);
        }catch (AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: User-Profile desciption is wrong\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            DAOUserService.updateById(uid, "Dave", newdescription, "nett", null, sessionID, DAOUserService.getBySessionId(sessionID).getSessionDate(), false);
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
        pw.append(String.format("pass = %b",pass));
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

        try{
            Assertions.assertEquals(DAOUserStoryService.getByName("UserStory1").getDescription(), "Blablah1");
        }catch (AssertionError e){
            pw.append("Fail: non-existent User-Story found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            usService.saveUserStory("UserStory1", "Blablah1", 1, -1);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(DAOUserStoryService.getByName("UserStory1").getDescription(), "Blablah1");
        }catch (AssertionError e){
            pw.append("Fail: existent User-Story not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        int u1id = DAOUserStoryService.getByName("UserStory1").getId();

        try{
            usService.deleteUserStory(u1id);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertFalse(DAOUserStoryService.checkId(u1id));
        }catch (AssertionError e){
            pw.append("Fail: deleted User Story found\n");
            pass = false;
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b", pass));
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
        try{
            uservice.saveUserStory("Story1", "Blah1", 1, -1);
        }catch (Exception e){
            e.printStackTrace();
        }
        int uid = DAOUserStoryService.getByName("Story1").getId();

        try{
            //Assertions.assertNotEquals("Task1", DAOUserStoryService.getTaskByID(t1.getID()).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: non-existent Task found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try {
            tservice.saveTask(-1, "Task1", 0, uid);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
           //Assertions.assertEquals(t1.getDescription(), tservice.getTaskByName("Task1").getDescription());
        }catch(AssertionError e){
            pw.append("Fail: existent Task not found\n");
            pass = false;
            throw new AssertionError(e);
        }
        try {
            uservice.deleteUserStory(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Voraussetzung: wenn eine User-Story gelöscht wird, werden dazugehörige Tasks gelöscht
        try{
            Assertions.assertNotEquals("Task1", tservice.getTaskByDescription("Task1").getDescription());
        }catch(AssertionError | Exception e){
            e.printStackTrace();
            pw.append("Fail: non-existent Task found\n");
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
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

        try {
            Assertions.assertNull(DAOUserStoryService.getByName("UStory1").getDescription());
        }catch (AssertionError e){
            pw.append("Fail: non-existent User-Story found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            uservice.saveUserStory("UStory1", "blaBla1", 1, -1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Assertions.assertEquals(DAOUserStoryService.getByName("UStory1").getDescription(), u1.getDescription());
        }catch (AssertionError e){
            pw.append("Fail: valid User-Story not created\n");
            pass = false;
            throw new AssertionError(e);
        }
        int uid =  DAOUserStoryService.getByName("UStory1").getId();

        try{
            //Assertions.assertNull(DAOUserStoryService.getTaskByID(t1.getID()).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: non-existent Task found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            tservice.saveTask(-1,"Task1", 0, uid);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            //Assertions.assertEquals(t1.getDescription(), DAOUserStoryService.getTaskByID(t1.getID()).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: existent Task not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        //TODO: Task ohne TaskID finden
        try{
            //tservice.saveTask(tid, newdescription, 0,uid);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            //Assertions.assertEquals(t1.getDescription(), DAOUserStoryService.getTaskByID(t1.getID()).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: updated Task not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        pw.append(String.format("pass = %b",pass));
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

        try{
            uservice.saveUserStory("StoryName", "BlahBlah", 1, -1);
        }catch (Exception e){
            e.printStackTrace();
        }

        int uid = DAOUserStoryService.getByName("StoryName").getId();

        try{
            tservice.saveTask(-1,"TaskDescription",0,uid);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            tservice.saveTask(-1,"TaskDescription2",0,uid);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            //Assertions.assertEquals(t1.getDescription(), DAOUserStoryService.getTaskByID(t1.getID()).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: existent Task not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        int tID = 0;

        try{
            tID = tservice.getTaskByDescription("TaskDescription").getID();
            tservice.deleteTask(tID);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            //Assertions.assertEquals(t1.getDescription(), tservice.getTaskByID(tID).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: deleted Task found\n");
            pass = false;
            throw new AssertionError(e);
        }
        try {
            uservice.deleteUserStory(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            //Assertions.assertEquals(t2.getDescription(), DAOUserStoryService.getTaskByID(t2.getID()).getDescription());
        }catch(AssertionError e){
            pw.append("Fail: deleted Task found\n");
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    /*  Test ID: Logic.T9
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Tasklisten-Zuordnung T16.B1&B2
     */
    @Test
    //TODO: Datenstruktur zur Zuodrnung von Tsks zu Taskboards fehlt
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

