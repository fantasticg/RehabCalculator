package com.example.rehabcalculator.ui.main.content;

import java.util.ArrayList;

public class CalendarItem {
    private int year;
    private int month;
    private int day;
    private ArrayList<TherapyContents> list = null;

    public CalendarItem(int year, int month, int day,  ArrayList<TherapyContents> list) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.list = list;
    }

    public int getDay() {
        return day;
    }

    public void addList(ArrayList<TherapyContents> list) {
        if(this.list == null) {
            this.list = new ArrayList<>();
            this.list = list;
        } else {
            this.list = list;
        }
    }

    public void addListItem(TherapyContents contents) {
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
