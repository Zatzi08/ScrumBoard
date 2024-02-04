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
    private int authorization;

    public User(Account account, Profile profile, int authorization){
        this.account = account;
        this.profile = profile;
        this.authorization = authorization;
    }
}
