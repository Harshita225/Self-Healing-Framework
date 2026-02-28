package utils;

public class StringSimilarity {

    // Levenshtein Distance
    public static int levenshteinDistance(String a, String b) {

        if (a == null || b == null) return 0;

        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {

                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;

                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1,
                                     dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + cost
                    );
                }
            }
        }

        return dp[a.length()][b.length()];
    }

    // Similarity Percentage (0–100)
    public static double similarityPercent(String a, String b) {

        if (a == null || b == null) return 0;

        int maxLength = Math.max(a.length(), b.length());
        if (maxLength == 0) return 100;

        int distance = levenshteinDistance(a, b);

        return (1.0 - ((double) distance / maxLength)) * 100;
    }
}