package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.service.UserStoryService;
import lombok.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


// Definiert Datentyp
@Getter
@Setter
public class Task extends dataClasses {
    private String description;
    private Priority priority;
    private int userStoryID;
    private Date dueDate;
    private double timeNeededG;
    private double timeNeededA;
    private boolean done;
    private int tbID;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public Task(int tID, String description, int priority, int userStoryID, String dueDate, double timeNeededG, double timeNeededA,int tbID) throws ParseException {
        super(tID);
        Enumerations prior = new Enumerations();
        this.description = description;
        this.priority = prior.IntToPriority(priority);
        this.userStoryID = userStoryID;
        if (dueDate != null) {
            DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (dueDate != null && !dueDate.equals("")) {
                this.dueDate = dformat.parse(dueDate.replace('T', ' '));
            }
        } else {
            this.dueDate = null;
        }
        this.timeNeededG = timeNeededG;
        this.timeNeededA = timeNeededA;
        this.done = false;
        this.tbID = tbID;

    }

    public int getPriorityAsInt(){
        Enumerations prior = new Enumerations();
        return prior.getInt(this.getPriority());
    }

    public String getDueDateAsString(){
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            return dformat.format(this.dueDate).replace(' ','T');
        } catch (Exception e){
            return "";
        }
    }

    public String getDueDateAsPresentable(){
        DateFormat dformat = new SimpleDateFormat("dd/MM/yy HH:mm");
        try{
            return dformat.format(this.dueDate);
        } catch (Exception e){
            return "";
        }
    }

    public UserStory getUserStory(){
        UserStoryService userStoryService = new UserStoryService();
        try {
            return userStoryService.getUserStory(this.getUserStoryID());
        } catch (Exception e){
            return null;
        }
    }

    public String getTaskBoardNameOfTask(){
        if (this.getTbID() == -1) return "";
        return DAOTaskBoardService.getById(this.getTbID()).getName();
    }

    public boolean isDone(){
        if (this.getTbID() >= 1) {
            return DAOTaskService.getById(this.getID()).getTaskList().getSequence() == 5;
        }
        return false;
    }

    public List<User> getUser(){
        List<DAOUser> dul = DAOTaskService.getWithUsersById(this.getID()).getUsers();
        List<User> list = new LinkedList<User>();
        for (DAOUser u : dul){
            User toAdd = new User(u.getName(),u.getId(),new LinkedList<Enumerations.Role>(),u.getAuthorization().getAuthorization());
            list.add(toAdd);
        }
        return list;
    }

    public List<String> getUserAsJSON(){
        List<DAOUser> dul = DAOTaskService.getWithUsersById(this.getID()).getUsers();
        List<String> list = new LinkedList<String>();
        if (!dul.isEmpty()) {
            for (DAOUser u : dul) {
                User toAdd = new User(u.getName(), u.getId(), new LinkedList<Enumerations.Role>(), u.getAuthorization().getAuthorization());
                list.add(toAdd.toJSON());
            }
        }
        return list;
    }

    public String toJSON() {
        String json = "{";
        json += "\"id\":\""+ this.getID();
        json += "\",\"desc\":\""+ this.getDescription();
        json += "\",\"userStoryID\":\""+ this.getUserStoryID();
        json += "\",\"tbID\":\""+ this.getTbID();
        json += "\",\"tlID\":\""+ DAOTaskService.getById(this.getID()).getTaskList().getId();
        json += "\",\"dueDate\":\""+ this.getDueDateAsString();
        json += "\",\"timeNeededG\":\""+ this.getTimeNeededG();
        json += "\",\"timeNeededA\":\""+ this.getTimeNeededA();
        json += "\"}";
        return json;
    }
}
