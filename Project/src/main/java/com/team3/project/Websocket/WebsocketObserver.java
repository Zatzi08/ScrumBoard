package com.team3.project.Websocket;

import com.team3.project.Classes.Role;
import com.team3.project.Classes.UserStory;
import com.team3.project.Classes.observable;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAORoleService;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.DAOService.DAOUserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebsocketObserver {

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
        for (DAOUserStory dus : DAOUserStoryService.getAll()){
            service.sendMessage("PM-TzUS/US"+dus.getId(), messageTyp, object);
        }
    }

    public void sendToProfileByID(int messageTyp, observable object) {
        sendToUserGroup(messageTyp,object);
        service.sendMessage("Pr/user"+object.getID(), messageTyp, object);
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
        for (DAOTaskBoard dtb : DAOTaskBoardService.getAll()){
            service.sendMessage("TB/board" + dtb.getId(), messageTyp, object);
        }
    }

    public void sendToTaskBoardByID(int messageTyp, observable object){
        Integer tbID = object.getTBID_P();
        if (tbID != null){
            service.sendMessage("TB/board" + tbID, messageTyp, object);
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
        else sendToTaskBoardByID(messageTyp, object);
    }

    public void sendToRoleGroup(int messageTyp, Role role) {
        List<DAOUser> dul = DAOUserService.getAllWithRoles();
        DAORole dr = DAORoleService.getByID(role.getID());
        for (DAOUser du : dul){
            if (du.getRoles().contains(dr)){
                sendToProfileByID(du.getId(), role);
            }
        }
        service.sendMessage("PM-N", messageTyp, role);
    }
}
