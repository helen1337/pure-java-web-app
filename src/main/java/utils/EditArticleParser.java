package utils;

import models.Article;

import javax.servlet.http.HttpServletRequest;

public class EditArticleParser {
    public static Article parsArticle(HttpServletRequest request) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String theme = request.getParameter("theme");
        String content = request.getParameter("content");
        int id = Integer.parseInt(request.getParameter("id"));

        Article editArticle = new Article();

        editArticle.setTitle(title);
        editArticle.setAuthor(author);
        editArticle.setTheme(theme);
        editArticle.setContent(content);
        editArticle.setId(id);

        return editArticle;
    }
}
