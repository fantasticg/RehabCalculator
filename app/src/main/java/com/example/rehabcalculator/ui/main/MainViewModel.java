package com.example.rehabcalculator.ui.main;

import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private ArrayList<TherapyContents> add_init_list = new ArrayList<>();

    private HashMap<String, CalendarItem> calendar_init_map = new HashMap<>();

    private HashMap<String, CalendarItem> calendar_saved_map = null;

    private ArrayList<TherapyContents> new_addlist = new ArrayList<>();

    private ArrayList<TherapyContents> save_addlist = new ArrayList<>();

    private Calendar selectCal = Calendar.getInstance();

    public void setSelectCal(Calendar cal) {
        this.selectCal = cal;
    }

    public Calendar getSelectCal() {
        return selectCal;
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

    public HashMap<String, CalendarItem> getCalendarInitList(int year, int month, int enddayofmonth) {

        calendar_init_map.clear();

        for(int i = 0; i < enddayofmonth; i++) {
            addCalendarItemWithList(year, month, i+1, null);
        }
        return calendar_init_map;
    }

    public HashMap<String, CalendarItem> getCalendarInitMap() {
        return calendar_init_map;
    }

    public void setCalendarSaveMap(HashMap<String, CalendarItem> map) {
        if(map != null) {
            if(calendar_saved_map == null) {
                calendar_saved_map = map;
            } else {
                calendar_saved_map.putAll(map);
            }
        }
    }

    public HashMap<String, CalendarItem> getCalendarSaveMap() {
        return calendar_saved_map;
    }

    public void addCalendarItemWithContent(int year, int month, int dayofMonth, TherapyContents contents) {
        if(calendar_saved_map.get(Utils.getCalendarMapKey(year, month, dayofMonth)) == null) {
            ArrayList<TherapyContents> list = new ArrayList<>();
            list.add(contents);
            calendar_saved_map.put(Utils.getCalendarMapKey(year, month, dayofMonth), new CalendarItem(year, month, dayofMonth, list));
        } else {
            calendar_saved_map.get(Utils.getCalendarMapKey(year, month, dayofMonth)).addListItem(contents);
        }
        //totalCountCalculation(contents);
    }

    private void addCalendarItemWithList(int year, int month, int dayofMonth, ArrayList<TherapyContents> list) {
        if(calendar_init_map.size() <dayofMonth || calendar_init_map.get(dayofMonth) == null) {
            calendar_init_map.put(Utils.getCalendarMapKey(year, month, dayofMonth), new CalendarItem(year, month, dayofMonth, list));
        } else {
            calendar_init_map.get(Utils.getCalendarMapKey(year, month, dayofMonth)).addList(list);
        }
    }


    public void setNewAddList(ArrayList<TherapyContents> list) {
        this.new_addlist = list;
    }


    public ArrayList<TherapyContents> getNewAddList() {
        return this.new_addlist;
    }

    public void setAddSaveList(ArrayList<TherapyContents> list) {
        if(list != null) {
            this.save_addlist = list;
        }
    }

    public void addItemSavedlist(TherapyContents contents) {
        this.save_addlist.add(contents);
    }

    public ArrayList<TherapyContents> getSavedlist() {
        return save_addlist;
    }

}
