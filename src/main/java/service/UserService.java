package service;

import dao.UserDao;
import daoImpl.UserDaoImpl;
import models.User;

import java.util.Properties;

/**
 * Service class for managing user-related operations
 */
public class UserService {
    private UserDao userDao;
    private static UserService instance;
    private UserService() {
        userDao = UserDaoImpl.getInstance();
    }

    /**
     * Returns the singleton instance of UserService
     *
     * @return The UserService instance
     */public static final UserService getInstance() {
        if (instance == null) {
            try {
                instance = new UserService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Checks if a user with the given credentials exists
     *
     * @param user The user whose existence is to be checked
     * @return true if the user exists, otherwise false
     */
    public boolean isUserExist(User user) {
        String login = user.getLogin();
        return userDao.isExist(login);
    }

    /**
     * Logs in a user based on the provided credentials
     *
     * @param credentials Properties object containing
     *                    login and password
     * @return The logged-in User object
     * or null if login fails
     */
    public User login(Properties credentials) {
        String login = credentials.getProperty("login");
        String password = credentials.getProperty("password");
        return userDao.login(login, password);
    }

    /**
     * Registers a new user in the system
     *
     * @param user The user to be registered
     * @return true if the registration is successful otherwise false
     */
    public boolean signUp(User user) {
        return userDao.signUp(user);
    }

    /**
     * Edits user information
     *
     * @param user The updated user information
     * @return true if the user information is successfully edited
     * otherwise false
     */
    public boolean editUser(User user) {
        return userDao.edit(user);
    }

    /**
     * Deletes a user based on their unique identifier
     *
     * @param user The user to be deleted
     * @return True if the user deletion is successful
     * otherwise false.
     */
    public boolean deleteUser(User user) {
        int user_id = user.getUser_id();
        return userDao.delete(user_id);
    }

}
