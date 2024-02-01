package utils;

import models.Comment;
import models.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for parsing comment-related data from HttpServletRequest.
 */
public class CommentParser {

    /**
     * Parses comment data from the HttpServletRequest object.
     *
     * @param request The HttpServletRequest object containing comment data
     * @return A Comment object populated with parsed data
     */
    public static Comment parsComment(HttpServletRequest request) {

        String article_idReq = request.getParameter("article_id");
        String nickname = request.getParameter("nickname");
        String content = request.getParameter("content");

        int article_id = -1;
        if (!StringUtils.isEmpty(article_idReq)) {
            article_id = Integer.parseInt(article_idReq);
        }

        int user_id = -1;
        String status = request.getParameter("status");
        if (status.equals("user")) {
            User user = SessionManager.getUserFromSession(request);
            user_id = user.getUser_id();
        }
        return new Comment(-1, article_id, user_id, nickname, content, "");
    }
}