package com.example.rehabcalculator.ui.main.content;

import java.util.ArrayList;

public class CalendarItem {
    private int day;
    private ArrayList<TherapyContents> list = null;

    public CalendarItem(int day, ArrayList<TherapyContents> list) {
        this.day = day;
        this.list = list;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int d) {
        this.day = d;
    }

    public void setListItem(TherapyContents contents) {
        if(this.list == null) {
            this.list = new ArrayList<>();
            this.list.add(contents);
        } else {
            this.list.add(contents);
        }
    }

    public ArrayList<TherapyContents> getList() {
        return this.list;
    }
}
