package models;

/**
 * The class represents a model for an article.
 * <p>It encapsulates various attributes of an article, including its unique identifier (ID),
 * title, author, theme, timestamp and content.</p>
 */
public class Article {
    private int id;
    private String title;
    private String author;
    private String theme;
    private String time;
    private String content;
    public Article() {}
    public Article(int id, String title, String author, String theme, String time, String content) {
        this.id = id;
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