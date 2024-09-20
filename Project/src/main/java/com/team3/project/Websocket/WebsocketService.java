package com.team3.project.Websocket;

import com.team3.project.Classes.Response;
import com.team3.project.Classes.observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebsocketService {

    private final SimpMessagingTemplate template;

    @Autowired
    public WebsocketService(SimpMessagingTemplate simpMessagingTemplate){
        this.template = simpMessagingTemplate;
    }

    /*
        Message-Types:
            0 : new Item
            1 : deleted Item
            2 : changed primitiv Parameter - Name, Desc,...
            3 : changed complex Parameter - Liste an Namen,...
            4 : Error
     */
    void sendMessage(String topicID, int messageTyp, observable object) {
        ResponseEntity<String> response = new ResponseEntity<>(new Response(messageTyp, object).toJSON(), HttpStatus.OK);
        System.out.println(response);
        template.convertAndSend("/topic/" + topicID, response);
    }

}
