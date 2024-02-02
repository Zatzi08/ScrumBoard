package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Account account;
    private Profile profile;

}
