package com.team3.project.Faced;

import com.team3.project.Controller.WebController;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.service.*;

import java.util.Random;


public class PresentationToLogic {
    public final AccountService accountService;
    public final TaskService taskService;
    public final UserStoryService userStoryService;
    public final WebSessionService webSessionService;
    private static PresentationToLogic instance;
    public TaskBoardService taskBoardService;


    private PresentationToLogic(){
        this.accountService = new AccountService();
        this.userStoryService = new UserStoryService();
        this.taskService = new TaskService();
        this.webSessionService = new WebSessionService();
        this.taskBoardService = new TaskBoardService();
    }

    public static PresentationToLogic getInstance(){
        if(PresentationToLogic.instance == null) PresentationToLogic.instance = new PresentationToLogic();
        return PresentationToLogic.instance;
    }

}
