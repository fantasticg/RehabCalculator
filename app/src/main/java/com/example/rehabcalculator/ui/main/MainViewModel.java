package com.example.rehabcalculator.ui.main;

import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    //센터를 무슨 요일날 가는지 데이타 정보.
    private ArrayList<TherapyContents> add_init_list = new ArrayList<>();
    private ArrayList<CalendarItem> calendar_init_list = new ArrayList<>();

    //public HashMap<String, CenterContents> centerMap = new HashMap<>();



    //RehabDayTimeInfoItem(int day, int num, Date start, Date end, RehabCenterInfoItem info)
    public void setMapping(ArrayList<TherapyContents> aCenterList) {

    }




    public ArrayList<TherapyContents> getDayInitList() {

        EmptyListaddTime("", Const.MONDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.TUESDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.WEDNESDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.THURSDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.FRIDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.SATURDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));
        EmptyListaddTime("", Const.SUNDAY,0, 1, Utils.getDate(3,0), Utils.getDate(4,0));

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



}
