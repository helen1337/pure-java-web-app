package service;

import dao.ArticleDao;
import daoImpl.ArticleDaoImpl;
import models.Article;
import utils.ArticleUtils;


import java.util.List;
import java.util.Objects;


public class ArticleService {

    private ArticleDao articleDao;

    private static ArticleService instance;

    private ArticleService() {
        articleDao = ArticleDaoImpl.getInstance();
    }

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

    public boolean addArticle(Article article) {
        return Objects.nonNull(articleDao.addArticle(article));
    }

    public List<Article> getArticle(String column, String value) {
        List<Article> list = articleDao.getArticleByColumn(column, value);
        for (Article a : list) {
            ArticleUtils.cutContent(list);
        }
        return list;
    }

    public List<Article> getAllArticle() {
        List<Article> list = articleDao.getAllArticle();
        for (Article a : list) {
            ArticleUtils.cutContent(list);
        }
        return list;
    }
    public List<String> getAllTheme() {
        return articleDao.getAllTheme();
    }

    public Article getArticle(String id) {
        return articleDao.getById(id);
    }

    public boolean editArticle(Article article) {
        return articleDao.editArticle(article);
    }

    public boolean deleteArticle(String id) {
        return articleDao.deleteArticle(id);
    }
}
