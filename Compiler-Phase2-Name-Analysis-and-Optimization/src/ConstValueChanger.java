import java.util.*;
import java.util.regex.*;

public class ConstValueChanger {
    private final Map<String, String> typedefs = new HashMap<>();
    private final Map<String, String> defines = new HashMap<>();
    private final Map<String, String> constVars = new HashMap<>();

    public String transform(String cCode) {
        typedefs.clear();
        defines.clear();
        constVars.clear();

        List<String> lines = new ArrayList<>(List.of(cCode.split("\\n")));
        List<String> resultLines = new ArrayList<>();

        Pattern typedefPattern = Pattern.compile("\\btypedef\\s+(.+?)\\s+(\\w+)\\s*;");
        Pattern definePattern = Pattern.compile("#define\\s+(\\w+)\\s+([^;\\n]+)");
        Pattern constPattern = Pattern.compile("\\bconst\\s+(\\w+)\\s+(\\w+)\\s*=\\s*([^;]+);");

        // First pass: collect definitions
        for (String line : lines) {
            String trimmed = line.trim();

            Matcher typedefMatcher = typedefPattern.matcher(trimmed);
            if (typedefMatcher.matches()) {
                typedefs.put(typedefMatcher.group(2), typedefMatcher.group(1));
            }

            Matcher defineMatcher = definePattern.matcher(trimmed);
            if (defineMatcher.matches()) {
                defines.put(defineMatcher.group(1), defineMatcher.group(2).trim());
            }

            Matcher constMatcher = constPattern.matcher(trimmed);
            if (constMatcher.matches()) {
                constVars.put(constMatcher.group(2), constMatcher.group(3).trim());
            }
        }

        // Second pass: apply replacements
        for (String line : lines) {
            String processedLine = line;

            // Avoid replacing in typedef and define lines themselves
            if (!processedLine.trim().startsWith("typedef")) {
                for (Map.Entry<String, String> entry : typedefs.entrySet()) {
                    processedLine = processedLine.replaceAll("\\b" + Pattern.quote(entry.getKey()) + "\\b", entry.getValue());
                }
            }

            if (!processedLine.trim().startsWith("#define")) {
                for (Map.Entry<String, String> entry : defines.entrySet()) {
                    processedLine = processedLine.replaceAll("\\b" + Pattern.quote(entry.getKey()) + "\\b", entry.getValue());
                }
            }

            for (Map.Entry<String, String> entry : constVars.entrySet()) {
                processedLine = processedLine.replaceAll("\\b" + Pattern.quote(entry.getKey()) + "\\b", entry.getValue());
            }

            resultLines.add(processedLine);
        }

        return String.join("\n", resultLines);
    }
}
