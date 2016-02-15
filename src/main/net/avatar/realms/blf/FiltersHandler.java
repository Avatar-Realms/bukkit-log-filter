package net.avatar.realms.blf;


import java.util.regex.Pattern;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class FiltersHandler {

    private static final String HOUR_REGEX = "^\\[\\d{2}:\\d{2}:\\d{2}\\] ";

    private static final Pattern DISCONNECTION_1 = Pattern.compile(HOUR_REGEX + "\\[Server thread\\/INFO\\]: Disconnecting com\\.mojang\\.authlib");
    private static final Pattern DISCONNECTION_2 = Pattern.compile(HOUR_REGEX + "\\[Server thread\\/INFO\\]: .+ lost connection: ");
    private static final Pattern DISCONNECTION_3 = Pattern.compile(HOUR_REGEX + "\\[Server thread\\/INFO\\]: .+ left the game");

    private static final Pattern CONNECTION_1 = Pattern.compile(HOUR_REGEX + "\\[Server thread\\/INFO\\]: .+ joined the game");

    public static boolean matchesDisconnection(String line) {
       return DISCONNECTION_1.matcher(line).find()
               || DISCONNECTION_2.matcher(line).find()
               || DISCONNECTION_3.matcher(line).find();

    }

    public static boolean matchesConnection(String line) {
        return CONNECTION_1.matcher(line).find();
    }
}
