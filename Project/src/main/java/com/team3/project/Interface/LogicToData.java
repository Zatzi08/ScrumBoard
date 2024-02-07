package com.team3.project.Interface;

import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserStoryService;

public class LogicToData {
    private static LogicToData instance;
    public final DAOAccountService daoAccountService;
    public final DAOUserStoryService daoUserStoryService;

    private LogicToData(){
        this.daoAccountService = new DAOAccountService();
        this.daoUserStoryService = new DAOUserStoryService();
    }

    public static LogicToData getInstance(){
        if(LogicToData.instance == null) LogicToData.instance = new LogicToData();
        return LogicToData.instance;
    }
}
