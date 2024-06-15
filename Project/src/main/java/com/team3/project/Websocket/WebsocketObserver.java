package com.team3.project.Websocket;

import com.team3.project.Classes.UserStory;
import com.team3.project.Classes.observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsocketObserver {

    // 11h wasted
    // just Refactor den Controller mit @SendTo
    // oder finde raus woher du den MessageChannel bekommst

    @Autowired
    private WebsocketService service;

    @Autowired
    public WebsocketObserver(WebsocketService service){
        this.service = service;
    }

    /*
        Message-Types:
            0 : new Item
            1 : deleted Item
            2 : changed primitiv Parameter - Name, Desc,...
            3 : changed complex Parameter - Liste an Namen,...
            4 : Error
     */

    public void sendToUserGroup(int messageTyp, observable object) {
        // TODO TB, PM-T/TzUS/N changes
        service.sendMessage("PM-N", messageTyp, object);
        service.sendMessage("PM-T", messageTyp, object);
        // kann keinen Broadcast für alle machen
        service.sendMessage("PM-TzUS", messageTyp, object);
    }

    public void sendToProfileByID(int messageTyp, observable object) {
        sendToUserGroup(messageTyp,object);
        service.sendMessage("profile"+object.getID(), messageTyp, object);
    }

    public void sendToUSzTByUSID(int messageTyp, observable object){
        Integer usID = object.getUSID_P();
        if (usID != null){
            service.sendMessage("PM-TzUS/US" + usID, messageTyp, object);
        }
    }

    public void sendToUserStoryGroup(int messageTyp, observable object){
        // TODO: PM-US/TzUS/T, TB
        service.sendMessage("PM-US", messageTyp, object);
        sendToUSzTByUSID(messageTyp, object);
        service.sendMessage("PM-T", messageTyp, object);
        service.sendMessage("TB", messageTyp, object);
    }

    public void sendToTaskBoardByID(int messageTyp, observable object){
        Integer tbID = object.getTBID_P();
        if (tbID != null){
            service.sendMessage("/Board" + tbID, messageTyp, object);
        }
    }

    public void sendToTaskBoardGroup(int messageTyp, observable object){
        sendToTaskBoardByID(messageTyp, object);
        sendToUSzTByUSID(messageTyp,object);
    }

    public void sendToTaskGroup(int messageTyp, observable object){
        sendToTaskBoardByID(messageTyp, object);
        sendToUSzTByUSID(messageTyp, object);
        service.sendMessage("PM-T", messageTyp, object);
    }

    public void sendToTaskListGroup(int messageTyp, observable object){
        if (messageTyp == 0) sendToTaskGroup(messageTyp,object);
        sendToTaskBoardByID(messageTyp, object);
    }
}
