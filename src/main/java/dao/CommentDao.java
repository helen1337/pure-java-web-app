package dao;

import models.Comment;

import java.util.List;

/**
 * Defines operations for managing comments in a data store
 */
public interface CommentDao {

    /**
     * Add a new comment
     *
     * @param comment the comment to be added
     * @return true if the adding is successful otherwise false
     */
    boolean addComment(Comment comment);

    /**
     * Retrieves a list of comments based on a specified article_id
     *
     * @param article_id the unique identifier of the article
     * @return a list of comments that match the specified article's identifier,
     * or null if not found
     */
    List<Comment> getCommentByArticleId(int article_id);

    /**
     * Delete a comment based on its unique identifier
     *
     * @param id the unique identifier of the comment to be deleted
     * @return true if the deletion is successful otherwise false
     */
    boolean deleteComment(String id);
}