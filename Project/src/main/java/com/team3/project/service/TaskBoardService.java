package com.team3.project.service;
import com.team3.project.Classes.TaskBoard;
import com.team3.project.Classes.TaskList;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOTaskListService;
import com.team3.project.DAOService.DAOTaskService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TaskBoardService {
    // TODO: FIX Konstruktor
    public TaskBoard getTaskBoardByID(int tbid) throws Exception {
        if (tbid < 0) throw new Exception("Null ID");
        DAOTaskBoard daoTaskBoard = DAOTaskBoardService.getById(tbid);
        if(daoTaskBoard == null) throw new Exception("TaskBoard not existent");
        TaskBoard taskBoard = toTaskBoard(daoTaskBoard);
        return taskBoard;
    }

    public void createTaskBoard(String taskBoardName) throws Exception{
        if(taskBoardName == null) throw new Exception("Null Name");
        if (taskBoardName.isEmpty()) throw new Exception("Empty Name");
        DAOTaskBoardService.create(taskBoardName, null);
    }


    public TaskBoard getTaskBoard() throws Exception {
        List<DAOTaskBoard> taskBoardList = DAOTaskBoardService.getAll();
        return taskBoardList.isEmpty()? null : toTaskBoard(taskBoardList.get(0));
    }

    public TaskBoard toTaskBoard(DAOTaskBoard daoTaskBoard) throws Exception {
        TaskBoard taskBoard = new TaskBoard(daoTaskBoard.getId(), daoTaskBoard.getName());
        List<DAOTaskList> taskLists = DAOTaskListService.getByTaskBoardId(daoTaskBoard.getId());
        if (taskLists.isEmpty()) throw new Exception("Invalid TaskBoard");
        List<TaskList> taskListList = new LinkedList<TaskList>();
        for (DAOTaskList dtl : taskLists){
            TaskList toAdd = TaskListService.toTaskList(dtl);
            taskListList.add(toAdd);
        }
        taskBoard.setTaskListList(taskListList);
        return taskBoard;
    }

    public List<Integer> getTaskBoardIDs() {
        List<DAOTaskBoard> taskBoardList = DAOTaskBoardService.getAll();
        List<Integer> IDs = new LinkedList<Integer>();
        for (DAOTaskBoard taskBoard : taskBoardList) {
            int toAdd = taskBoard.getId();
            IDs.add(toAdd);
        }
        return IDs;
    }

    public List<TaskBoard> getAllTaskBoards() {
        List<DAOTaskBoard> dtbs = DAOTaskBoardService.getAll();
        if (dtbs.isEmpty()) return null;
        try {
            List<TaskBoard> tbs = new LinkedList<TaskBoard>();
            for (DAOTaskBoard dtb : dtbs) {
                TaskBoard toAdd = toTaskBoard(dtb);
                tbs.add(toAdd);
            }
            return tbs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveTaskBoard(TaskBoard taskBoard) throws Exception {
        if (taskBoard.getID() < -1) throw new Exception("Invalid tbID");
        if (taskBoard.getName().isEmpty()) throw new Exception("Invalid Name");
        if (taskBoard.getID() == -1){
            createTaskBoard(taskBoard.getName());
        } else {
            if (DAOTaskBoardService.getById(taskBoard.getID()) != null){
                DAOTaskBoardService.updateNameById(taskBoard.getID(),taskBoard.getName());
            } else throw new Exception("TaskBoard not found");
        }
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: TB11.B1
     */
    public void deleteTaskBoard(int tbID) throws Exception {
        if (tbID <= 0) throw new Exception("Invalid tbID");
        if (DAOTaskBoardService.getAll().size() < 2) throw new Exception("Invalid Operation");
        DAOTaskBoard dtb = DAOTaskBoardService.getById(tbID);
        if (dtb == null) throw new Exception("TaskBoard not found");
        DAOTaskBoardService.deleteById(dtb.getId());
    }
}
