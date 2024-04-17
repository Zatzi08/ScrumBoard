package com.team3.project.Tests;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.User;
import com.team3.project.Classes.UserStory;
import com.team3.project.Classes.Profile;
import com.team3.project.service.AccountService;
import com.team3.project.DAOService.DAOUserStoryService;
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

    @BeforeEach @AfterEach
    public void emptyDatabase(){
        UserStoryService usService = new UserStoryService();
        try{
            Assertions.assertNull(usService.getAllUserStorys()); // Die Datenbank ist leer
        } catch (AssertionError e){
            pw.append("not empty Database\n");
            throw new AssertionError(e);
        }
    }

    @AfterAll
    public static void closeWriter(){
        pw.close();
    }

    // TODO: Tests atomarer gestalten -> z.B. Test auf DB empty als BeforeEach und oder AfterEach statt in jedem Test

    /*  Test ID: Logic.T1
     *  Author: Henry Lewis Freyschmidt
     *  Zweck:
     */
    @Test
    void createUserStory(){ // TODO: fix problem: wenn eine userStory mit nicht vorhandener id updated werden soll, ändert sich etwas in der DB (siehe Zeile 29)
        pw.append("Logik-Test-createUserStory\nTest ID: Logic.T1\nDate: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", 1, -1);
        UserStory u2 = new UserStory("UserStory2", "Blablah2", 3, -1);
        UserStory u3 = new UserStory("UserStory3", "Blablah3", 2, 5);

        try {
            usService.addUserStory(u1);
        } catch (Exception e){
            e.printStackTrace();
        }

        List<UserStory> list = usService.getAllUserStorys();

        try {
            usService.addUserStory(u2);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            Assertions.assertNotSame(list, usService.getAllUserStorys());//prüfen ob u2 erstellt wird (soll erstellt werden)
        } catch (AssertionError e){
            pw.append("Fail: no Userstory created\n");
            pass = false;
            throw new AssertionError(e);
        }

        list = usService.getAllUserStorys();

        /*usService.addUserStory(u3);
        Assertions.assertSame(list, usService.getAllUserStorys());*/ // prüfen ob u3 erstellt wird (soll nicht erstellt werden)
        DAOUserStoryService.delete(1);
        DAOUserStoryService.delete(2);
        pw.append(String.format("pass: %b", pass));
    }

    /*  Test ID: Logic.T2
     *  Author: Henry Lewis Freyschmidt
     *  Zweck:
     */
    @Test
    void updateUserStory(){
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T2\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", 1, -1);
        UserStory u2 = new UserStory("UserStory2", "Blablah2", 3, -1);
        UserStory u3 = new UserStory("UserStory3", "Blablah3", 2, 1);

        try {
            usService.addUserStory(u1);
            usService.addUserStory(u2);
        } catch (Exception e){
            e.printStackTrace();
        }
        List<UserStory> list = usService.getAllUserStorys();
        //Assertions.assertSame(list, usService.getAllUserStorys()); // prüfen aktuell gleich
        try {
            usService.addUserStory(u3); //u1 wird bearbeitet und mit Werten von u3 ersetzt
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

        DAOUserStoryService.delete(1);
        DAOUserStoryService.delete(2);

        pw.append(String.format("pass: %b", pass));
    }
    /*  Test ID: Logic.T3
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Profile erstellen
     */
    @Test
    void createProfile(){
        pw.append("Logik-Test-createProfile\nTest ID: Logic.T3\n" + "Date: " + formatter.format(date)+ '\n');
        ProfileService pservice = new ProfileService();
        AccountService aservice = new AccountService();
        User user1 = new User("Dave", 123,"","", Enumerations.Role.manager);
        Profile profil1 = new Profile("Dave", "bin langweilig", "Manager");

        try{
            aservice.createUser(user1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNull(DAOAccountService.getByID(user1.getID()).getWorkDescription());
        }catch(AssertionError e){
            pw.append("Fail: non-existent User-Profile-description found\n");
            pass = false;
            throw new AssertionError(e);
        }

        try{
            pservice.addProfile(profil1);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotNull(DAOAccountService.getByID(user1.getID()).getPrivatDescription());
        }catch(AssertionError e){
            pw.append("Fail: existent User-Profile-description not found\n");
            pass = false;
            throw new AssertionError(e);
        }
        try{
            DAOAccountService.delete(user1.getID());
        }catch( Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNull(DAOAcountService.getByID(user1.getID()).getWorkDescription());
        }catch(AssertionError e){
            pw.append("Fail: non-existent User-Profile-description found\n");
            pass = false;
            throw new AssertionError(e);
        }

        pw.append(String.format("pass: %b", pass));
    }

    /*  Test ID: Logic.T4
     *  Author: Henry Lewis Freyschmidt
     *  Zweck: Profile aktualisieren
     */
    @Test
    void updateProfile(){
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T4\n" + "Date: " + formatter.format(date)+ '\n');
        ProfileService pservice = new ProfileService();
        AccountService aservice = new AccountService();
        User user1 = new User("Dave", 123,"","", Enumerations.Role.manager);
        Profile profil1 = new Profile("Dave", "bin langweilig", "Manager");
        String newdescription = "new Manager";

        try{
            aservice.createUser(user1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            pservice.addProfile(profil1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertNotEquals(DAOAccountService.getByName(profil1.getUname()).getDescription(), newdescription);
        }catch (AssertionError e){
            pw.append("Fail: User-Profile desciption is wrong");
            pass = false;
        }

        profil1.setDescription(newdescription);

        try{
            pservice.updateProfile(profil1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Assertions.assertEquals(DAOAccountService.getByName(profil1.getUname()).getDescription(), newdescription);
        }catch (AssertionError e){
            pw.append("Fail: User-Profile-Description does not contain updated value");
            pass = false;
        }
        pw.append(String.format("pass = %b",pass));
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
* T4.B1 - Task-Löschen: erfüllt, wenn die gewünschte Task aus der DB entfernt wird
*
* Test T16:
* T16.B1 - Task-Erstellen + Task-Board-Zuorndung: erfüllt, wenn betrachtete Task an gewünschte Task-Board verknüpft ist
* T.16.B2 - Task-Editieren + Task-Board-Zuordung: erfüllt, wenn gewünschte Task-Board-Zuordnung in der korrekten Task geändert wird
*
* Test TB2:
* TB2.B1 - Task-Board-Anzeigen: erfüllt, wenn gewünschtes Task-Board aus der DB geholt und ans front-end geschickt wird
 */

