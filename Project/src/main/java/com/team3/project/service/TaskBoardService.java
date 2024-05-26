package com.team3.project.service;
import com.team3.project.Classes.TaskBoard;
import com.team3.project.Classes.TaskList;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOTaskListService;
import com.team3.project.service.TaskListService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.team3.project.service.TaskListService.toTaskList;

public class TaskBoardService {
    public TaskBoard getTaskBoardByID(int tbid) throws Exception {
        if (tbid < 0) throw new Exception("Null ID");
        DAOTaskBoard daoTaskBoard = DAOTaskBoardService.getById(tbid);
        if(daoTaskBoard == null) throw new Exception("TaskBoard not existent");
        TaskBoard taskBoard = new TaskBoard(daoTaskBoard.getId(), daoTaskBoard.getName());
        taskBoard.setTaskListsInTaskBoard(daoTaskBoard.getTaskLists());
        return taskBoard;
    }
    //TODO: DB-Funktion zum erstellen von TaskBoards fehlt
    public void createTaskBoard(String taskBoardName) throws Exception{
        if(taskBoardName == null) throw new Exception("Null Name");
        if (taskBoardName.isEmpty()) throw new Exception("Empty Name");
        DAOTaskBoardService.create(taskBoardName, null);
    }


    public TaskBoard getTaskBoard() throws Exception {
        List<DAOTaskBoard> taskBoardList = DAOTaskBoardService.getAll();
        return taskBoardList.isEmpty()? null : toTaskBoard(taskBoardList.get(0));
    }

    private TaskBoard toTaskBoard(DAOTaskBoard daoTaskBoard) throws Exception {
        TaskBoard taskBoard = new TaskBoard(daoTaskBoard.getId(), daoTaskBoard.getName());
        List<DAOTaskList> taskLists = DAOTaskListService.getByTaskBoardId(daoTaskBoard.getId());
        List<TaskList> taskListsInTaskBoard = new LinkedList<TaskList>();
        for (DAOTaskList taskList : taskLists) {
            TaskList toAdd = toTaskList(taskList);
            taskListsInTaskBoard.add(toAdd);
        }
        taskBoard.setTaskListsInTaskBoard(taskLists);
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
}
