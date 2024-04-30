package com.team3.project.DAOService;

import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAO.DAOUserStory;

public class DAOStartService {
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static void start() {
        DAOSession.startOrStop(true);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static <Dao> void clearDB(String name) {
        switch (name.toLowerCase()) {
            case "account":
            case "user":
                DAOService.deleteAll(DAOUser.class);
                break;
            case "role":
                DAOService.deleteAll(DAORole.class);
                break;
            case "task":
                DAOService.deleteAll(DAOTask.class);
                break;
            case "tasklist":
                DAOService.deleteAll(DAOTaskList.class);
                break;
            case "taskboard":
                DAOService.deleteAll(DAOTaskBoard.class);
                break;
            case "userstory":
                DAOService.deleteAll(DAOUserStory.class);
                break;
            default:
                DAOService.deleteAll(DAOUser.class);
                DAOService.deleteAll(DAORole.class);
                DAOService.deleteAll(DAOTask.class);
                DAOService.deleteAll(DAOTaskList.class);
                DAOService.deleteAll(DAOTaskBoard.class);
                DAOService.deleteAll(DAOUserStory.class);
                    break;
        }
    }
}
