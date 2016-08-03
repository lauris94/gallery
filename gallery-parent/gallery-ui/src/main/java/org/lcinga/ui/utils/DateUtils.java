package org.lcinga.ui.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by lcinga on 2016-08-03.
 */

public class DateUtils {
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm";

    private DateUtils(){}

    public static String convertDateToString(Date input) {
        Instant instant = Instant.ofEpochMilli(input.getTime());
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
        return res.format(formatter);
    }
}
