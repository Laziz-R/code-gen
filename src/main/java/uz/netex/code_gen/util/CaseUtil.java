package uz.netex.code_gen.util;

public class CaseUtil {
    public static final String
            CAMEL_CASE = "camelCase",
            PASCAL_CASE = "PascalCase",
            SNAKE_CASE = "snake_case",
            KEBAB_CASE = "kebab-case",
            UPPER_CASE = "UPPER_CASE";

    public static String camelToSnake(String camel) {
        if (camel == null)
            return null;
        StringBuilder snake = new StringBuilder();
        for (int i = 0; i < camel.length(); i++) {
            char c = camel.charAt(i);
            if ('A' <= c && c <= 'Z') {
                snake.append("_").append((char) (c + 32));
            } else {
                snake.append(c);
            }
        }
        return snake.toString();
    }

    public static String camelToPascal(String camel) {
        if (camel == null || camel.equals(""))
            return camel;
        char c = camel.charAt(0);
        if ('a' <= c && c <= 'z')
            c -= 32;
        return c + camel.substring(1);
    }

    public static String camelToKebab(String camel) {
        if (camel == null)
            return null;
        StringBuilder kebab = new StringBuilder();
        for (int i = 0; i < camel.length(); i++) {
            char c = camel.charAt(i);
            if ('A' <= c && c <= 'Z') {
                kebab.append("-").append((char) (c + 32));
            } else {
                kebab.append(c);
            }
        }
        return kebab.toString();
    }

    public static String kebabToPascal(String kebab) {
        if (kebab == null)
            return null;
        kebab = "-" + kebab.toLowerCase();
        StringBuilder pascal = new StringBuilder();
        for (int i = 0; i < kebab.length(); i++) {
            char c = kebab.charAt(i);
            if (c == '-') continue;
            if (kebab.charAt(i - 1) == '-')
                if ('a' <= c && c <= 'z')
                    c -= 32;
            pascal.append(c);
        }
        return pascal.toString();
    }

    public static String pascalToSnake(String pascal) {
        if (pascal == null)
            return null;
        StringBuilder snake = new StringBuilder();
        for (int i = 0; i < pascal.length(); i++) {
            char c = pascal.charAt(i);
            if ('A' <= c && c <= 'Z')
                snake.append("_").append((char) (c + 32));
            else
                snake.append(c);
        }
        if (snake.toString().startsWith("_"))
            snake = new StringBuilder(snake.substring(1));
        return snake.toString();
    }

    public static String pascalToCamel(String pascal) {
        if (pascal == null || pascal.equals(""))
            return pascal;
        char c = pascal.charAt(0);
        if ('A' <= c && c <= 'Z')
            c += 32;
        return c + pascal.substring(1);
    }

    public static String pascalToKebab(String pascal) {
        if (pascal == null)
            return null;
        StringBuilder kebab = new StringBuilder();
        for (int i = 0; i < pascal.length(); i++) {
            char c = pascal.charAt(i);
            if ('A' <= c && c <= 'Z')
                kebab.append("-").append((char) (c + 32));
            else
                kebab.append(c);
        }
        if (kebab.toString().startsWith("-"))
            kebab = new StringBuilder(kebab.substring(1));
        return kebab.toString();
    }

    public static String snakeToCamel(String snake) {
        if (snake == null || snake.equals(""))
            return snake;
        snake = snake.toLowerCase();
        StringBuilder camel = new StringBuilder(snake.charAt(0) + "");
        for (int i = 1; i < snake.length(); i++) {
            char c = snake.charAt(i);
            if (c == '_') continue;
            if (snake.charAt(i - 1) == '_') {
                if ('a' <= c && c <= 'z')
                    c -= 32;
            }
            camel.append(c);
        }
        return camel.toString();
    }

    public static String snakeToPascal(String snake) {
        if (snake == null)
            return null;
        snake = "_" + snake.toLowerCase();
        StringBuilder pascal = new StringBuilder();
        for (int i = 1; i < snake.length(); i++) {
            char c = snake.charAt(i);
            if (c == '_') continue;
            if (snake.charAt(i - 1) == '_') {
                if ('a' <= c && c <= 'z')
                    c -= 32;
            }
            pascal.append(c);
        }
        return pascal.toString();
    }

    public static String snakeToKebab(String snake) {
        return snake.toLowerCase().replaceAll("_", "-");
    }
}
