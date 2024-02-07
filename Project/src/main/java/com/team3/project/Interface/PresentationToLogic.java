package com.team3.project.Interface;

import com.team3.project.service.AccountService;
import com.team3.project.service.TaskService;
import com.team3.project.service.UserStoryService;
import org.springframework.context.annotation.Bean;


public class PresentationToLogic {
    public final AccountService accountService;
    public final TaskService taskService;
    public final UserStoryService userStoryService;
    private static PresentationToLogic instance;

    private PresentationToLogic(){
        this.accountService = new AccountService();
        this.userStoryService = new UserStoryService();
        this.taskService = new TaskService();
    }
    public static PresentationToLogic getInstance(){
        if(PresentationToLogic.instance == null) PresentationToLogic.instance = new PresentationToLogic();
        return PresentationToLogic.instance;
    }
}
