package dao;

import models.Article;

import java.util.List;

/**
 * Defines operations for managing articles in a data store
 */
public interface ArticleDao {

    /**
    * Get a list of all themes
     *
     * @return List of all themes, or null if not found
     */
    List<String> getAllTheme();

    /**
     * Add a new article
     *
     * @param a the article to be added
     * @return True if the article is successfully added otherwise false
     */
    boolean addArticle(Article a);

    /**
     * Delete an article based on its unique identifier
     *
     * @param id the unique identifier of the article to be deleted
     * @return true if the deletion is successful otherwise false
     */
    boolean deleteArticle(String id);

    /**
     * Get a list of all articles
     *
     * @return list of the all articles, or null if not found
     */
    List<Article> getAllArticle();

    /**
     * Retrieves a list of articles
     * based on a specified column and its value
     *
     * @param column the column by which to filter articles
     * @param value the value to match in the specified column
     * @return a list of articles that match the specified criteria,
     * or null if not found
     */
    List<Article> getArticleByColumn(String column, String value);

    /**
     * Retrieves an article based on its unique identifier
     *
     * @param id the unique identifier of the article
     * @return the article with the specified identifier,
     * or null if not found
     */
    Article getById(String id);

    /**
     * Edit an existing article
     *
     * @param article the updated article
     * @return true if the editing is successful otherwise false
     */
    boolean editArticle(Article article);
}