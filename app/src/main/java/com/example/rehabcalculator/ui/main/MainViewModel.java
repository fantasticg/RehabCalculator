package com.example.rehabcalculator.ui.main;

import com.example.rehabcalculator.ui.main.content.CenterNamePriceItem;
import com.example.rehabcalculator.ui.main.content.CenterContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    //센터를 무슨 요일날 가는지 데이타 정보.
    private HashMap<String, CenterNamePriceItem> centerMap = new HashMap<>();
    private ArrayList<CenterContents> timeList = new ArrayList<>();
    private ArrayList<CenterContents> day_empty_list = new ArrayList<>();
    public HashMap<String, CenterNamePriceItem> getCenterMap() {
        return centerMap;
    }
    public ArrayList<CenterContents> getContentsItem() {
        return timeList;
    }


    //RehabDayTimeInfoItem(int day, int num, Date start, Date end, RehabCenterInfoItem info)
    public void addTimeInfo(String center_name, int day, int price, int num, Date start, Date end) {
        if(centerMap.get(center_name)==null) {
            CenterNamePriceItem centerInfo = new CenterNamePriceItem(center_name, price);
            centerMap.put(centerInfo.getTherapist(), centerInfo);
        }
        CenterContents item = new CenterContents(day, num, start, end, centerMap.get(center_name));
        timeList.add(item);
    }

    public ArrayList<CenterContents> getDayEmptyList() {


        EmptyListaddTime("", Const.MONDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.TUESDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.WEDNESDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.THURSDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.FRIDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.SATURDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.SUNDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));


        return day_empty_list;
    }

    private void EmptyListaddTime(String center_name, int day, int price, int num, Date start, Date end) {
        day_empty_list.add(new CenterContents(day, num, start, end, centerMap.get(center_name)));
    }



}
