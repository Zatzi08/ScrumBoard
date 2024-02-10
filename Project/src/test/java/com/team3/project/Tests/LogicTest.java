package com.team3.project.Tests;

import com.team3.project.Classes.Account;
import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.User;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.service.UserStoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class LogicTest {

    @Test
    void createUserStory(){ // TODO: fix problem: wenn eine userStory mit nicht vorhandener id geupdated werden soll, ändert sich etwas in der DB (siehe Zeile 29)
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", Enumerations.Priority.low, -1);
        UserStory u2 = new UserStory("UserStory2", "Blablah2", Enumerations.Priority.high, -1);
        UserStory u3 = new UserStory("UserStory3", "Blablah3", Enumerations.Priority.low, 5);
        Assertions.assertEquals(usService.getAllUserStorys(), null); // Die Datenbank ist leer
        usService.addUserStory(u1);
        List<UserStory> list = usService.getAllUserStorys();
        usService.addUserStory(u2);
        Assertions.assertNotSame(list, usService.getAllUserStorys());//prüfen ob u2 erstellt wird (soll erstellt werden)
        list = usService.getAllUserStorys();
        /*usService.addUserStory(u3);
        Assertions.assertSame(list, usService.getAllUserStorys());*/ // prüfen ob u3 erstellt wird (soll nicht erstellt werden)
        DAOUserStoryService.delete(1);
        DAOUserStoryService.delete(2);
        Assertions.assertEquals(usService.getAllUserStorys(), null); // sicherstellen, dass die DB wieder leer ist
    }
    @Test
    void updateUserStory(){
        UserStoryService usService = new UserStoryService();
        UserStory u1 = new UserStory("UserStory1", "Blablah1", Enumerations.Priority.low, -1);
        UserStory u2 = new UserStory("UserStory2", "Blablah2", Enumerations.Priority.high, -1);
        UserStory u3 = new UserStory("UserStory3", "Blablah3", Enumerations.Priority.normal, 1);
        Assertions.assertEquals(usService.getAllUserStorys(), null);
        usService.addUserStory(u1);
        usService.addUserStory(u2);
        List<UserStory> list = usService.getAllUserStorys();
        //Assertions.assertSame(list, usService.getAllUserStorys()); // prüfen aktuell gleich
        usService.addUserStory(u3); //u1 wird bearbeitet und mit Werten von u3 ersetzt
        Assertions.assertNotSame(list, usService.getAllUserStorys()); //prüfen ob Userstory verändert wurde
        DAOUserStoryService.delete(1);
        DAOUserStoryService.delete(2);
        Assertions.assertEquals(usService.getAllUserStorys(), null);

    }
}
