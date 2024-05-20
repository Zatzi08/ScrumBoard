package com.team3.project.service;
import com.team3.project.Classes.TaskBoard;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAOService.DAOTaskBoardService;
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
        //DAOTaskBoardService.createTaskBoard(taskBoardName);
    }


}
