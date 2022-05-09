package main;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.TimeZone;

public class TimeZoneHelper {

    public static Timestamp LocalToUTCTimestamp() {
        Instant now = Instant.now();
        String UTCString = now.toString();
        UTCString = UTCString.replace("T"," ");
        UTCString = UTCString.replace("Z","");

        Timestamp UTCTimestamp = Timestamp.valueOf(UTCString);
        //String.format("%1$TD %1$TT", UTCTimestamp);

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





}
