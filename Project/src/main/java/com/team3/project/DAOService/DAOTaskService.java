package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUserStory;

public class DAOTaskService {
    //gets
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all tasks
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries
     * @return list of all tasks
     */
    public static List<DAOTask> getAll(){
        return DAOService.getAll(DAOTask.class);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by ID 
     * @param id identifier
     * @return user as Object 
     */
    public static DAOTask getById(int id) {
        return DAOService.getByID(id, DAOTask.class);
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: /
     * Function: gets entry by description
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by description 
     * @param  description
     * @return Task as Object 
     */
    public static DAOTask getByDescription(String description) {
        String parameterName = "description";
        return DAOService.getSingleByPara(DAOTask.class, description, parameterName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by ID 
     * @param id identifier
     * @return user as Object 
     */
    public static DAOTask getWithUserStorysById(int id) {
        String joinOnAttributeName = "userStory";
        return DAOService.getLeftJoinByID(id, DAOTask.class, joinOnAttributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static List<DAOTask> getListByUserStoryId(int id) {
        String parameterName = "userStory";
        return DAOService.getListByPara(DAOTask.class, id, parameterName);
    }

    //creates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean create(String description, /*DAOTaskList taskList,*/ DAOUserStory userStory) {
        DAOTask task = new DAOTask(description, /*taskList,*/ userStory);
        return DAOService.persist(task);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean create(String description, /*int taskListId,*/ int userStoryId) {
        /*DAOTaskList taskList = DAOService.getByID(taskListId, DAOTaskList.class);*/
        DAOUserStory userStory = DAOService.getByID(userStoryId, DAOUserStory.class);
        return create(description, /*taskList,*/ userStory);
    }

    //updates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean updateUserStoryById(int id, int userStoryId) {
        DAOTask task = DAOService.getByID(id, DAOTask.class);
        task.setUserStory(DAOUserStoryService.getById(userStoryId));
        return DAOService.merge(task);
    }
    
    /* Author: Marvin
     * Revisited: /
     * Function: Updates the description
     * Reason:
     * UserStory/Task-ID:
     */
    /**
     * @param description
     * @param TaskId
     * @return
     */
    public static boolean updateDescriptonById(String description, int TaskId) {
        DAOTask task = DAOService.getByID(TaskId, DAOTask.class);
        task.setDescription(description);;
        return DAOService.merge(task);
    }

    //deletes
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean deleteById(int id) {
        DAOTask task = DAOService.getByID(id, DAOTask.class);
        return DAOService.delete(task);
    }
}
