package com.team3.project.service;
import com.team3.project.Classes.TaskBoard;
import com.team3.project.Classes.TaskList;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOTaskListService;

import java.util.LinkedList;
import java.util.List;

public class TaskBoardService {
    // TODO: FIX Konstruktor
    public TaskBoard getTaskBoardByID(int tbid) throws Exception {
        if (tbid < 0) throw new Exception("Null ID");
        DAOTaskBoard daoTaskBoard = DAOTaskBoardService.getById(tbid);
        if(daoTaskBoard == null) throw new Exception("TaskBoard not existent");
        TaskBoard taskBoard = toTaskBoard(daoTaskBoard);
        return taskBoard;
    }

    //TODO: DB-Funktion zum erstellen von TaskBoards fehlt
    public void createTaskBoard(String taskBoardName) throws Exception{
        if(taskBoardName == null) throw new Exception("Null Name");
        if (taskBoardName.isEmpty()) throw new Exception("Empty Name");
        DAOTaskBoardService.createWithDefaultTaskLists(taskBoardName);
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
}
