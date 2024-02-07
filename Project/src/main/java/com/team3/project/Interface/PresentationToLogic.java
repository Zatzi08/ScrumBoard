package com.team3.project.Interface;

import com.team3.project.service.AccountService;
import com.team3.project.service.TaskService;
import com.team3.project.service.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class PresentationToLogic {

    public AccountService accountService;
    public TaskService taskService;
    public UserStoryService userStoryService;
}
