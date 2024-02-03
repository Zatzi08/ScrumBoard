package com.team3.project.Classes;

import com.team3.project.Classes.Account;
import com.team3.project.Classes.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Account account;
    private Profile profile;
    public User(Account account, Profile profile){
        this.account = account;
        this.profile = profile;
    }
}
