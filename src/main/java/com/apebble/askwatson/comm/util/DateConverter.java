package com.apebble.askwatson.comm.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static LocalDate strToLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
    }

}
