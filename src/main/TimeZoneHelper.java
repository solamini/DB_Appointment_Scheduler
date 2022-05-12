package main;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.TimeZone;

/** This is a helper class that helps converting time from various timezones. */
public class TimeZoneHelper {

    /** This method returns the current time in the UTC time zone as a timestamp.
     * @return Returns Timestamp of UTC time. */
    public static Timestamp LocalToUTCTimestamp() {
        Instant now = Instant.now();
        String UTCString = now.toString();
        UTCString = UTCString.replace("T"," ");
        UTCString = UTCString.replace("Z","");

        Timestamp UTCTimestamp = Timestamp.valueOf(UTCString);

        return UTCTimestamp;


    }
    /** This method converts the local Timestamp into a UTC timestamp.
     * @param localTimeStamp
     * @return This returns the UTC timestamp. */
    public static Timestamp LocalToUTCTimestamp(Timestamp localTimeStamp) {
        LocalDateTime localDT = localTimeStamp.toLocalDateTime();
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZDT = localDT.atZone(localZoneID);
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime UTCZDT = localZDT.withZoneSameInstant(utcZoneId);
        LocalDateTime UTCDateTime = UTCZDT.toLocalDateTime();

        Timestamp UTCTimestamp = Timestamp.valueOf(UTCDateTime);

        return UTCTimestamp;
    }

    /** This method converts the local timestamp to an Eastern timestamp.
     * @param localTimeStamp
     * @return Returns the EST Timestamp. */
    public static Timestamp LocalToESTTimestamp(Timestamp localTimeStamp) {
        LocalDateTime localDT = localTimeStamp.toLocalDateTime();
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZDT = localDT.atZone(localZoneID);
        ZoneId utcZoneId = ZoneId.of("US/Eastern");
        ZonedDateTime UTCZDT = localZDT.withZoneSameInstant(utcZoneId);
        LocalDateTime UTCDateTime = UTCZDT.toLocalDateTime();

        Timestamp ESTTimestamp = Timestamp.valueOf(UTCDateTime);

        return ESTTimestamp;
    }

    /** This method converts a UTC timestamp to a Local timestamp.
     * @param UTC timestamp input
     * @return Returns local timestamp */
    public static Timestamp UTCToLocalTimestamp(Timestamp UTC) {

        LocalDateTime UTCDateTime = UTC.toLocalDateTime();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime UTCZoneDateTime = UTCDateTime.atZone(utcZoneId);
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZDT = UTCZoneDateTime.withZoneSameInstant(localZoneID);
        LocalDateTime localDateTime = localZDT.toLocalDateTime();

        Timestamp localTimestamp = Timestamp.valueOf(localDateTime);

        return localTimestamp;
    }

    /** This method converts a UTC timestamp to an Eastern timestamp.
     * @param UTC timestamp input
     * @return Returns an EST timestamp */
    public static Timestamp UTCToESTTimestamp(Timestamp UTC) {
        LocalDateTime UTCDateTime = UTC.toLocalDateTime();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime UTCZoneDateTime = UTCDateTime.atZone(utcZoneId);
        ZoneId ESTZoneID = ZoneId.of("US/Eastern");
        ZonedDateTime ESTZDT = UTCZoneDateTime.withZoneSameInstant(ESTZoneID);
        LocalDateTime ESTDateTime = ESTZDT.toLocalDateTime();

        Timestamp ESTTimestamp = Timestamp.valueOf(ESTDateTime);

        return ESTTimestamp;
    }

    /** This method takes in a timestamp and tells you the hours in it.
     * @param timestamp
     * @return Returns the value of hours as a string. */
    public static String HoursFromTimestamp(Timestamp timestamp) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        String finalHours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

        //System.out.println(finalHours);
        return finalHours;
    }

    /** This method takes in a timestamp and tells you the minutes in it.
     * @param timestamp
     * @return Returns the value of minutes as a string. */
    public static String MinutesFromTimestamp(Timestamp timestamp) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        String finalMinutes = String.valueOf(calendar.get(Calendar.MINUTE));

        System.out.println(finalMinutes);
        return finalMinutes;
    }

    /** This method takes in a timestamp and adds 7 days of time to it.
     * @param timestamp
     * @return Returns a new timestamp that is 7 days after the input timestamp. */
    public static Timestamp TimestampPlus7Days(Timestamp timestamp){
        int sevenDaysInMiliseconds = 604800 *1000;
        String oldTSString = timestamp.toString();
        Timestamp oldTimeStamp = Timestamp.valueOf(oldTSString);
        oldTimeStamp.setTime(timestamp.getTime()+sevenDaysInMiliseconds);
        Timestamp newTimeStamp = oldTimeStamp;

        return newTimeStamp;
    }

    /** This method gives you the timestamp for the first day of the current month.
     * @return Returns timestamp of the first day of the current month. */
    public static Timestamp FirstDayOfMonthTS(){

        LocalDate currentLocalDate = LocalDate.now();
        LocalDate firstOfMonthLD = currentLocalDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalTime testLT = LocalTime.MIN;
        LocalDateTime firstOfMonthLDT = LocalDateTime.of(firstOfMonthLD,testLT);
        Timestamp firstOfMonthTS = Timestamp.valueOf(firstOfMonthLDT);

        return firstOfMonthTS;
    }

    /** This method gives you the timestamp for the last day of the current month.
     * @return Returns timestamp of the last day of the current month. */
    public static Timestamp LastDayOfMonthTS(){

        LocalDate currentLocalDate = LocalDate.now();
        LocalDate lastOfMonthLD = currentLocalDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalTime testLT = LocalTime.MAX;
        LocalDateTime lastOfMonthLDT = LocalDateTime.of(lastOfMonthLD,testLT);
        Timestamp lastOfMonthTS = Timestamp.valueOf(lastOfMonthLDT);

        return lastOfMonthTS;
    }

    /** This method takes in a timestamp and adds 15 minutes of time to it.
     * @param startingTimeStamp
     * @return Returns a new timestamp that is 15 minutes after the input timestamp. */
    public static Timestamp TimeStampPlus15Min(Timestamp startingTimeStamp){
        int fifteenMinutesInMiliSeconds = 15*60*1000;
        String oldTSString = startingTimeStamp.toString();
        Timestamp oldTimeStamp = Timestamp.valueOf(oldTSString);
        oldTimeStamp.setTime(startingTimeStamp.getTime()+fifteenMinutesInMiliSeconds);
        Timestamp newTimeStamp = oldTimeStamp;

        return newTimeStamp;
    }


}
