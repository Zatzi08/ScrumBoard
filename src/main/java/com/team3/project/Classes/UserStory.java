package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAOService.DAOTaskService;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class UserStory extends dataClasses implements observable {
    private String name;
    private String description;
    private Priority priority;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory(String name, String description, int priority, int ID){
        super(ID);
        Enumerations prior = new Enumerations();
        this.name = name;
        this.description = description;
        this.priority = prior.IntToPriority(priority);
    }

    public int getPriorityAsInt() {
        Enumerations prior = new Enumerations();
        return prior.getInt(this.getPriority());
    }


    public List<Double> getAnforderungenG(){
        List<DAOTask> dtl =DAOTaskService.getListByUserStoryId(this.getID());
        List<Double> AnforderungenG = new LinkedList<Double>();
        for (DAOTask dt : dtl){
            if (dt.getTaskList().getId() % 5 == 0){
                double g = dt.getProcessingTimeEstimatedInHours();
                AnforderungenG.add(g);
            }
        }
        return AnforderungenG;
    }

    public List<Double> getAnforderungenA(){
        List<DAOTask> dtl =DAOTaskService.getListByUserStoryId(this.getID());
        List<Double> AnforderungenA = new LinkedList<Double>();
        for (DAOTask dt : dtl){
            if (dt.getTaskList().getId() % 5 == 0){
                double g = dt.getProcessingTimeRealInHours();
                AnforderungenA.add(g);
            }
        }
        return AnforderungenA;
    }

    public List<String> getTNames(){
        List<String> name = new LinkedList<String>();
        List<DAOTask> dtl = DAOTaskService.getListByUserStoryId(this.getID());
        for (DAOTask dt : dtl) {
            if (dt.getTaskList().getId() % 5 == 0){
                String toAdd = "'" + dt.getDescription() + "'";
                name.add(toAdd);
            }
        }
        return name;
    }

    public String toJSON() {
        String json = "{";
        json += "\"id\":\"" + this.getID();
        json += "\",\"name\":\"" + this.getName();
        json += "\",\"desc\":\"" + this.getDescription();
        json += "\",\"prio\":\"" + this.getPriorityAsInt();
        json += "\"}";
        return json;
    }

    @Override
    public Integer getUSID_P() {
        return this.getID();
    }

    @Override
    public Integer getTBID_P() {
        return null;
    }
}
