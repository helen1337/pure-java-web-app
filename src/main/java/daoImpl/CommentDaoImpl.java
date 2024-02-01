package daoImpl;

import dao.CommentDao;
import models.Comment;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing comments
 */
public class CommentDaoImpl implements CommentDao {
    private Connection conn;
    private static CommentDao instance;

    /*
     * (non-Javadoc)
     *
     * @see utils.DBUtils#getConnection()
     */
    private CommentDaoImpl() throws SQLException, ClassNotFoundException {
        conn = DBUtils.getConnection();
    }

    /**
     * Returns the singleton instance of CommentDao
     *
     * @return CommentDao instance
     */
    public static final CommentDao getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            try {
                instance = new CommentDaoImpl();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Failed to connect to the database");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new ClassNotFoundException("Service error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Add a new comment
     *
     * @param comment the comment to be added
     * @return true if the adding is successful otherwise false
     */
    @Override
    public boolean addComment(Comment comment) {
        String sql = "insert into comment(article_id, user_id, nickname, content, time)" +
                "values(?,?,?,?,CURDATE())";
        int result = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, comment.getArticle_id());
            ps.setInt(2, comment.getUser_id());
            ps.setString(3, comment.getNickname());
            ps.setString(4, comment.getContent());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    /**
     * Retrieves a list of comments based on a specified article_id
     *
     * @param article_id the unique identifier of the article
     * @return a list of comments that match the specified article's identifier,
     * or null if not found
     */
    @Override
    public List<Comment> getCommentByArticleId(int article_id) {
        List<Comment> commentList = new ArrayList<>();
        Comment comment;
        String sql = "select * from comment where article_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, article_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comment = new Comment(
                            rs.getInt("id"),
                            rs.getInt("article_id"),
                            rs.getInt("user_id"),
                            rs.getString("nickname"),
                            rs.getString("content"),
                            rs.getString("time"));
                    commentList.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    /**
     * Delete a comment based on its unique identifier
     *
     * @param id the unique identifier of the comment to be deleted
     * @return true if the deletion is successful otherwise false
     */
    @Override
    public boolean deleteComment(String id) {
        String sql = "delete from comment where id=?";
        int result = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }
}