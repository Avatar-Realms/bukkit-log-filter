package net.avatar.realms.blf;

import java.util.regex.Pattern;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class FiltersHandler {

    public static final String HOUR_REGEX = "^\\[\\d{2}:\\d{2}:\\d{2}\\] ";
    public static final String THREAD_INFO = "\\[Server thread\\/INFO\\]: ";
    public static final String S_THREAD_WARN = "\\[Server thread\\/WARN\\]: ";
    public static final String THREAD_WARN = "\\[Thread-\\d+\\/WARN\\]: ";
    public static final String CRAFT_WARN = "\\[Craft Scheduler Thread - \\d+\\/WARN\\]: ";
    public static final String CRAFT_INFO = "\\[Craft Scheduler Thread - \\d+\\/INFO\\]: ";
    public static final String THREAD_ERROR = "\\[Server thread\\/ERROR\\]: ";

    private static final Pattern[] DISCONNECTIONS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "Disconnecting com\\.mojang\\.authlib"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ lost connection: "),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ left the game")};

    private static final Pattern[] CONNECTIONS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ joined the game"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ logged in with entity id \\d+"),
            Pattern.compile(HOUR_REGEX + "\\[User Authenticator #\\d+\\/INFO\\]: UUID of player \\S+ is")};

    private static final Pattern[] COMMANDBLOCKS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\[@: "),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "CommandBlock at [\\d,\\-]+ issued server command: .+")};

    private static final Pattern[] DEATHS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ was killed by Witch using magic"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ tried to swim in lava"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ was blown up by \\S+"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ was slain by \\S+"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ burned to death"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ went up in flames"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ was shot by \\S+"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ starved to death"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ hit the ground too hard"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ was killed trying to hurt \\S+"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ was burnt to a crisp whilst fighting \\S+"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ fell from a high place")};

    private static final Pattern[] CONSOLES = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "CONSOLE issued server command: .+")};

    private static final Pattern[] EXCEPTIONS = {Pattern.compile(HOUR_REGEX + CRAFT_WARN + ".*Exception.*"),
            Pattern.compile(HOUR_REGEX + CRAFT_WARN + "\\s+at .+\\(.+:.+\\)"),
            Pattern.compile(HOUR_REGEX + CRAFT_WARN + "\\s+\\.\\.\\. \\d+ more"),
            Pattern.compile(HOUR_REGEX + THREAD_WARN + ".*Exception.*"),
            Pattern.compile(HOUR_REGEX + THREAD_WARN + "\\s+at .+\\(.+:.+\\)"),
            Pattern.compile(HOUR_REGEX + THREAD_WARN + "\\s+\\.\\.\\. \\d+ more")};

    private static final Pattern[] PLAYER_COMMAND = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\S+ issued server command: .+")};

    public static boolean matchesDisconnection(String line) {
        for (Pattern disconnection : DISCONNECTIONS) {
            if (disconnection.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesConnection(String line) {
        for (Pattern connection : CONNECTIONS) {
            if (connection.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesCommandBlock(String line){
        for (Pattern commandBlock : COMMANDBLOCKS) {
            if (commandBlock.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesDeath(String line ) {
        for (Pattern death : DEATHS) {
            if (death.matcher(line).find()) {
                return true;
            }
        }

        return false;
    }

    public static boolean matchesConsoleCommand(String line) {
        for (Pattern console : CONSOLES) {
            if (console.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesException(String line) {
        for (Pattern exception : EXCEPTIONS) {
            if (exception.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesPlayerCommand(String line) {
        for (Pattern pattern : PLAYER_COMMAND) {
            if (pattern.matcher(line).find() && !CONSOLES[0].matcher(line).find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesPattern(String line, Pattern custom) {
        return custom.matcher(line).find();
    }
}
