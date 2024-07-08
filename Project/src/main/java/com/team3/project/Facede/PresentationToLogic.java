package com.team3.project.Facede;

import com.team3.project.Classes.Enumerations;
import com.team3.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentationToLogic {
    @Autowired
    public AccountService accountService;
    @Autowired
    public TaskService taskService;
    @Autowired
    public UserStoryService userStoryService;
    @Autowired
    public WebSessionService webSessionService;
    @Autowired
    public TaskBoardService taskBoardService;
    @Autowired
    public RoleService roleService;
    @Autowired
    public Enumerations enumerations;

    public PresentationToLogic init_facade(){
        this.taskService = new TaskService();
        this.userStoryService = new UserStoryService();
        this.accountService = new AccountService();
        this.taskBoardService = new TaskBoardService();
        this.webSessionService = new WebSessionService();
        this.roleService = new RoleService();
        return this;
    }
}
