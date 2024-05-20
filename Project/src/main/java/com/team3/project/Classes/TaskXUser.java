package com.team3.project.Classes;

import java.text.ParseException;
import java.util.List;

public class TaskXUser extends Task{
    List<User> users;

    public TaskXUser(List<User> users, int tID, String description, int priority, int userStoryID, String dueDate, double timeNeededG, double timeNeededA) throws ParseException {
        super(tID, description, priority, userStoryID, dueDate, timeNeededG, timeNeededA);
        this.users = users;
    }
}
