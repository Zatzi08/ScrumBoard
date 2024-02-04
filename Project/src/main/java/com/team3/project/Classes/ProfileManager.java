package com.team3.project.Classes;
import lombok.Setter;
import lombok.Getter;
import java.util.List;

@Getter
@Setter
public class ProfileManager {
    private List<User> userList;
    public ProfileManager(List<User> userList){
        this.userList = userList;
    }
}
