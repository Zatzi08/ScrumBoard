package com.team3.project.Facede;

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
}
