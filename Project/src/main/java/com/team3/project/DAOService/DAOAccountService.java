package com.team3.project.DAOService;

import com.team3.project.DAO.DAOAccount;
import com.team3.project.DAO.DAOUser;

public class DAOAccountService {
    //creates
    /* Author: Marvin Oliver Prüger
    * Revisited: Tom-Malte Seep
     * Function: creates a account
     * Reason: refactoring
     * UserStory/Task-ID: A2.D1
     */
    /** creates a Account with a Mail and a password
     * @param email    email
     * @param password password
     * @return         true if account creation was successfull
     */
    public static boolean create(String email, String password) {
        String hashedPassword = DAOUserService.hash(password);
        if (!checkMail(email)) {
            try {
                DAOService.persist(new DAOAccount(email, hashedPassword));
                DAOUser user = DAOUserService.getByMail(email);
                user.setAuthorization(DAOAuthorizationService.getByAuthorization(1));
                DAOService.merge(user);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false; //email already in database
    }
    
    //updates
    /* Author: Marvin Oliver Prüger
    * Revisited: Tom-Malte Seep
    * Function: updates the password for an account
    * Reason: refactoring
    * UserStory/Task-ID: A5.D1
    */
    /** updates the password for an account 
     * @param email    email
     * @param password new password
     * @return         true if update was successful
     */
    public static boolean updatePassword(String email, String password) {
        String hashedPassword = DAOUserService.hash(password);
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        if (account != null) {
            account.setPassword(hashedPassword);
            return DAOService.merge(account);
        }
        return false; //no entry
    }
    
    //deletes
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: deletes a user by email
     * Reason: refactoring
     *         cascading issues
     * UserStory/Task-ID: 
     */
    /** FOR TESTING ONLY <p>
     * deletes a user by mail
     * @param email email
     * @return      false if error occured else true
     */
    public static boolean deleteByMail(String email) {
        return DAOUserService.deleteByMail(email);
    }

    //checks
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: checks if the email exists
     * Reason: refactoring
     * UserStory/Task-ID: A3.D1
     */
    /** checks if the email exists
     * @param email email 
     * @return      true if email exists
     */
    public static boolean checkMail(String email) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        return (account != null) ? true : false;
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: checks if the password is correct for mail
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** checks if password is right for mail
     * @param email     email
     * @param password  password
     * @return          true if account exists and has the password
     */
    public static boolean checkLogin(String email, String password) {
        String hashedPassword = DAOUserService.hash(password);
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        return (account != null && account.getPassword().equals(hashedPassword)) ? true : false;
    }
}