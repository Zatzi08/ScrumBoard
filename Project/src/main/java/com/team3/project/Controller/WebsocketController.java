package com.team3.project.Controller;

import com.team3.project.Classes.Response;
import com.team3.project.Classes.parseable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import com.team3.project.Classes.Enumerations;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;


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
