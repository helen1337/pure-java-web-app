package utils;

import dao.UserDao;
import daoImpl.UserDaoImpl;
import models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtils {
    public static boolean login(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserDao dao = UserDaoImpl.getInstance();
        User user = dao.login(login, password);

        if (user == null) return false;

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return true;
    }
}
