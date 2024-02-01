package utils;

import models.Article;
import models.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Utility class for parsing article-related information from a HttpServletRequest
 */
public class ArticleParser {

    /**
     * Parses the article information from the HttpServletRequest parameters
     *
     * @param request The HttpServletRequest containing article-related parameters
     * @return Article object populated with information from the request parameters
     */
    public static Article parsArticle(HttpServletRequest request) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String theme = request.getParameter("theme");
        String content = request.getParameter("content");
        int id = -1;
        if (Objects.nonNull(request.getParameter("id"))) {
            id = Integer.parseInt(request.getParameter("id"));
        }
        int user_id = 0;
        if (SessionManager.isUserInSession(request)) {
            User user = SessionManager.getUserFromSession(request);
            user_id = user.getUser_id();
        }
        Article article = new Article();
        article.setTitle(title);
        article.setAuthor(author);
        article.setTheme(theme);
        article.setContent(content);
        article.setId(id);
        article.setUser_id(user_id);
        return article;
    }
}