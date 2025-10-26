import java.util.*;

public final class CodeFormatter {

    private static final int SPACE_UNIT = 4;

    private CodeFormatter() {}

    public static String format(String sourceCode) {
        String[] lines = sourceCode.split("\n", -1);
        List<String> formattedLines = new ArrayList<>(lines.length);
        Deque<Integer> indentationLevels = new ArrayDeque<>();
        int lastCodeLineIndex = -1;
        boolean insideMultiLineComment = false;

        for (String line : lines) {
            String strippedLine = line.trim();

            if (insideMultiLineComment) {
                formattedLines.add(line);
                if (strippedLine.contains("*/")) insideMultiLineComment = false;
                continue;
            }

            if (strippedLine.startsWith("/*")) {
                insideMultiLineComment = !strippedLine.contains("*/");
                formattedLines.add(line);
                continue;
            }

            if (strippedLine.startsWith("//") || strippedLine.isEmpty()) {
                formattedLines.add(line);
                continue;
            }

            int commentStartIndex = findCommentStart(line);
            String codePart = line;
            String commentPart = "";

            if (commentStartIndex >= 0) {
                commentPart = line.substring(commentStartIndex);
                codePart = line.substring(0, commentStartIndex);
            }

            if (!commentPart.isEmpty() && commentPart.contains("/*") && !commentPart.contains("*/")) {
                insideMultiLineComment = true;
            }

            String code = codePart.trim();
            int currentIndentation = countLeadingSpaces(line) / SPACE_UNIT;
            boolean blockEnd = code.equals("end");
            boolean blockStart = code.endsWith(":");

            if (!blockEnd) {
                while (!indentationLevels.isEmpty() && currentIndentation < indentationLevels.peek()) {
                    if (lastCodeLineIndex >= 0)
                        formattedLines.set(lastCodeLineIndex, formattedLines.get(lastCodeLineIndex) + "}");
                    indentationLevels.pop();
                }
            }

            StringBuilder newLine = new StringBuilder();
            newLine.append(" ".repeat(currentIndentation * SPACE_UNIT));

            if (blockStart) {
                String header = removeTrailingSpaces(code.substring(0, code.length() - 1));
                newLine.append(header).append(" {");
                indentationLevels.push(currentIndentation + 1);
            } else if (blockEnd) {
                if (!indentationLevels.isEmpty()) {
                    newLine.append("}");
                    indentationLevels.pop();
                }
            } else {
                newLine.append(code);
                if (!code.endsWith(";") && !code.endsWith("{") && !code.endsWith("}")) {
                    newLine.append(";");
                }
            }

            if (!commentPart.isEmpty()) {
                if (!Character.isWhitespace(commentPart.charAt(0))) newLine.append(' ');
                newLine.append(commentPart);
            }

            formattedLines.add(newLine.toString());
            lastCodeLineIndex = formattedLines.size() - 1;
        }

        while (!indentationLevels.isEmpty() && lastCodeLineIndex >= 0) {
            formattedLines.set(lastCodeLineIndex, formattedLines.get(lastCodeLineIndex) + "}");
            indentationLevels.pop();
        }

        return String.join("\n", formattedLines);
    }

    private static int countLeadingSpaces(String line) {
        int spaces = 0;
        while (spaces < line.length() && line.charAt(spaces) == ' ') spaces++;
        return spaces;
    }

    private static String removeTrailingSpaces(String s) {
        int i = s.length();
        while (i > 0 && Character.isWhitespace(s.charAt(i - 1))) i--;
        return (i == s.length()) ? s : s.substring(0, i);
    }

    private static int findCommentStart(String line) {
        boolean singleQuote = false;
        boolean doubleQuote = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '\\' && (singleQuote || doubleQuote)) {
                i++;
                continue;
            }

            if (!doubleQuote && ch == '\'') singleQuote = !singleQuote;
            else if (!singleQuote && ch == '"') doubleQuote = !doubleQuote;

            if (singleQuote || doubleQuote) continue;

            if (ch == '/' && i + 1 < line.length()) {
                char nextChar = line.charAt(i + 1);
                if (nextChar == '/' || nextChar == '*') return i;
            }
        }

        return -1;
    }
}
