package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {
    private String name;
    private String privatDescription;
    private String workDescription;
    private String[] roles;
}
