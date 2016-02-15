package net.avatar.realms.blf.models;

import net.avatar.realms.blf.exceptions.BLFFormatException;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class Hour {

    public static final String HOUR_REGEX = "^\\[(\\d{2}):(\\d{2}):(\\d{2})\\]$";

    private int hours;
    private int minutes;
    private int seconds;

    public Hour(String summary) throws BLFFormatException {
        if (summary.matches(HOUR_REGEX)) {
            summary.replace("[", "");
            summary.replace("]", "");
            String[] array = summary.split(":");
            try {
                this.hours = Integer.parseInt(array[0]);
                this.minutes = Integer.parseInt(array[1]);
                this.seconds = Integer.parseInt(array[2]);
            }
            catch (NumberFormatException ex) {
                throw new BLFFormatException("Was not able to parse the hour string", ex);
            }
        }
        else {
            throw new BLFFormatException("Was not able to parse the hour string", null);
        }
    }

    public Hour(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String toString() {
        return "[" + hours + ":" + minutes + ":" + seconds + "]";
    }

    public boolean isBefore(Hour other) {
        if ((this.hours < other.hours)) {
            return true;
        }
        else if (this.hours == other.hours) {
            if (this.minutes < other.minutes) {
                return true;
            }
            else if (this.minutes == other.minutes) {
                if (this.seconds < other.seconds) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isAfter(Hour other) {
        if ((this.hours > other.hours)) {
            return true;
        }
        else if (this.hours == other.hours) {
            if (this.minutes > other.minutes) {
                return true;
            }
            else if (this.minutes == other.minutes) {
                if (this.seconds > other.seconds) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hour hour = (Hour) o;

        if (hours != hour.hours) return false;
        if (minutes != hour.minutes) return false;
        return seconds == hour.seconds;

    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        result = 31 * result + seconds;
        return result;
    }
}
