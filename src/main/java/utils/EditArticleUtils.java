package utils;

import models.Article;
import service.ArticleService;

import javax.servlet.http.HttpServletRequest;

public class EditArticleUtils {

    public static boolean editArticle(HttpServletRequest request) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String theme = request.getParameter("theme");
        String content = request.getParameter("content");
        int id = Integer.parseInt(request.getParameter("id"));


        ArticleService articleService = ArticleService.getInstance();
        Article editArticle = new Article();

        editArticle.setTitle(title);
        editArticle.setAuthor(author);
        editArticle.setTheme(theme);
        editArticle.setContent(content);
        editArticle.setId(id);

        return articleService.editArticle(editArticle);
    }
}
