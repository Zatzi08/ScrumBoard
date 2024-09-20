package com.team3.project.Classes;

import lombok.*;

@Getter
@Setter
public abstract class dataClasses {
    private int ID;

    public dataClasses(int ID) {
        this.ID = ID;
    }
}
