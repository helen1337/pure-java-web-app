package models;

/**
 * The class represents a model for an article.
 * <p>It encapsulates various attributes of an article, including its unique identifier (ID),
 * user unique identifier (user_id), title, author, theme, timestamp and content.</p>
 */
public class Article {
    private int id;
    private int user_id;
    private String title;
    private String author;
    private String theme;
    private String time;
    private String content;
    public Article() {}
    public Article(int id, int user_id, String title, String author, String theme, String time, String content) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.author = author;
        this.theme = theme;
        this.time = time;
        this.content = content;
    }
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", user_id=" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", theme='" + theme + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}