package dao;

import models.User;
public interface UserDao {
    boolean isExist(String login);
    boolean signUp(User newUser);
    User login(String username, String password);

    boolean delete(String user_id);

    boolean edit(User user);
}