package com.team3.project.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class WebsocketController {

    // Funktion zum Testen
    @MessageMapping({"/ping"})
    @SendTo("/topic/projectManager")
    public ResponseEntity ping() {
            System.out.println("ping");
        return new ResponseEntity("pong", HttpStatus.OK);
    }
}
