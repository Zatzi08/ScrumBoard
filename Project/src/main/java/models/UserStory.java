package models;

import models.Enumerations.Prioritys;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStory {
    private int userID;
    private String description;
    private Prioritys priority;
    //TODO: Datentyp ändern auf "Priority" welcher die Strings low, normal, high und urgent beinhaltet
}
