package com.team3.project.Tests;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
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

    // TODO: Tests atomarer gestalten -> z.B. Test auf DB empty als BeforeEach und oder AfterEach statt in jedem Test

    /*  Test ID: Logic.T1
     *  Author: Henry Lewis Freyschmidt
     *  Zweck:
     */
    @Test
    void createUserStory(){ // TODO: fix problem: wenn eine userStory mit nicht vorhandener id geupdated werden soll, ändert sich etwas in der DB (siehe Zeile 29)
        pw.append("Logk-Test-createUserStory\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", Enumerations.Priority.low, -1);
        UserStory u2 = new UserStory("UserStory2", "Blablah2", Enumerations.Priority.high, -1);
        UserStory u3 = new UserStory("UserStory3", "Blablah3", Enumerations.Priority.low, 5);
        if (usService.getAllUserStorys() != null) {
            pw.append("not empty Database Userstory\n");
            pass = false;
        }
        Assertions.assertNull(usService.getAllUserStorys()); // Die Datenbank ist leer

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
            pw.append("no Userstory created\n");
            pass = false;
            throw new AssertionError(e);
        }

        list = usService.getAllUserStorys();

        /*usService.addUserStory(u3);
        Assertions.assertSame(list, usService.getAllUserStorys());*/ // prüfen ob u3 erstellt wird (soll nicht erstellt werden)
        DAOUserStoryService.delete(1);
        DAOUserStoryService.delete(2);

        if (usService.getAllUserStorys() != null) {
            pw.append("not emptied Database Userstory\n");
            pass = false;
        }
        Assertions.assertNull(usService.getAllUserStorys()); // sicherstellen, dass die DB wieder leer ist

        pw.append(String.format("pass: %b", pass));
    }

    /*  Test ID: Logic.T2
     *  Author: Henry Lewis Freyschmidt
     *  Zweck:
     */
    @Test
    void updateUserStory(){
        pw.append("Logk-Test-updateUserStory\n" + "Date: " + formatter.format(date)+ '\n');
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", Enumerations.Priority.low, -1);
        UserStory u2 = new UserStory("UserStory2", "Blablah2", Enumerations.Priority.high, -1);
        UserStory u3 = new UserStory("UserStory3", "Blablah3", Enumerations.Priority.normal, 1);
        if (usService.getAllUserStorys() != null) {
            pw.append("not empty Database Userstory\n");
            pass = false;
        }
        Assertions.assertNull(usService.getAllUserStorys());


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
            pw.append("no Userstory created\n");
            pass = false;
            throw new AssertionError(e);
        }

        DAOUserStoryService.delete(1);
        DAOUserStoryService.delete(2);

        if (usService.getAllUserStorys() != null) {
            pw.append("not emptied Database Userstory\n");
            pass = false;
        }
        Assertions.assertNull(usService.getAllUserStorys());
        pw.append(String.format("pass: %b\n\n", pass));
    }
}
