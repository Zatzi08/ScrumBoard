package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import com.team3.project.service.UserStoryService;
import lombok.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public Task(int tID, String description, int priority, int userStoryID, String dueDate, double timeNeededG, double timeNeededA) throws ParseException {
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
}
