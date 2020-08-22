package com.example.rehabcalculator.ui.main.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

    public static String datetimeTextOnBtn(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.YEAR) + "년" + (cal.get(Calendar.MONTH)+1)+ "월" + cal.get(Calendar.DAY_OF_MONTH)+ "일"+
                (cal.get(Calendar.HOUR_OF_DAY)>11?" PM ": " AM ") + cal.get(Calendar.HOUR) +":"
                +(cal.get(Calendar.MINUTE)<10?"0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE));
    }

    public static String calendarMonthBtn(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.YEAR) + "." + (cal.get(Calendar.MONTH)+1);
    }

    public static void setAddPref(Context context, ArrayList<TherapyContents> values) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(values);
        editor.putString("Therapy", json);
        editor.commit();
    }

    public static ArrayList<TherapyContents> ReadAddData(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Therapy", "EMPTY");
        Type type = new TypeToken<ArrayList<TherapyContents>>() {}.getType();
        if(json.equals("EMPTY")) {
            return null;
        }
        ArrayList<TherapyContents> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

    public static void setCalendarPref(Context context, HashMap<String, CalendarItem> values) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(values);
        editor.putString("Calendar", json);
        editor.commit();
    }

    public static HashMap<String, CalendarItem> ReadCalendarData(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Calendar", "EMPTY");
        Type type = new TypeToken<HashMap<String, CalendarItem>>(){}.getType();
        if(json.equals("EMPTY")) {
            return null;
        }
        HashMap<String, CalendarItem> map = gson.fromJson(json, type);
        return map;
    }

}
