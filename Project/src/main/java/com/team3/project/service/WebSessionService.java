package com.team3.project.service;

//import com.team3.project.DAOService.DAOSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Service
public class WebSessionService {
    private final DateFormat dformat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private final String MasterID = "EAIFPH8746531";
    public boolean verify(String id) throws Exception {
        if (id == null) throw new Exception("Null ID");
        return id.equals(MasterID) ? true : false; // TODO: statt false DAOSessionIDCheck
    }
}

