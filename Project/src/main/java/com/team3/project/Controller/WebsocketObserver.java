package com.team3.project.Controller;

import com.team3.project.Classes.parseable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class WebsocketObserver {

    // 6h wasted
    // just Refactor den Controller mit @SendTo
    // oder finde raus woher du den MessageChannel bekommst

    @Autowired
    private WebsocketService service;

    @Autowired
    public WebsocketObserver(WebsocketService service){
        this.service = service;
    }

    @PostMapping("/HI")
    private void HI(){
        service.sendMessage("projectManager", 4, null);
    }

    public void sendToProjectManager(int messageTyp, parseable object){
        service.sendMessage("projectManager", messageTyp, object);
    }
}
