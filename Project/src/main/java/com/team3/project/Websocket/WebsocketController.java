package com.team3.project.Websocket;

import com.team3.project.Classes.UserStory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import com.team3.project.Classes.Enumerations;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class WebsocketController {

    private Enumerations enumerations = new Enumerations();

    @MessageMapping("/UserStory")
    @SendTo("/topic/ProjectManagerStorys")
    public String ProjectManagerStorys( String name) {
        saveStringAsUserstory(name);
        return "true";
    }

    private void saveStringAsUserstory(String message) {
        String name = message.substring(message.indexOf(":")+1,message.indexOf(","));
        String description = message.substring(message.indexOf(":", message.indexOf(":")+1)+1,message.indexOf(",", message.indexOf(",")+1));
        String priority = message.substring(message.indexOf(":",message.indexOf(":",message.indexOf(":")+1)+1)+1);
        UserStory userStory = new UserStory(-1, name, description, enumerations.StringToPriority(priority));
        // saveUser(userstory)
        // TODO: Implement saveUserstory
    }
}
