package utils;

import models.Article;

import java.util.List;

public class ArticleUtils {

    public static List<Article> cutContent(List<Article> list) {
        for (Article a : list) {
            if (a.getContent() != null && a.getContent().length() > 350) {
                a.setContent(a.getContent().substring(0, 348) + "...");
            }
        }
        return list;
    }

}

