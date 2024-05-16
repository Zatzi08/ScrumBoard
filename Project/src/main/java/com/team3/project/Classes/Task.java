package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.beans.ConstructorProperties;
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
    private int timeNeededG;
    private int timeNeededA;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public Task(int tID, String description, int priority, int userStoryID, String dueDate, int timeNeededG, int timeNeededA) throws ParseException {
        super(tID);
        Enumerations prior = new Enumerations();
        this.description = description;
        this.priority = prior.IntToPriority(priority);
        this.userStoryID = userStoryID;
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (dueDate != null && !dueDate.equals("")) {
            this.dueDate = dformat.parse(dueDate.replace('T', ' '));
        } else {
            this.dueDate = null;
        }
        this.timeNeededG = timeNeededG;
        this.timeNeededA = timeNeededA;
    }




    public int getPriorityAsInt(){
        Enumerations prior = new Enumerations();
        return prior.getInt(this.getPriority());
    }

    public String getDueDateAsString(){
        DateFormat dformat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return dformat.format(this.dueDate);
    }
}
