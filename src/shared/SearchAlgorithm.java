package shared;

import java.util.ArrayList;
import java.util.List;

public class SearchAlgorithm {

    public static List<Article> search(List<Article> dataset, String query) {
        List<Article> result = new ArrayList<>();

        for (Article article : dataset) {
            if (containsSubstring(article.getTitle(), query)) {
                result.add(article);
            }
        }
        return result;
    }

    private static boolean containsSubstring(String text, String pattern) {
        int[] lps = computeLPSArray(pattern);
        int i = 0, j = 0;

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == pattern.length()) {
                return true;
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }
        return false;
    }

    private static int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0, i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i++] = length;
            } else if (length != 0) {
                length = lps[length - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}