package com.team3.project.Facede;

import com.team3.project.service.*;


public class PresentationToLogic {
    public final AccountService accountService;
    public final TaskService taskService;
    public final UserStoryService userStoryService;
    public final WebSessionService webSessionService;
    public TaskBoardService taskBoardService;
    private static PresentationToLogic instance;


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
