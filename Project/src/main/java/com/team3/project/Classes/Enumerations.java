package com.team3.project.Classes;

public class Enumerations {

    // default Priority = low
    public enum Priority {low, normal, high, urgent;

    }
    public enum Role {admin, productOwner,  manager, nutzer}

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Typumwandlung
     * Grund: Frontend sendet nur Strings
     * UserStory/Task-ID: /
     */
    public Priority StringToPriority(String priority){
        return switch (priority) {
            case "urgent" -> Priority.urgent;
            case "high" -> Priority.high;
            case "normal" -> Priority.normal;
            default -> Priority.low;
        };
    }

    public int getInt(Priority priority) {
        return switch (priority) {
            case urgent -> 4;
            case high -> 3;
            case normal -> 2;
            default -> 1;
        };
    }
}
