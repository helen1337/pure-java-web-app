package dao;

import models.User;

/**
 * Defines operations for managing users in a data store
 */
public interface UserDao {

    /**
     * Check user existence by login
     *
     * @param login the user's login
     * @return true if the searching is successful otherwise false
     */
    boolean isExist(String login);

    /**
     * Register a new user in the system
     *
     * @param newUser the user object representing the new user
     * @return true if the user registration is successful otherwise false
     */
    boolean signUp(User newUser);

    /**
     * Log in a user with the specified login and password
     *
     * @param login the login of the user
     * @param password the password of the user
     * @return the user object if login is successful otherwise null
     */
    User login(String login, String password);

    /**
     * Delete a user
     *
     * @param user_id the unique identifier of the user to be deleted
     * @return true if the user deletion is successful otherwise false
     */
    boolean delete(int user_id);

    /**
     * Edit user information based on the new user object
     *
     * @param user the new user object with update info
     * @return true if the user info is successful edited otherwise false
     */
    boolean edit(User user);
}