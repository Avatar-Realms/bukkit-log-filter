package net.avatar.realms.blf.models;

import java.util.regex.Pattern;

/**
 * Created by Nokorbis on 16/02/2016.
 */
public class CustomString {

    private String firstPart; // If something was entered in the combo box
    private String secondPart; // If something was entered in the combo box
    private String thirdPart;

    private String name;

    private boolean regex;
    private boolean caseSensitive;

    public CustomString () {

    }

    public CustomString(String firstPart, String secondPart, String thirdPart, boolean regex, boolean caseSensitive) {
        this(firstPart, secondPart, thirdPart, regex, caseSensitive, null);
    }

    public CustomString(String firstPart, String secondPart, String thirdPart, boolean regex, boolean caseSensitive, String name) {
        this();
        this.firstPart = firstPart;
        this.secondPart = secondPart;
        this.thirdPart = thirdPart;
        this.regex = regex;
        this.caseSensitive = caseSensitive;

        this.name = name;
    }

    public Pattern compile() {
        Pattern pattern = null;

        StringBuilder builder = new StringBuilder();
        builder.append(firstPart);
        builder.append(secondPart);

        String third = thirdPart;

        if (!regex) {
            if (firstPart != null && secondPart != null
                    && !firstPart.isEmpty() && !secondPart.isEmpty()) {
                builder.append(".*");
            }
            third = escape(third);
        }

        builder.append(third);

        if (caseSensitive) {
            pattern = Pattern.compile(builder.toString());
        }
        else {
            pattern = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);
        }

        return pattern;
    }

    private String escape (String str) {
        str = str.replace("\\", "\\\\");
        str = str.replace(".", "\\.");
        str = str.replace("+", "\\+");
        str = str.replace("-", "\\-");
        str = str.replace("*", "\\*");
        str = str.replace("/", "\\/");
        str = str.replace("^", "\\^");
        str = str.replace("$", "\\$");
        str = str.replace("(", "\\(");
        str = str.replace(")", "\\)");
        str = str.replace("[", "\\[");
        str = str.replace("]", "\\]");
        str = str.replace("{", "\\{");
        str = str.replace("}", "\\}");
        str = str.replace("|", "\\|");
        str = str.replace("?", "\\?");
        return str;
    }

    public String getFirstPart() {
        return firstPart;
    }

    public void setFirstPart(String firstPart) {
        this.firstPart = firstPart;
    }

    public String getSecondPart() {
        return secondPart;
    }

    public void setSecondPart(String secondPart) {
        this.secondPart = secondPart;
    }

    public String getThirdPart() {
        return thirdPart;
    }

    public void setThirdPart(String thirdPart) {
        this.thirdPart = thirdPart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
}
