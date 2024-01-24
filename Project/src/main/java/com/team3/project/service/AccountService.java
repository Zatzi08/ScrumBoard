package com.team3.project.service;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public boolean LoginCheck(String UName, String PW){
        return !UName.equals("Fail");
    }
}
