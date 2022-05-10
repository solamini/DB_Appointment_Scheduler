package main;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneHelper {

    public static Timestamp LocalToUTCTimestamp() {
        Instant now = Instant.now();
        String UTCString = now.toString();
        UTCString = UTCString.replace("T"," ");
        UTCString = UTCString.replace("Z","");

        Timestamp UTCTimestamp = Timestamp.valueOf(UTCString);

        return UTCTimestamp;


    }
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

    public static void LocalToEST() {


    }

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

    public static void UTCToEST() {

    }

    public static String HoursFromTimestamp(Timestamp timestamp) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        String finalHours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

        //System.out.println(finalHours);
        return finalHours;
    }

    public static String MinutesFromTimestamp(Timestamp timestamp) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        String finalMinutes = String.valueOf(calendar.get(Calendar.MINUTE));

        System.out.println(finalMinutes);
        return finalMinutes;
    }

    public static Timestamp TimestampPlus7Days(Timestamp timestamp){
        int sevenDaysInSeconds = 604800 *1000;
        String oldTSString = timestamp.toString();
        Timestamp oldTimeStamp = Timestamp.valueOf(oldTSString);
        oldTimeStamp.setTime(timestamp.getTime()+sevenDaysInSeconds);
        Timestamp newTimeStamp = oldTimeStamp;

        return newTimeStamp;
    }

    public static Timestamp FirstDayOfMonthTS(){

        LocalDate currentLocalDate = LocalDate.now();
        LocalDate firstOfMonthLD = currentLocalDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalTime testLT = LocalTime.MIN;
        LocalDateTime firstOfMonthLDT = LocalDateTime.of(firstOfMonthLD,testLT);
        Timestamp firstOfMonthTS = Timestamp.valueOf(firstOfMonthLDT);

        return firstOfMonthTS;
    }

    public static Timestamp LastDayOfMonthTS(){

        LocalDate currentLocalDate = LocalDate.now();
        LocalDate lastOfMonthLD = currentLocalDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalTime testLT = LocalTime.MAX;
        LocalDateTime lastOfMonthLDT = LocalDateTime.of(lastOfMonthLD,testLT);
        Timestamp lastOfMonthTS = Timestamp.valueOf(lastOfMonthLDT);

        return lastOfMonthTS;
    }

}
