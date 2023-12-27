package dao;

import models.Article;

import java.util.List;
public interface ArticleDao {

    List<String> getAllTheme();

    Article addArticle(Article a);

    boolean deleteArticle(String id);

    List<Article> getAllArticle();

    List<Article> getArticleByColumn(String column, String value);

    Article getById(String id);

    boolean editArticle(Article article);
}