package service;

import dao.ArticleDao;
import daoImpl.ArticleDaoImpl;
import models.Article;
import utils.ArticleUtils;

import java.util.List;
import java.util.Objects;

/**
 * Service class for managing articles
 */
public class ArticleService {
    private ArticleDao articleDao;
    private static ArticleService instance;
    private ArticleService() {
        articleDao = ArticleDaoImpl.getInstance();
    }

    /**
     * Returns the singleton instance of ArticleService
     *
     * @return The ArticleService instance
     */
    public static final ArticleService getInstance() {
        if (instance == null) {
            try {
                instance = new ArticleService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Adds a new article
     *
     * @param article The article to be added
     * @return True if the article is successfully added otherwise false
     */
    public boolean addArticle(Article article) {
        return Objects.nonNull(articleDao.addArticle(article));
    }

    /**
     * Retrieves a list of articles based on a specified column and its value
     *
     * @param column The column by which to filter articles
     * @param value  The value to match in the specified column
     * @return A list of articles that match the specified criteria
     */
    public List<Article> getArticle(String column, String value) {
        List<Article> list = articleDao.getArticleByColumn(column, value);
        for (Article a : list) {
            ArticleUtils.cutContent(list);
        }
        return list;
    }

    /**
     * Retrieves a list of all articles with truncated content
     *
     * @return A list of all articles with truncated content
     */
    public List<Article> getAllArticle() {
        List<Article> list = articleDao.getAllArticle();
        for (Article a : list) {
            ArticleUtils.cutContent(list);
        }
        return list;
    }

    /**
     * Retrieves a list of all unique themes
     *
     * @return A list of all unique themes
     */
    public List<String> getAllTheme() {
        return articleDao.getAllTheme();
    }

    /**
     * Retrieves an article based on its unique identifier
     *
     * @param id The unique identifier of the article
     * @return The article with the specified identifier
     * or null if not found
     */
    public Article getArticle(String id) {
        return articleDao.getById(id);
    }

    /**
     * Edits an existing article
     *
     * @param article The updated article
     * @return True if the article is successfully edited
     * otherwise false
     */
    public boolean editArticle(Article article) {
        return articleDao.editArticle(article);
    }

    /**
     * Deletes an article based on its unique identifier
     *
     * @param id The unique identifier of the article to be deleted
     * @return True if the article is successfully deleted
     * otherwise false
     */
    public boolean deleteArticle(String id) {
        return articleDao.deleteArticle(id);
    }
}
