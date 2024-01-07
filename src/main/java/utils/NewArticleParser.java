package utils;

import models.Article;

import javax.servlet.http.HttpServletRequest;

public class NewArticleParser {
    public static Article parsArticle(HttpServletRequest request) {
        String title =  request.getParameter("title");
        String author =  request.getParameter("author");
        String theme =  request.getParameter("theme");
        String content =  request.getParameter("content");

        Article newArticle = new Article();

        newArticle.setTitle(title);
        newArticle.setAuthor(author);
        newArticle.setTheme(theme);
        newArticle.setContent(content);

        return newArticle;
    }
}
