package com.example.rehabcalculator.ui.main;

import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private HashMap<Integer, ArrayList<TherapyContents>> calendarMap = new HashMap<>();

    private ArrayList<TherapyContents> add_init_list = new ArrayList<>();
    private ArrayList<CalendarItem> calendar_init_list = new ArrayList<>();

    private ArrayList<TherapyContents> add_list;

    //public HashMap<String, CenterContents> centerMap = new HashMap<>();



    //RehabDayTimeInfoItem(int day, int num, Date start, Date end, RehabCenterInfoItem info)
    public void addCenterMap(int dayOfMonth, TherapyContents aCenterList) {
        if (calendarMap.get(dayOfMonth) == null) {
            ArrayList<TherapyContents> list = new ArrayList<>();
            list.add(aCenterList);
            calendarMap.put(dayOfMonth, list);
        } else {
            calendarMap.get(dayOfMonth).add(aCenterList);
        }

    }




    public ArrayList<TherapyContents> getDayInitList() {

        if(add_init_list.size() == 0) {
            EmptyListaddTime("", Const.MONDAY, 0, 1, Utils.getDate(3, 0), Utils.getDate(4, 0));
            EmptyListaddTime("", Const.TUESDAY, 0, 1, Utils.getDate(3, 0), Utils.getDate(4, 0));
            EmptyListaddTime("", Const.WEDNESDAY, 0, 1, Utils.getDate(3, 0), Utils.getDate(4, 0));
            EmptyListaddTime("", Const.THURSDAY, 0, 1, Utils.getDate(3, 0), Utils.getDate(4, 0));
            EmptyListaddTime("", Const.FRIDAY, 0, 1, Utils.getDate(3, 0), Utils.getDate(4, 0));
            EmptyListaddTime("", Const.SATURDAY, 0, 1, Utils.getDate(3, 0), Utils.getDate(4, 0));
            EmptyListaddTime("", Const.SUNDAY, 0, 1, Utils.getDate(3, 0), Utils.getDate(4, 0));
        }
        return add_init_list;
    }

    private void EmptyListaddTime(String center_name, int dayOfWeek, int price, int num, Date start, Date end) {
        add_init_list.add(new TherapyContents(dayOfWeek, num, start, end, null));
    }

    public ArrayList<CalendarItem> getCalendarInitList(int dayOfMonth) {

        for(int i = 0; i < dayOfMonth; i++) {
            EmptyListCalendarItem(i+1, null);
        }
        return calendar_init_list;
    }


    private void EmptyListCalendarItem(int dayofMonth, ArrayList<TherapyContents> list) {
        calendar_init_list.add(new CalendarItem(dayofMonth, list));
    }


    public void setAddList(ArrayList<TherapyContents> list) {
        if(this.add_list == null) {
            this.add_list = list;
        } else {
            add_list.addAll(list);
        }

    }

    public ArrayList<TherapyContents> getAddList() {
        return this.add_list;
    }



}
