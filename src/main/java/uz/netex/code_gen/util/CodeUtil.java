package uz.netex.code_gen.util;

public class CodeUtil {

    public static String removeComments(String text) {
        StringBuilder noComment = new StringBuilder();
        for (String line : text.lines().toList()) {
            int i = line.indexOf("//");
            if (i > -1) {
                line = line.substring(0, i);
            }
            if (line.trim().equals(""))
                continue;
            noComment.append(line).append("\n");
        }

        int begin = noComment.indexOf("/*");
        while (begin > -1) {
            int end = noComment.indexOf("*/", begin + 2);
            if (end > 0) {
                noComment.delete(begin, end + 2);
            } else {
                noComment.delete(begin, noComment.length());
                break;
            }
            begin = noComment.indexOf("/*");
        }
        return noComment.toString();
    }

    public static String remove2Probels(String content) {
        content = content.trim();
        while (content.contains("  ")) {
            content = content.replaceAll(" {2}", " ");
        }
        return content;
    }

    public static int findCloseBrc(String str, int openIndex) {
        try {
            char openBkt = str.charAt(openIndex);
            char closeBkt;
            switch (openBkt) {
                case '{' -> closeBkt = '}';
                case '(' -> closeBkt = ')';
                case '[' -> closeBkt = ']';
                default -> {
                    return -1;
                }
            }

            int v = 1;
            int cursor = openIndex + 1;
            while (cursor < str.length()) {
                if (str.charAt(cursor) == openBkt)
                    v++;
                else if (str.charAt(cursor) == closeBkt)
                    v--;
                if (v == 0)
                    return cursor;
                cursor++;
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static String extractJavadoc(String text) {
        if (text.startsWith("/**")) {
            int endOfComment = text.indexOf("*/", 3) + 2;
            return endOfComment == 1
                    ? text
                    : text.substring(0, endOfComment);
        }
        return "";
    }

}
