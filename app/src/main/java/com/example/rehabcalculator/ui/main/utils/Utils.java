package com.example.rehabcalculator.ui.main.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static Date getDate(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static String timeTextOnBtn(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return (cal.get(Calendar.HOUR_OF_DAY)>11?"PM ": "AM ") + cal.get(Calendar.HOUR) +":" +(cal.get(Calendar.MINUTE)<10?"0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE));
    }
}