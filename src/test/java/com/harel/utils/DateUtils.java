package com.harel.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // כדי להיות עקבי עם המשתמשים בישראל
    public static final ZoneId ISRAEL_TZ = ZoneId.of("Asia/Jerusalem");

    private static final DateTimeFormatter UI_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate todayIsrael() {
        return LocalDate.now(ISRAEL_TZ);
    }

    public static String formatForUi(LocalDate date) {
        return date.format(UI_FORMAT);
    }
}
