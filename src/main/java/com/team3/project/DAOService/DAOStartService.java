package com.team3.project.DAOService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAO.DAOUserStory;

public class DAOStartService {
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: starts the EntityManagerFactory
     * Reason:
     * UserStory/Task-ID:
     */
    /** starts the EntityManagerFactory
     */
    public static void start() {
        DAOSession.startOrStop(true);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: starts the EntityManagerFactory for tests
     * Reason:
     * UserStory/Task-ID:
     */
    /** starts the EntityManagerFactory for tests
     */
    public static void restartForTests() {
        DAOSession.testStart();
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: stop the EntityManagerFactory
     * Reason:
     * UserStory/Task-ID:
     */
    /** stop the EntityManagerFactory
     */
    public static void stop() {
        DAOSession.startOrStop(false);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes all entries from the database
     * Reason:
     * UserStory/Task-ID:
     */
    /** deletes all entries
     * @param <Dao> DAOObject
     * @param name  "account""user"<p>"role"<p>"task"<p>"tasklist"<p>"taskboard"<p>"userstory"
     */
    public static <Dao> void clearDBTable(String name) {
        switch (name.toLowerCase()) {
            case "accounts": case "account":
            case "users": case "user":
                DAOService.deleteAll(DAOUser.class);
                break;
            case "roles": case "role":
                DAOService.deleteAll(DAORole.class);
                break;
            case "tasks": case "task":
                DAOService.deleteAll(DAOTask.class);
                break;
            case "tasklists": case "tasklist":
                DAOService.deleteAll(DAOTaskList.class);
                break;
            case "taskboards": case "taskboard":
                DAOService.deleteAll(DAOTaskBoard.class);
                break;
            case "userstories": case "userstorys": case "userstory":
                DAOService.deleteAll(DAOUserStory.class);
                break;
            default:
                    break;
        }
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: 
     * Reason: 
     * UserStory/Task-ID:
     */
    public static <Dao> boolean wipeDb() {
        try {
            DAOService.deleteAll(DAORole.class);
            DAOService.deleteAll(DAOTask.class);
            DAOService.deleteAll(DAOTaskList.class);
            DAOService.deleteAll(DAOTaskBoard.class);
            DAOService.deleteAll(DAOUser.class);
            DAOService.deleteAll(DAOUserStory.class);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
