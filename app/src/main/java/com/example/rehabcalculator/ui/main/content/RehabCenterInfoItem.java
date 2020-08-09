package com.example.rehabcalculator.ui.main.content;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;

public class RehabCenterInfoItem {
    public int day;
    public String cenetrName; //센터이름
    private int price; //금액
    private HashMap<Integer, RehabDayTimeInfoItem> timeInfo; //요일, 횟수, 시작시간

    public RehabCenterInfoItem(int day, String name, int price, HashMap<Integer, RehabDayTimeInfoItem> time_items) {
        this.day = day;
        this.cenetrName = name;
        this.price = price;
        this.timeInfo = time_items;
    }

    @NonNull
    @Override
    public String toString() {

        String string = this.cenetrName+" " + String.valueOf(this.price);
        if(timeInfo != null) {
            Iterator<Integer> keys = timeInfo.keySet().iterator();
            for (Integer key : timeInfo.keySet()) {
                string = string + " " + timeInfo.get(key);
            }
        }
        return string;
    }
}

