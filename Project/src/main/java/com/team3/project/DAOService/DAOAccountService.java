package com.team3.project.DAOService;

import com.team3.project.DAO.DAOAccount;

public class DAOAccountService {
    /* Author: Marvin Oliver Prüger
    * Revisited: Tom-Malte Seep
     * Function: creates a account
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** creates a Account with a Mail and a password
     * @param email
     * @param password
     * @return
     */
    public static boolean create(String email, String password) {
        if (!checkMail(email)) {
            try {
                DAOService.persist(new DAOAccount(email, password));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false; //email already in database
    }
    
    /* Author: Marvin Oliver Prüger
    * Revisited: Tom-Malte Seep
    * Function: updates the password for an account
    * Reason: refactoring
    * UserStory/Task-ID:
    */
    /** updates the password for an account 
     * @param email
     * @param password new password
     * @return
     */
    public static boolean updatePassword(String email, String password) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        if (account != null) {
            account.setPassword(password);
            try {
                DAOService.merge(account);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false; //no entry
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: deletes a user by email
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** FOR TESTING ONLY <p>
     * deletes a user by mail
     * @param email
     * @return      false if error occured else true
     */
    public static boolean deleteByMail(String email) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        if (account != null) {
            try { 
                DAOService.delete(account);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.toString());
                return false;
            }
        }
        return true;
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: checks if the email exists
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** checks if the email exists
     * @param emailToTest   
     * @return            does email exists
     */
    public static boolean checkMail(String emailToTest) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, emailToTest, parameterName);
        return (account != null) ? true : false;
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: checks if the password is correct for mail
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** checks if password is right for mail
     * @param email     
     * @param password 
     * @return         
     */
    public static boolean loginCheck(String email, String password) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        return (account != null && account.getPassword().equals(password)) ? true : false;
    }
}