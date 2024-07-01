package com.team3.project.Classes;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class Enumerations {

    // default Priority = low
    public enum Priority {low, normal, high, urgent}
    /*Legende:
     * Nutzer = 1
     * Manager = 2
     * ProductOwner = 3
     * admin = 4
     */
    public enum Role {admin, ProductOwner, Manager, Nutzer}
    public enum ResponceTyp {newItem, deletedItem, changedPrimitivParam, changedConplexParam, ERROR}

    public static Role getRoleFromInt(int role){
        return switch (role) {
            case 4 -> Role.admin;
            case 3 -> Role.ProductOwner;
            case 2 -> Role.Manager;
            default -> Role.Nutzer;
        };
    }


    /* Author: Henry van Rooyen
     * Revisited: /
     * Function: HelperFunction -> converts authorization from role to int
     * UserStory/Task-ID: R2.B1 & B2
     */
    public static int getAuthorizationFromRoleAsInt(@Nonnull final Role role){
        return switch (role) {
            case admin -> 4;
            case ProductOwner -> 3;
            case Manager -> 2;
            case Nutzer -> 1;
        };
    }
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

    public Priority IntToPriority(int priority) {
        return switch (priority) {
            case 4 -> Priority.urgent;
            case 3 -> Priority.high;
            case 2 -> Priority.normal;
            default -> Priority.low;
        };
    }

    public static ResponceTyp IntToResponceTyp(int typID){
        return switch (typID) {
            case 4 -> ResponceTyp.ERROR;
            case 3 -> ResponceTyp.changedConplexParam;
            case 2 -> ResponceTyp.changedPrimitivParam;
            case 1 -> ResponceTyp.deletedItem;
            default -> ResponceTyp.newItem;
        };
    }
}
