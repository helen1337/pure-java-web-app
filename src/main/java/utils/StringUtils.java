package utils;

/**
 * Utility class for common string-related operations
 */
public class StringUtils {

    /**
     * Checks if a given string is empty or null after trimming
     *
     * @param str The string to be checked
     * @return true if the string is empty or null after trimming otherwise false
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals(""))
            return true;
        return false;
    }
}
