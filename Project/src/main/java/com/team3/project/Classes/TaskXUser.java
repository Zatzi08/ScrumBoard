package com.team3.project.Classes;

import com.team3.project.DAO.DAOTask;
import com.team3.project.DAOService.DAOTaskService;

import java.util.List;

public class TaskXUser extends dataClasses implements observable {
    List<User> userList;

    public TaskXUser(int ID, List<User> user) {
        super(ID);
        this.userList = user;
    }

    @Override
    public String toJSON() {
        String json = "{";
        json += "\"id\":\""+ this.getID();
        json += "\",\"user\":"+ parseUser(this.userList);
        json += "}";
        return json;
    }

    private String parseUser(List<User> users){
        String json = "[";

        for (User user : users){
            json += user.toJSON();
            if (user != users.get(users.size()-1))
                json += ",";
        }

        json += "]";
        return json;
    }

    @Override
    public Integer getUSID_P() {
        DAOTask dt = DAOTaskService.getWithUserStorysById(this.getID());
        if (dt != null) {
            return dt.getUserStory().getId();
        }
        return null;
    }

    @Override
    public Integer getTBID_P() {
        DAOTask dt = DAOTaskService.getWithUserStorysById(this.getID());
        if (dt != null && dt.getTaskList() != null) {
            return dt.getTaskList().getTaskBoard().getId();
        }
        return null;
    }
}
