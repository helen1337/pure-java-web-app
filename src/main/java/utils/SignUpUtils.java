package utils;

import dao.UserDao;
import daoImpl.UserDaoImpl;
import models.User;

import javax.servlet.http.HttpServletRequest;

public class SignUpUtils {
    public static String signUp(HttpServletRequest request){

        String login = request.getParameter("login");

        UserDao dao = UserDaoImpl.getInstance();
        if (dao.isExist(login)) return "exist";

        User newUser = new User();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String city = request.getParameter("city");

        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setCity(city);

        if (dao.signUp(newUser)) return "success";
        return "fail";
    }
}
