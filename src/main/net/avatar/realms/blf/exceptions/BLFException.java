package net.avatar.realms.blf.exceptions;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class BLFException extends Exception {

    private String message;

    public BLFException (String error, Throwable cause) {
        super(cause);
        this.message = error;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
