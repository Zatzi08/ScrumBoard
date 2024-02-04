package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.Getter;
import lombok.Setter;


// Definiert Datentyp

@Getter
@Setter
public class Task {
    private int taskID;
    private String description;
    private Priority priority;
    public Task(int tID,String description, Priority priority){
        this.taskID = tID;
        this.description = description;
        this.priority = priority;
    }
    public boolean createTask(String description, Enumerations.Priority priority){
        //TODO: Anfrage an DB createTaskDB implementieren : erstelle Task mit gegebenen Argumenten
        //return createTaskDB(description,priority);
        return true;
    }
    public boolean deleteTask(int taskID, User user){
        //TODO: checkRights(user) implementieren
        //TODO: deleteTaskDB implementieren
        //if checkRights == false then throw Exception "Benötigte Rechte nicht vorhanden"
        // else do deleteTaskDB(taskID)
        // return deleteTaskDB(taskID);
        return true;
    }
    public boolean updateTask(int taskID, String description, Enumerations.Priority priority) {
        // wenn ein Wert nicht verändert wird, wird der Methode  null übergeben
        //TODO: implementiere checkTaskID: existiert die gegebene Task
        //TODO: implementiere updateTaskDB(description, priority): Beschreibung in DB ändern ; nicht veränderte Argumente mit null belegen
        //if checkTask == false then throw exception "Die Task wurde nicht in der Datenbank gefunden."
        //else updateTaskDescriptionDB(taskID, description)
        //return  updateTaskDescriptionDB(taskID, description);
        return true;
    }
}
