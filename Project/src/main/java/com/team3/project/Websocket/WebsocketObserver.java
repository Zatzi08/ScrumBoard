package com.team3.project.Websocket;

import com.team3.project.Classes.parseable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void sendToProjectManager(int messageTyp, parseable object){
        service.sendMessage("projectManager", messageTyp, object);
    }

    public void sendToTaskGroup(int messageTyp, parseable object){

    }
}
