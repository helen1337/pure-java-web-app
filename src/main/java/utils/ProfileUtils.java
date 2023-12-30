package utils;

import dao.UserDao;
import daoImpl.UserDaoImpl;
import models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProfileUtils {

    public static boolean editProfile(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        String user_id = Integer.toString(sessionUser.getUser_id());
        String login = sessionUser.getLogin();

        User editUser = new User();
        editUser.setUser_id(Integer.parseInt(user_id));
        editUser.setFirstName(firstName);
        editUser.setLastName(lastName);
        editUser.setEmail(email);
        editUser.setLogin(login);
        editUser.setPassword(password);
        editUser.setCity(city);


        UserDao dao = UserDaoImpl.getInstance();

        if (!dao.edit(editUser)) return false;
        session.setAttribute("user", editUser);
        return true;
    }

    public static boolean deleteProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String user_id = Integer.toString(sessionUser.getUser_id());

        UserDao dao = UserDaoImpl.getInstance();

        if (!dao.delete(user_id)) return false;
        session.setAttribute("user", null);
        return true;
    }
}
