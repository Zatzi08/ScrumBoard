package com.team3.project.service;

import com.team3.project.Classes.Task;
import com.team3.project.Classes.UserStory;
import com.team3.project.Websocket.WebsocketObserver;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserStoryService {

    @Autowired
    private WebsocketObserver observer;

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: gibt UserStory aus DB mit bestimmter id
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * gets an UserStory out of Database
     * @param userStoryID - Identifier
     * @return UserStory-Object
     * @throws Exception Null Params or Object not found
     */
    public UserStory getUserStory(int userStoryID) throws Exception {
        if (userStoryID == -1) throw new Exception("Invalid UserStoryID");

        DAOUserStory story = DAOUserStoryService.getById(userStoryID);
        if (story == null) throw new Exception("UserStory not found");
        return new UserStory(story.getName(), story.getDescription(), story.getPriority(), story.getId());
    }

    public UserStory getUserStoryByName(String name){ //nur für Tests notwendig
        DAOUserStory daoUserStory = DAOUserStoryService.getByName(name);
        return new UserStory(daoUserStory.getName(), daoUserStory.getDescription(), daoUserStory.getPriority(),daoUserStory.getId());
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Erstellt neue oder updatet existente UserStory in DB
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * create/save UserStory-Object in Database
     * @param userStory -
     * @throws Exception Null Params or Object not found
     */
    public void saveUserStory(UserStory userStory) throws Exception {
        if(userStory == null) throw new Exception("Null User-Story");
        if (userStory.getName() == null) throw new Exception("Null Story-Name");
        if (userStory.getDescription() == null) throw new Exception("Null Story-Desc");
        if (userStory.getPriorityAsInt() <= -1) throw new Exception("Null Story-prio");
        if(userStory.getID() <= -1){
            boolean responce = DAOUserStoryService.create(userStory.getName(),userStory.getDescription(), userStory.getPriorityAsInt());
            if (responce){
                try{
                    List<DAOUserStory> dusl = DAOUserStoryService.getAll();
                    DAOUserStory dus = dusl.get(dusl.size()-1);
                    observer.sendToUserStoryGroup(0, new UserStory(dus.getName(), dus.getDescription(), dus.getPriority(), dus.getId()));
                } catch (Exception e){
                    System.out.println("Observer nicht initialisiert");
                }
            }
        } else {
            DAOUserStory dus = DAOUserStoryService.getById(userStory.getID());
            if (dus != null){
                boolean responce = false;
                if (!dus.getName().equals(userStory.getName())) 
                    responce = responce || DAOUserStoryService.updateName(dus.getId(),userStory.getName());
                if (!dus.getDescription().equals(userStory.getDescription()))
                    responce = responce || DAOUserStoryService.updateDescription(dus.getId(),userStory.getDescription());
                if (dus.getPriority() != userStory.getPriorityAsInt())
                    responce = responce || DAOUserStoryService.updatePriority(dus.getId(), userStory.getPriorityAsInt());

                if (responce){
                    try{
                        dus = DAOUserStoryService.getById(userStory.getID());
                        observer.sendToUserStoryGroup(2, new UserStory(dus.getName(), dus.getDescription(), dus.getPriority(), dus.getId()));
                    } catch (Exception e){
                        System.out.println("Observer nicht initialisiert");
                    }
                } else {
                    try{
                        observer.sendToUserStoryGroup(4, null);
                    } catch (Exception e){
                        System.out.println("Observer nicht initialisiert");
                    }
                }
            } else throw new Exception("UserStory not found");
        }
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: gibt alle UserStorys aus DB aus
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * gets all UserStorys out of Database
     * @return LinkedList<UserStory> or Null
     */
    public List<UserStory> getAllUserStorys() {
        List<DAOUserStory> user = DAOUserStoryService.getAll();
        if (user != null) {
            List<UserStory> userStories = new LinkedList<>();
            for (DAOUserStory daoUserStory : user) {
                UserStory toAdd = new UserStory(daoUserStory.getName(), daoUserStory.getDescription(), daoUserStory.getPriority(), daoUserStory.getId());
                userStories.add(toAdd);
            }
            return userStories;
        }
        return null;
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: löscht existente UserStory in DB anhand id
     * Grund: /
     * UserStory/Task-ID: /
     */

    /**
     * deletes UserStory and all related Tasks out of Database
     * @param uid - Identifier
     * @throws Exception Null Params
     */
    public void deleteUserStoryAndLinkedTasks(int uid) throws Exception {
        if (uid <= -1) throw new Exception("Null usid");
        DAOUserStory dus = DAOUserStoryService.getWithTasksById(uid);
        if (dus == null) throw new Exception("UserStory not found");
        List <DAOTask> tasks = dus.getTasks();
        if(tasks != null) {
            for (DAOTask dt : tasks) {
                boolean responce = DAOTaskService.deleteById(dt.getId());
                if (responce) {
                    try {
                        observer.sendToTaskGroup(1,
                                new Task(dt.getId(), dt.getDescription(), dt.getPriority(), dt.getUserStory().getId(),
                                        dt.getDueDate(), dt.getProcessingTimeEstimatedInHours(),
                                        dt.getProcessingTimeRealInHours(), dt.getTaskList().getTaskBoard().getId()));
                    } catch (Exception e) {
                        System.out.println("Observer nicht initialisiert");
                    }
                }
            }
        }
        boolean responce = DAOUserStoryService.deleteById(uid);
        if (responce) {
            try {
                observer.sendToUserStoryGroup(1,
                        new UserStory(dus.getName(), dus.getDescription(), dus.getPriority(), dus.getId()));
            } catch (Exception e) {
                System.out.println("Observer nicht initialisiert");
            }
        }
    }
}