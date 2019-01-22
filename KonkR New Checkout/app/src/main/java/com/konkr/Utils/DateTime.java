package com.konkr.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTime {

    public static String getCurrentDateTime() {
        String strDateTime = "";
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd");
            dateFormatGmt.setTimeZone(TimeZone.getDefault());
            strDateTime = dateFormatGmt.format(new Date()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDateTime;
    }

    public static String getCurrentDateTimeForBPDevice() {
        String strDateTime = "";
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("(HH:mm:ss MM/dd/yy)");
            dateFormatGmt.setTimeZone(TimeZone.getDefault());
            strDateTime = dateFormatGmt.format(new Date()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDateTime;
    }

    public static String timeInFormat(int currentPosition) {
        int hours = currentPosition / 3600;
        int minutes = (currentPosition % 3600) / 60;
        int seconds = currentPosition % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String getDateFromString(String date) {
        String newDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
            SimpleDateFormat newdateFormat = new SimpleDateFormat("dd/MM/yyyy");
            newDate = newdateFormat.format(dateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }
    public static String getFullDateFromString(String date) {
        String newDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newdateFormat = new SimpleDateFormat("MMM-dd");
            newDate = newdateFormat.format(dateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static String getDateInNumber(String date) {
        String newDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd");
            SimpleDateFormat newdateFormat = new SimpleDateFormat("dd-MM");
            newDate = newdateFormat.format(dateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static String getMonthNumber(String date) {
        String newDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
            SimpleDateFormat newdateFormat = new SimpleDateFormat("MM");
            newDate = newdateFormat.format(dateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static String getDateForTransactions(String date) {
        String newDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat newdateFormat = new SimpleDateFormat("MMMM d,yyyy");
            newDate = newdateFormat.format(dateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static String getCurrentMonth() {
        String strDateTime = "";
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MM");
            dateFormatGmt.setTimeZone(TimeZone.getDefault());
            strDateTime = dateFormatGmt.format(new Date()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDateTime;
    }
}
