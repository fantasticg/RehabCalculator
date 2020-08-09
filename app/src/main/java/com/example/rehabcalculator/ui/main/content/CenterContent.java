package com.example.rehabcalculator.ui.main.content;

import com.example.rehabcalculator.ui.main.MainViewModel;

import java.util.HashMap;

public class CenterContent {

    public static void addItem(MainViewModel mMainViewModel, RehabCenterInfoItem item) {
        mMainViewModel.addCenterInfo(item);
    }

    public static RehabCenterInfoItem createRehabCenterItem(int day, String name, int price, HashMap<Integer, RehabDayTimeInfoItem> time_items) {
        return new RehabCenterInfoItem(day, name, price, time_items);
    }

}
