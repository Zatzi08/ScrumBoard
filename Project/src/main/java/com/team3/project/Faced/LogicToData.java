package com.team3.project.Faced;

import com.team3.project.Classes.Enumerations;

public class LogicToData {

    private final Enumerations enumerations = new Enumerations();
/*
    public List<UserStory> getALL(){
        List<DAOUserStory> daoUserStorys = DAOUserStory.getAll();
        List<UserStory> userStorys = new ArrayList<UserStory>();
        for (DAOUserStory daoStory : daoUserStorys){
            userStorys.add(new UserStory(daoStory.getName(), daoStory.getDescription(), enumerations.IntToPriority(daoStory.getPriority()),String.format("%s",daoStory.getId())));
        }
        return userStorys;
    }
*/
}
