package com.superland.utils;
import java.util.Calendar;
public class ExpiryPaymentLinkUtil {
    public static String generate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        String formattedString = String.format("%s-%s-%s %s:%s:%s\t", year, month, day, hour, minute, second);
        return formattedString+"+0700";
    }
}
