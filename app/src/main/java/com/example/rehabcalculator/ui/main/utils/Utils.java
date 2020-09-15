package com.example.rehabcalculator.ui.main.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;

import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.HolidayItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

    public static String getCalendarMapKey(int year, int month, int dayofMonth){
        return year+""+(month+1)+""+dayofMonth;
    }

    public static String getEditDayTitle(int year, int month, int dayofMonth){
        return year+"년 "+(month+1)+"월 "+dayofMonth+"일";
    }

    //year, month 에 해당하는 캘린더정보만 리스트로 가져오기
    public static HashMap<String, CalendarItem> getYMCalendarItemList(HashMap<String, CalendarItem> calendarMap, int year, int month, int enddayofmonth) {
        HashMap<String, CalendarItem> ret = new HashMap<>();
        for(int i =1; i <= enddayofmonth; i++) {
            if(calendarMap.get(getCalendarMapKey(year, month, i)) != null) {
                ret.put(getCalendarMapKey(year, month, i), calendarMap.get(getCalendarMapKey(year, month, i)));
            }
        }
        return ret;
    }

    public static ArrayList<TherapyContents> getTheDaySchedules(HashMap<String, CalendarItem> calendarMap, int year, int month, int day) {
        if(calendarMap.get(getCalendarMapKey(year, month, day)) != null) {
            return calendarMap.get(getCalendarMapKey(year, month, day)).getList();
        } else {
            return null;
        }

    }

    public static HashMap<String, Integer> getMonthCheck(HashMap<String, CalendarItem> calendarMap, int year, int month, int enddayofmonth) {
        HashMap<String, CalendarItem> list = getYMCalendarItemList(calendarMap, year, month, enddayofmonth);
        HashMap<String, Integer> countByTherapist = new HashMap<>();
        for(int day =1; day <= enddayofmonth; day++) {
            ArrayList<TherapyContents> contentsList = list.get(getCalendarMapKey(year, month, day)).getList();
            for(int j = 0; contentsList != null && j < contentsList.size() ; j++) {
                TherapyContents contents = contentsList.get(j);
                if (countByTherapist.get(contents.getTherapistName()) == null) {
                    countByTherapist.put(contents.getTherapistName(), contents.getPrice() + contents.getMonthlyFee());
                } else {
                    countByTherapist.put(contents.getTherapistName(), countByTherapist.get(contents.getTherapistName()) + contents.getPrice());
                }
            }
        }

        return countByTherapist;
    }

    //http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear=2020&solMonth=09&ServiceKey=kIamWpfH2pAqWc93inU1gD3kfhTA9FhpEPVlU0Lrhm86arMWWg8vn92k46PVKBGENQymzCp068PBRfUEM7I3bQ%3D%3D
    //http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear=2020&numOfRows=20&ServiceKey=kIamWpfH2pAqWc93inU1gD3kfhTA9FhpEPVlU0Lrhm86arMWWg8vn92k46PVKBGENQymzCp068PBRfUEM7I3bQ%3D%3D
    public static HashMap<Integer, HolidayItem> getRestDeInfo(int y, int m) throws Exception {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode(String.valueOf(y), "UTF-8")); /*연*/
        //&solMonth=03
        String month = (m+1)<10? "0"+String.valueOf(m+1) : String.valueOf(m+1);
        urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode(month, "UTF-8"));
        //urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("20", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") +  "=kIamWpfH2pAqWc93inU1gD3kfhTA9FhpEPVlU0Lrhm86arMWWg8vn92k46PVKBGENQymzCp068PBRfUEM7I3bQ%3D%3D"); /*Service Key*/
        URL url = new URL(urlBuilder.toString());

        GetXMLTask task = new GetXMLTask();
        return task.execute(url).get();

    }


    private static class GetXMLTask extends AsyncTask<URL, Void, HashMap<Integer, HolidayItem>>{
        @Override
        protected HashMap<Integer, HolidayItem> doInBackground(URL... urls) {
            HashMap<Integer, HolidayItem> holidays = new HashMap<>();
            Document doc = null;
            try {

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(urls[0].openStream()));
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("item");

                for(int i = 0; i< nodeList.getLength(); i++){

                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList dateName = fstElmnt.getElementsByTagName("dateName");
                    NodeList locdate = fstElmnt.getElementsByTagName("locdate");
                    HolidayItem item = new HolidayItem(
                            locdate.item(0).getChildNodes().item(0).getNodeValue(),
                            dateName.item(0).getChildNodes().item(0).getNodeValue());

                    holidays.put(item.getDayOfMonth(), item);
                }


            } catch (Exception e) {
                Log.d("hkyeom", "Parsing Error");
            }
            return holidays;
        }

    }

}
