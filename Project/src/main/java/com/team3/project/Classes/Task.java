package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOTaskListService;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.service.UserStoryService;
import lombok.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


// Definiert Datentyp
@Getter
@Setter
public class Task extends abstraktDataClasses {
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

    public Task(int tID, String description, int priority, int userStoryID, String dueDate, double timeNeededG, double timeNeededA, boolean done, int tbID) throws ParseException {
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
        this.done = done;
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
            List<DAOTaskList> dtls = DAOTaskListService.getByTaskBoardId(this.getTbID());
            for (DAOTask dt : DAOTaskListService.getWithTasksById(dtls.get(dtls.size() - 1).getId()).getTasks()) {
                if (dt.getId() == this.getID()) return true;
            }
        }
        return false;
    }
}
