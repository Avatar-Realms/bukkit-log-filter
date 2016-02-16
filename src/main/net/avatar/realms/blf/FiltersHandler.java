package net.avatar.realms.blf;

import java.util.regex.Pattern;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class FiltersHandler {

    public static final String HOUR_REGEX = "^\\[\\d{2}:\\d{2}:\\d{2}\\] ";
    public static final String THREAD_INFO = "\\[Server thread\\/INFO\\]: ";
    public static final String THREAD_WARN = "\\[Server thread\\/INFO\\]: ";
    public static final String CRAFT_WARN = "\\[Craft Scheduler Thread - \\d+\\/WARN\\]: ";
    public static final String THREAD_ERROR = "\\[Server thread\\/INFO\\]: ";

    private static final Pattern[] DISCONNECTIONS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "Disconnecting com\\.mojang\\.authlib"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ lost connection: "),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ left the game")};

    private static final Pattern[] CONNECTIONS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ joined the game"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ logged in with entity id \\d+"),
            Pattern.compile(HOUR_REGEX + "\\[User Authenticator #\\d+\\/INFO\\]: UUID of player .+ is")};

    private static final Pattern[] COMMANDBLOCKS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "\\[@: ")};

    private static final Pattern[] DEATHS = {Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ was killed by Witch using magic"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ tried to swim in lava"),
            Pattern.compile(HOUR_REGEX + THREAD_INFO + ".+ fell from a high place")};

    private static final Pattern[] CONSOLES = {Pattern.compile(HOUR_REGEX + THREAD_INFO + "CONSOLE issued server command: .+")};

    private static final Pattern[] EXCEPTIONS = {Pattern.compile(HOUR_REGEX + CRAFT_WARN + ".*Exception.*"),
            Pattern.compile(HOUR_REGEX + CRAFT_WARN + "\\s+at .+\\(.+:.+\\)"),
            Pattern.compile(HOUR_REGEX + CRAFT_WARN + "\\s+\\.\\.\\. \\d+ more")};

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
}
