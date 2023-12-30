package daoImpl;

import dao.ArticleDao;
import models.Article;
import utils.DBUtils;

import java.sql.*;
import java.util.*;

public class ArticleDaoImpl implements ArticleDao {

    private Connection conn;
    private static ArticleDao instance;

    private ArticleDaoImpl() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blog",
                "root", "root");
    }

    public static final ArticleDao getInstance() {
        if (instance == null) {
            try {
                instance = new ArticleDaoImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public List<String> getAllTheme() {
        String sql = "select distinct(theme) from article order by theme";
        List<String> themeList = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            themeList = new ArrayList();
            while (rs.next()) {
                themeList.add(rs.getString(1));
            }
            DBUtils.close(ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themeList;
    }
    @Override
    public Article addArticle(Article a) {
        String sql = "insert into article(title, author, theme, content, time) " +
                "values (?,?,?,?,CURDATE()) ";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getAuthor());
            ps.setString(3, a.getTheme());
            ps.setString(4, a.getContent());
            ps.executeUpdate();
            DBUtils.close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getLastArticle();
    }

    private Article getLastArticle() {
        String sql = "select * from article order by time desc limit 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("theme"),
                        rs.getString("time"),
                        rs.getString("content"));
                DBUtils.close(ps, rs);
                return article;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteArticle(String id) {
        String sql = "delete from article where id=?";
        PreparedStatement ps;
        int result = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            result = ps.executeUpdate();
            DBUtils.close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public boolean editArticle(Article article) {
        String sql = "update article set title=?, author=?, theme=?, content=? where id=" + article.getId();
        PreparedStatement ps = null;
        int result = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getAuthor());
            ps.setString(3, article.getTheme());
            ps.setString(4, article.getContent());
            result = ps.executeUpdate();
            DBUtils.close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<Article> getAllArticle() {
        List<Article> articlesList = new ArrayList<>();

        String sql = "select * from article";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("theme"),
                        rs.getString("time"),
                        rs.getString("content"));
                articlesList.add(article);
            }
            DBUtils.close(ps, rs);
//            Collections.sort(articlesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articlesList;
    }

    @Override
    public List<Article> getArticleByColumn(String column, String value) {
        List<Article> articlesList = null;
        Article a = null;
        String sql = "select * from article where " + column + " = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            articlesList = new ArrayList<>();
            while (rs.next()) {
                a = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("theme"),
                        rs.getString("time"),
                        rs.getString("content"));
                articlesList.add(a);
            }
            DBUtils.close(ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articlesList;
    }

    @Override
    public Article getById(String id) {

        Article article = null;
        String sql = "select * from article where id=" + id;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("theme"),
                        rs.getString("time"),
                        rs.getString("content"));
            }
            DBUtils.close(ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }
}
