package service;

import dao.CommentDao;
import daoImpl.CommentDaoImpl;
import models.Comment;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for managing comments
 */
public class CommentService {
    private CommentDao commentDao;
    private static CommentService instance;
    private CommentService() throws SQLException, ClassNotFoundException {
        commentDao = CommentDaoImpl.getInstance();
    }

    /**
     * Returns the singleton instance of CommentService
     *
     * @return The CommentService instance
     */
    public static final CommentService getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new CommentService();
        }
        return instance;
    }

    /**
     * Add a new comment
     *
     * @param comment the comment to be added
     * @return true if the adding is successful otherwise false
     */
    public boolean addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    /**
     * Retrieves a list of comments based on a specified article_id
     *
     * @param article_id the unique identifier of the article
     * @return a list of comments that match the specified article's identifier,
     * or null if not found
     */
    public List<Comment> getComments(String article_id) {
        return commentDao.getCommentByArticleId(Integer.parseInt(article_id));
    }

    /**
     * Delete a comment based on its unique identifier
     *
     * @param id the unique identifier of the comment to be deleted
     * @return true if the deletion is successful otherwise false
     */
    public boolean deleteComment(String id) {
        return commentDao.deleteComment(id);
    }
}