package models;

/**
 * The class represents a model for a comment.
 * <p>
 *     It encapsulates various attributes of a comment, including its unique
 *     identifier, article unique identifier, user unique identifier, nickname,
 *     content and timestamp.
 * </p>
 * */
public class Comment {
    private int id;
    private int article_id;
    private int user_id;
    private String nickname;
    private String content;
    private String time;
    public Comment(int id, int article_id, int user_id, String nickname, String content, String time) {
        this.id = id;
        this.article_id = article_id;
        this.user_id = user_id;
        this.nickname = nickname;
        this.content = content;
        this.time = time;
    }
    public Comment() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getArticle_id() {
        return article_id;
    }
    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}