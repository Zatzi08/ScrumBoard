package com.team3.project.Classes;

import lombok.*;

@Getter
@Setter
public abstract class abstraktDataClasses {
    private int ID;

    public abstraktDataClasses(int ID) {
        this.ID = ID;
    }
}
