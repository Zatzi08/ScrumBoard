package com.team3.project.Facede;

import com.team3.project.service.*;


public class PresentationToLogic {
    public final AccountService accountService = new AccountService();
    public final TaskService taskService = new TaskService();
    public final UserStoryService userStoryService = new UserStoryService();
    public final WebSessionService webSessionService = new WebSessionService();
    public final TaskBoardService taskBoardService = new TaskBoardService();
    private static PresentationToLogic instance = new PresentationToLogic();


    private PresentationToLogic(){}

    public static PresentationToLogic getInstance(){
        if(PresentationToLogic.instance == null) PresentationToLogic.instance = new PresentationToLogic();
        return PresentationToLogic.instance;
    }
}
