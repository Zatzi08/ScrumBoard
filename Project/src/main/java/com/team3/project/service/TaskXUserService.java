package com.team3.project.service;

import com.team3.project.Classes.Task;
import com.team3.project.Classes.TaskXUser;
import com.team3.project.Classes.User;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserService;

import java.util.LinkedList;
import java.util.List;

public class TaskXUserService extends TaskService{

    public void saveTaskXUser(TaskXUser taskXUser){
        try{
            saveTask(new Task(taskXUser.getID(), taskXUser.getDescription(), taskXUser.getPriorityAsInt(),taskXUser.getUserStoryID(), taskXUser.getDueDateAsString(), taskXUser.getTimeNeededG(), taskXUser.getTimeNeededA(), taskXUser.getTbID()));
        }catch (Exception e){
            e.printStackTrace();
        }
        int id = taskXUser.getID();
        if (id == -1){
            try {
                List<DAOTask> dtl = DAOTaskService.getAll();
                id = dtl.get(dtl.size()-1).getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<DAOUser> daoUsers = new LinkedList<>();
        for(User user : taskXUser.getUsers()){
            DAOUser daoUser = DAOUserService.getById(user.getID());
            daoUsers.add(daoUser);
        }
        DAOTaskService.updateUsersById(id, daoUsers);
    }

}
