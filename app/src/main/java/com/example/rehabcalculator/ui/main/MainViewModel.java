package com.example.rehabcalculator.ui.main;

import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private ArrayList<TherapyContents> add_init_list = new ArrayList<>();

    private HashMap<String, CalendarItem> calendar_map = new HashMap<>();

    private HashMap<String, Integer> countByTherapist = new HashMap<>();

    private ArrayList<TherapyContents> add_list = new ArrayList<>();

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

    public HashMap<String, CalendarItem> getCalendarInitList(int year, int month) {
        return calendar_map;
    }

    public HashMap<String, CalendarItem> getCalendarInitList(int year, int month, int enddayofmonth) {

        if(calendar_map.size() == enddayofmonth) {
            return calendar_map;
        }

        for(int i = 0; i < enddayofmonth; i++) {
            addCalendarItemWithList(year, month, i+1, null);
        }
        return calendar_map;
    }



    public void addCalendarItemWithList(int year, int month, int dayofMonth, ArrayList<TherapyContents> list) {
        if(calendar_map.size() <dayofMonth || calendar_map.get(dayofMonth) == null) {
            calendar_map.put(getMapKey(year, month, dayofMonth), new CalendarItem(year, month, dayofMonth, list));
        } else {
            calendar_map.get(getMapKey(year, month, dayofMonth)).addList(list);
        }
    }

    public void addCalendarItemWithContent(int year, int month, int dayofMonth, TherapyContents contents) {
        if(calendar_map.get(dayofMonth) == null) {
            ArrayList<TherapyContents> list = new ArrayList<>();
            list.add(contents);
            calendar_map.put(getMapKey(year, month, dayofMonth), new CalendarItem(year, month, dayofMonth, list));
        } else {
            calendar_map.get(getMapKey(year, month, dayofMonth)).addListItem(contents);
        }
        totalCountCalculation(contents);
    }

    /*
    private void totalCountCalculation(ArrayList<TherapyContents> list) {
        if(list == null) {
            return;
        }

        for(TherapyContents contents : list) {
            if(countByTherapist.get(contents.getTherapistName()) == null) {
                countByTherapist.put(contents.getTherapistName(), 1);
            } else {
                countByTherapist.put(contents.getTherapistName(), countByTherapist.get(contents.getTherapistName())+1);
            }
        }
    }

     */

    public void totalCountCalculation(TherapyContents contents) {
        if(countByTherapist.get(contents.getTherapistName()) == null) {
            countByTherapist.put(contents.getTherapistName(), contents.getPrice()+contents.getMonthlyFee());
        } else {
            countByTherapist.put(contents.getTherapistName(), countByTherapist.get(contents.getTherapistName())+contents.getPrice());
        }
    }

    public HashMap<String, Integer> getCountMap() {
        return countByTherapist;
    }

    public String getMapKey(int year, int month, int dayofMonth){
        return year+""+month+1+""+dayofMonth;
    }

    public void setAddList(ArrayList<TherapyContents> list) {
        this.add_list = list;
    }

    public ArrayList<TherapyContents> getSavedList() {
        return this.add_list;
    }



}
