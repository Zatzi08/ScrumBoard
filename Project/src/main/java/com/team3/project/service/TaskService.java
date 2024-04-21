package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Task;
import com.team3.project.Classes.User;
import org.springframework.stereotype.Service;

import java.util.List;

// Interagiert mit Repository, also create, delete, get, set
@Service
public class TaskService {

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean createTask(Task t){
        //TODO: Anfrage an DB createTaskDB implementieren : erstelle Task mit gegebenen Argumenten
        //return createTaskDB(description,priority);
        return true;
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean deleteTask(Task task){
        //TODO: checkRights(user) implementieren
        //TODO: deleteTaskDB implementieren
        //if checkRights == false then throw Exception "Benötigte Rechte nicht vorhanden"
        // else do deleteTaskDB(taskID)
        // return deleteTaskDB(taskID);
        return true;
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean updateTask(Task task) {

        // wenn ein Wert nicht verändert wird, wird der Methode  null übergeben
        //TODO: implementiere checkTaskID: existiert die gegebene Task
        //TODO: implementiere updateTaskDB(description, priority): Beschreibung in DB ändern ; nicht veränderte Argumente mit null belegen
        //if checkTask == false then throw exception "Die Task wurde nicht in der Datenbank gefunden."
        //else updateTaskDescriptionDB(taskID, description)
        //return  updateTaskDescriptionDB(taskID, description);
        return true;
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public Task getTask(int taskID){
        //Task task = new Task();
        //TODO: implementiere findTask: Rückgabe Task mit  TaskID= taskID
        return null;
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Erfragt alle Tasks aus der Datenbank
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    public List<Task> getAllTask() {
        return null;//DAOTaskService.getAll(); TODO: Implement wenn DAO fertig
    }
}
