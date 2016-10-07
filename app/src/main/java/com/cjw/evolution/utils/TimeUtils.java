package com.cjw.evolution.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by CJW on 2016/10/4.
 */

public class TimeUtils {

    public static String formatShotsTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = sdf.parse(time);
            return stringFromInterval(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String stringFromInterval(long time) {

        long curTime = System.currentTimeMillis();
        int timeInterval = (int) ((curTime - time) / 1000);

        long zeroTime = getZeroTime(time);
        int zeroTimeInterval = (int) ((curTime - zeroTime) / 1000);

        if (timeInterval >= 0) {
            if (timeInterval < 60 * 3) {
                return "刚刚";
            } else if (timeInterval < 60 * 60) {
                int min = timeInterval / 60;
                String stringWithFormat = "%s分钟";
                return String.format(stringWithFormat, min);
            } else if (timeInterval < 60 * 60 * 24) {
                int hour = timeInterval / 60 / 60;
                String stringWithFormat = "%s时";
                return String.format(stringWithFormat, hour);
            } else if (zeroTimeInterval < 60 * 60 * 24 * 2) {
                return "昨天";
            } else if (zeroTimeInterval < 60 * 60 * 24 * 4) {
                int hour = zeroTimeInterval / 60 / 60 / 24;
                String stringWithFormat = "%s天";
                return String.format(stringWithFormat, hour);
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
                String strDate = simpleDateFormat.format(zeroTime);
                String curDate = simpleDateFormat.format(System.currentTimeMillis());
                if (strDate.endsWith(curDate)) {
                    simpleDateFormat = new SimpleDateFormat("MM-dd");
                    strDate = simpleDateFormat.format(zeroTime);
                    return strDate;
                } else {
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    strDate = simpleDateFormat.format(zeroTime);
                    return strDate;
                }
            }
        } else {
            return "刚刚";
        }
    }

    private static long getZeroTime(long time) {
        Calendar original = Calendar.getInstance();
        original.setTimeInMillis(time);

        int year = original.get(Calendar.YEAR);
        int month = original.get(Calendar.MONTH);
        int day = original.get(Calendar.DAY_OF_MONTH);

        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day, 0, 0, 0);
        return newDate.getTimeInMillis();
    }

}
