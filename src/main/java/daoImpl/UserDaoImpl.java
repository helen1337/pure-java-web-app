package daoImpl;

import dao.UserDao;
import models.User;
import org.apache.commons.beanutils.BeanUtils;
import utils.DBUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private Connection conn;

    private UserDaoImpl() throws ClassNotFoundException, SQLException {
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blog",
                "root", "root");
    }

    private static UserDao instance;

    public static final UserDao getInstance() {
        if (instance == null) {
            try {
                instance = new UserDaoImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean isExist(String login) {
        String sql = "select * from user where login=? ";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DBUtils.close(ps, rs);
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean signUp(User newUser) {
        String sql = "insert into user(firstName, lastName, email, login, password, city) " +
                "values (?,?,?,?,?,?) ";
        PreparedStatement ps = null;
        int result = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, newUser.getFirstName());
            ps.setString(2, newUser.getLastName());
            ps.setString(3, newUser.getEmail());
            ps.setString(4, newUser.getLogin());
            ps.setString(5, newUser.getPassword());
            ps.setString(6, newUser.getCity());
            result = ps.executeUpdate();

                DBUtils.close(ps);
        } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        return result > 0;
    }


    @Override
    public User login(String login, String password) {

        User user = null;
        String sql = "select * from user where login=? and password=? ";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                user = new User();
                map.put("user_id", rs.getString("user_id"));
                map.put("firstName", rs.getString("firstName"));
                map.put("lastName", rs.getString("lastName"));
                map.put("email", rs.getString("email"));
                map.put("login", rs.getString("login"));
                map.put("password", rs.getString("password"));
                map.put("city", rs.getString("city"));
                try {
                    BeanUtils.populate(user, map);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
            DBUtils.close(ps, rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean delete(String user_id) {
        String sql = "delete from user where user_id=?";
        PreparedStatement ps;
        int result = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user_id);
            result = ps.executeUpdate();
            DBUtils.close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public boolean edit(User user) {
        String sql = "update user set firstName=?, lastName=?, email=?, password=?, city=? where user_id=?";
        PreparedStatement ps;
        int result = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getCity());
            ps.setString(6, Integer.toString(user.getUser_id()));
            result = ps.executeUpdate();
            DBUtils.close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }
}
