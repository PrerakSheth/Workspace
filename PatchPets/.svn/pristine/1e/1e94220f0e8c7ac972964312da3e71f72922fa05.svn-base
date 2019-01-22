package com.patchpets.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTime {

    public static String getCurrentDateTime() {
        String strDateTime = "";
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy");
            dateFormatGmt.setTimeZone(TimeZone.getDefault());
            strDateTime = dateFormatGmt.format(new Date()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDateTime;
    }

    public static String getDateForCardExpiry(String date) {
        String newDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("M/yy");
            newDate = newDateFormat.format(dateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
