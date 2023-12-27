package utils;

import dao.ArticleDao;
import daoImpl.ArticleDaoImpl;
import models.Article;

import javax.servlet.http.HttpServletRequest;

public class NewArticleUtils {
    public static boolean createArticle(HttpServletRequest request) {

        String title =  request.getParameter("title");
        String author =  request.getParameter("author");
        String theme =  request.getParameter("theme");
        String content =  request.getParameter("content");

        ArticleDao articleDao = ArticleDaoImpl.getInstance();
        Article newArticle = new Article();

        newArticle.setTitle(title);
        newArticle.setAuthor(author);
        newArticle.setTheme(theme);
        newArticle.setContent(content);

        Article article = articleDao.addArticle(newArticle);
        return article != null;
    }
}
