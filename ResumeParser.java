package logic;

import java.util.regex.*;

public class ResumeParser {
    public static class Parsed {
        public String name, email, phone, text;
    }

    private static final Pattern EMAIL = Pattern.compile("\\b[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}\\b");
    private static final Pattern PHONE = Pattern.compile("\\b(\\+?\\d[\\d\\s()-]{6,}\\d)\\b");

    public static Parsed parse(String text) {
        Parsed p = new Parsed();
        p.text = text;
        Matcher m = EMAIL.matcher(text);
        if (m.find()) p.email = m.group();
        m = PHONE.matcher(text);
        if (m.find()) p.phone = m.group();

        // naive name guess = first capitalized line
        for (String line : text.split("\\r?\\n")) {
            line = line.trim();
            if (line.matches("^[A-Z][a-zA-Z ]{2,}$") && !line.contains("@")) {
                p.name = line;
                break;
            }
        }
        return p;
    }
}

