package com.team3.project.DAOService;

import java.util.List;
import java.util.stream.Collectors;

import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAO.DAOUserStory;

import io.micrometer.common.lang.Nullable;

public class DAOTaskService {
    //gets
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all tasks
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries
     * @return List of DAOTasks
     */
    public static List<DAOTask> getAll(){
        return DAOService.getAll(DAOTask.class);
    }

    static List<DAOTask> getAllFloating() {
        String linkName = "taskList";
        return DAOService.getAllFloating(DAOTask.class, linkName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by ID 
     * @param id identifier
     * @return   DAOTask
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
     * @param  description description
     * @return             DAOTask
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
    /** gets entry by ID with DAOUserStory
     * @param id identifier
     * @return   DAOTask
     */
    public static DAOTask getWithUserStorysById(int id) {
        String joinOnAttributeName = "userStory";
        return DAOService.getLeftJoinByID(id, DAOTask.class, joinOnAttributeName);
    }

    static DAOTask getWithTaskListById(int id) {
        String joinOnAtrributeName = "taskList";
        return DAOService.getLeftJoinByID(id, DAOTask.class, joinOnAtrributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: get entries of tasks by userStoryid
     * Reason:
     * UserStory/Task-ID:
     */
    /** get entries of tasks by userStoryid
     * @param userStoryId identifier userstory
     * @return            List of DAOTasks
     */
    public static List<DAOTask> getListByUserStoryId(int userStoryId) {
        String parameterName = "userStory.id";
        return DAOService.getListByPara(DAOTask.class, userStoryId, parameterName);
    }

    //creates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: creates a task with the DAOUserStory
     * Reason:
     * UserStory/Task-ID:
     */
    /** creates a task with the DAOUserStory
     * @param description description
     * @param userStory   DAOUserStory
     * @return            true if create was successfull
     */
    public static boolean create(String description, /*DAOTaskList taskList,*/ DAOUserStory userStory) {
        return DAOService.merge(new DAOTask(description, /*taskList,*/ userStory));
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: creates a task with the userStoryid
     * Reason: overload
     * UserStory/Task-ID:
     */
    /** creates a task with the userStoryid
     * @param description description
     * @param userStoryId identifier DAOUserStory
     * @return            true if create was successfull
     */
    public static boolean create(String description, int userStoryId) {
        DAOUserStory userStory = DAOService.getByID(userStoryId, DAOUserStory.class);
        return create(description, userStory);
    }

    public static boolean create(String description, int priority, boolean done, @Nullable String dueDate, 
                                 double processingTimeEstimatedInHours, double processingTimeRealInHours, 
                                 @Nullable DAOTaskList taskList, @Nullable DAOUserStory userStory, @Nullable List<DAOUser> users) {
        return DAOService.merge(new DAOTask(description, priority, done, dueDate, processingTimeEstimatedInHours, processingTimeRealInHours, taskList, userStory, users));
    }


    //updates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates an entry
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates a DAOTask
     * @param id          identifier
     * @param description description
     * @param priority    priority
     * @param doDate      doDate
     * @param timeNeededG timeNeededG
     * @param timeNeededA timeNeededA
     * @param taskList    DAOTaskList
     * @param userStory   DAOUserStory
     * @param users       List of DAOUsers
     * @return            true if update is successfull
     */
    public static boolean updateById(int id, @Nullable String description, int priority, boolean done, @Nullable String doDate, 
                                     double processingTimeEstimatedInHours, double processingTimeRealInHours, @Nullable DAOTaskList taskList, 
                                     @Nullable DAOUserStory userStory, @Nullable List<DAOUser> users) {
        DAOTask task = DAOService.getByID(id, DAOTask.class);
        task.cloneDAOTask(new DAOTask(description, priority, done, doDate, processingTimeEstimatedInHours, processingTimeRealInHours, taskList, userStory, users));
        return DAOService.merge(task);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: adds a userStoryId to a task
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates a DAOTask with a DAOUserStory
     * @param id          identifier
     * @param userStoryId identifier DAOUserStory
     * @return            true if update was successfull
     */
    public static boolean updateUserStoryById(int id, int userStoryId) {
        DAOTask task = DAOService.getByID(id, DAOTask.class);
        task.setUserStory(DAOUserStoryService.getById(userStoryId));
        return DAOService.merge(task);
    }
    
    /* Author: Marvin
     * Revisited: /
     * Function: updates the description
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates the description
     * @param id          identifier
     * @param description description
     * @return            true if update was successfull
     */
    public static boolean updateDescriptonById(int id, String description) {
        DAOTask task = DAOService.getByID(id, DAOTask.class);
        task.setDescription(description);
        return DAOService.merge(task);
    }

    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates the priority
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates the priority
     * @param id       identifier
     * @param priority priority
     * @return         true if update was successfull
     */
    public static boolean updatePriorityById(int id, int priority) {
        DAOTask task = DAOService.getByID(id, DAOTask.class);
        task.setPriority(priority);
        return DAOService.merge(task);
    }

    public static boolean updateDueDateById(int id, String dueDate) {
        DAOTask daoTask = DAOService.getByID(id, DAOTask.class);
        daoTask.setDueDate(dueDate);
        return DAOService.merge(daoTask);
    }

    public static boolean updateProcessingTimeEstimatedInHoursById(int id, double processingTimeEstimatedInHours) {
        DAOTask daoTask = DAOService.getByID(id, DAOTask.class);
        daoTask.setProcessingTimeEstimatedInHours(processingTimeEstimatedInHours);
        return DAOService.merge(daoTask);
    }

    public static boolean updateProcessingTimeRealInHoursById(int id, double processingTimeRealInHours) {
        DAOTask daoTask = DAOService.getByID(id, DAOTask.class);
        daoTask.setProcessingTimeRealInHours(processingTimeRealInHours);
        return DAOService.merge(daoTask);
    }



    static boolean updateDoneById(int id, boolean done) {
        DAOTask daoTask = DAOService.getByID(id, DAOTask.class);
        daoTask.setDone(done);
        return DAOService.merge(daoTask);
    }

    public static boolean setDoneById(int id) {
        return updateDoneById(id, true);
    }

    public static boolean setUnDoneById(int id) {
        return updateDoneById(id, false);
    }

    public static boolean updateUsersById(int id, List<DAOUser> daoUsers) {
        DAOTask daoTask = DAOService.getByID(id, DAOTask.class);
        daoTask.setUsers(daoUsers);
        return DAOService.merge(daoTask);
    }

    public static boolean updateTaskBoardById(int id, DAOTaskBoard taskBoard) {
        return updateTaskBoardIdById(id, taskBoard.getId());
    }
    
    public static boolean updateTaskBoardIdById(int id, int taskBoardId) {
        //check if "freie Tasks" exists
        String joinOnAttributeName = "taskList";
        DAOTask daoTask = DAOService.getLeftJoinByID(id, DAOTask.class, joinOnAttributeName);
        String daoTaskListName = "freie Tasks";
        try {
            DAOTaskBoard daoTaskBoard = DAOTaskBoardService.getWithTaskListsById(taskBoardId);
            List<DAOTaskList> daoTaskListsOfDaoTaskBoard = daoTaskBoard.getTaskLists();
            if (daoTaskListsOfDaoTaskBoard.stream().map(DAOTaskList::getName).collect(Collectors.toList()).contains(daoTaskListName)) {
                DAOTaskList daoTaskList = daoTaskListsOfDaoTaskBoard.stream().filter(sDaoTaskList -> sDaoTaskList.getName().equals(daoTaskListName)).min((sDaoTaskList1, sDaoTaskList2) -> Integer.compare(sDaoTaskList1.getSequence(), sDaoTaskList2.getSequence())).get();
                daoTask.setTaskList(daoTaskList);
                return DAOService.merge(daoTask);
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean updateTaskListById(int id, DAOTaskList taskList) {
        DAOTask daoTask = getWithTaskListById(id);
        daoTask.setTaskList(taskList);
        return DAOService.merge(daoTask);
    }

    //deletes
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes a entry by id
     * Reason:
     * UserStory/Task-ID:
     */
    /** delete DAOTask by id
     * @param id identifier
     * @return   true if delete is successfull
     */
    public static boolean deleteById(int id) {
        DAOTask task = DAOService.getByID(id, DAOTask.class);
        return DAOService.delete(task);
    }
}
