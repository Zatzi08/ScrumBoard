package com.team3.project.service;

public class TaskBoardService {
    public Object getTaskBoard(int tbid) throws Exception {
        if (tbid == -1) throw new Exception("Null ID");
        return null; //DAOTaskBoardService.getTaskBoard(tbid);
    }
}
