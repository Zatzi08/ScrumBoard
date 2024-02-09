package com.team3.project.Websocket;

import com.team3.project.Classes.UserStory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import com.team3.project.Classes.Enumerations;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;


@Controller
public class WebsocketController {

    private final Enumerations enumerations = new Enumerations();

    @MessageMapping("/UserStory")
    @SendTo("/topic/ProjectManagerStorys")
    public String ProjectManagerStorys( String name) {
        try {
            saveStringAsUserstory(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "true";
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Typumwandlung von String auf UserStory
     * Grund: Frontend schickt nur String
     * UserStory/Task-ID: /
     */
    private void saveStringAsUserstory(String message) throws IOException {
        String name = message.substring(message.indexOf(":")+1,message.indexOf(","));
        String description = message.substring(message.indexOf(":", message.indexOf(":")+1)+1,message.indexOf(",", message.indexOf(",")+1));
        String priority = message.substring(message.indexOf(":",message.indexOf(":",message.indexOf(":")+1)+1)+1);
        String id = "0";
        UserStory userStory = new UserStory( name, description, enumerations.StringToPriority(priority), id);
        // saveUser(userstory)
        // TODO: Implement saveUserstory
    }
}
