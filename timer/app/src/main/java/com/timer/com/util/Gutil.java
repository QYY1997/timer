package com.timer.com.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Gutil {
    public static String parseTime(int secondsMillis) {
        String format = "mm:ss.SSS";
        if (secondsMillis >= 3600*1000) {
            //>=1h
            format = "HH:mm:ss.SSS";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return sdf.format(new Date(secondsMillis));
    }
    public static String parseTimeMillis(int secondsMillis) {
        String format = "ss.SSS";
        if (secondsMillis >= 60*1000) {
            //>=1min
            format = "mm:ss.SSS";
        }
        if (secondsMillis >= 3600*1000) {
            //>=1h
            format = "HH:mm:ss.SSS";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return sdf.format(new Date(secondsMillis));
    }
    public static String bitrateFormat(int bitrate){
        DecimalFormat df = new DecimalFormat("#.00");
        String result;
        if ( bitrate < 1024 * 1024) {
            result = df.format(bitrate / 1024f) + " Kbps";
        } else {
            result = df.format(bitrate / 1024 / 1024f) + " Mbps";
        }
        return result;
    }
}
