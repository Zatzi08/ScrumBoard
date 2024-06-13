package com.team3.project.Classes;

import static com.team3.project.Classes.Enumerations.IntToResponceTyp;

public class Response implements parseable{
    Enumerations.ResponceTyp ResponceTyp;
    parseable ResponceObject;

    public Response(int typeID, parseable object){
        this.ResponceTyp = IntToResponceTyp(typeID);
        this.ResponceObject = object;
    }

    public String toJSON(){
        String responce = "{";
        if (this.ResponceTyp.ordinal() == 4){
            responce += "\"Responcetype\":\"" + this.ResponceTyp.ordinal();
            responce += "\",\"object\":" + null;
        } else {
            responce += "\"Responcetype\":\"" + this.ResponceTyp.ordinal();
            responce += "\",\"object\":" + this.ResponceObject.toJSON();
        }
        responce += "}";
        return responce;
    }
}
