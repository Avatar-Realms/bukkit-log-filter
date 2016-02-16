package net.avatar.realms.blf;


import javafx.util.Pair;
import net.avatar.realms.blf.exceptions.BLFFormatException;
import net.avatar.realms.blf.models.Hour;

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
    private static final Pattern CONNECTION_2 = Pattern.compile(HOUR_REGEX + "\\[Server thread\\/INFO\\]: .+ logged in with entity id \\d+");
    private static final Pattern CONNECTION_3 = Pattern.compile(HOUR_REGEX + "\\[User Authenticator #\\d+\\/INFO\\]: UUID of player .+ is");


    private static final Pattern COMMANDBLOCK_1 = Pattern.compile(HOUR_REGEX + "\\[Server thread\\/INFO\\]: \\[@: ");

    public static boolean matchesDisconnection(String line) {
       return DISCONNECTION_1.matcher(line).find()
               || DISCONNECTION_2.matcher(line).find()
               || DISCONNECTION_3.matcher(line).find();

    }

    public static boolean matchesConnection(String line) {
        return CONNECTION_1.matcher(line).find()
                ||CONNECTION_2.matcher(line).find()
                ||CONNECTION_3.matcher(line).find();
    }

    public static boolean matchesCommandBlock(String line){
        return COMMANDBLOCK_1.matcher(line).find();
    }
}
