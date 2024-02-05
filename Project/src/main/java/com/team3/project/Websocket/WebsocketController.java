package com.team3.project.Websocket;

import com.team3.project.Classes.UserStory;
import com.team3.project.service.UserStoryService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    private UserStoryService userStoryService;

    @MessageMapping("/UserStory")
    @SendTo("/topic/ProjectManagerStorys")
    public UserStory ProjectManagerStorys(){
        return userStoryService.getUserStoryT();
    }
}
