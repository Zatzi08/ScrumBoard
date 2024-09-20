package com.team3.project.Classes;

import static com.team3.project.Classes.Enumerations.IntToResponceTyp;

public class Response implements observable {
    Enumerations.ResponceTyp ResponceTyp;
    observable ResponceObject;

    public Response(int typeID, observable object){
        this.ResponceTyp = IntToResponceTyp(typeID);
        this.ResponceObject = object;
    }

    public String toJSON(){
        String responce = "{";
        if (this.ResponceTyp.ordinal() == 4){
            responce += "\"ResponceTyp\":\"" + this.ResponceTyp.ordinal();
            responce += "\",\"objectTyp\":" + null;
            responce += ",\"object\":" + null;
        } else {
            responce += "\"ResponceTyp\":\"" + this.ResponceTyp.ordinal();
            responce += "\",\"objectTyp\":\"" + this.ResponceObject.getClass();
            responce += "\",\"object\":" + this.ResponceObject.toJSON();
        }
        responce += "}";
        return responce;
    }

    @Override
    public int getID() {
        return this.ResponceObject.getID();
    }

    @Override
    public Integer getUSID_P() {
        return this.ResponceObject.getUSID_P();
    }

    @Override
    public Integer getTBID_P() {
        return this.ResponceObject.getTBID_P();
    }
}
