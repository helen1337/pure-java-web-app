package utils;

import models.Article;

import java.util.List;

/**
 * Utility class for performing operations on lists of Article objects
 */
public class ArticleUtils {

    /**
     * Cuts the content of each Article in the list to a maximum length of 350 characters.
     * If the content exceeds 350 characters, it is truncated and "..." is appended.
     *
     * @param list The list of Article objects to be processed
     * @return The modified list of Article objects with truncated content
     */
    public static List<Article> cutContent(List<Article> list) {
        for (Article a : list) {
            if (a.getContent() != null && a.getContent().length() > 350) {
                a.setContent(a.getContent().substring(0, 348) + "...");
            }
        }
        return list;
    }
}