package com.example.rehabcalculator.ui.main;

import com.example.rehabcalculator.ui.main.content.RehabCenterInfoItem;

import java.util.ArrayList;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    //센터를 무슨 요일날 가는지 데이타 정보.
    private ArrayList<RehabCenterInfoItem> centerList = new ArrayList<>();

    public ArrayList<RehabCenterInfoItem> getCenterList() {
        return centerList;
    }

    public void addCenterInfo(RehabCenterInfoItem item) {
        centerList.add(item);
    }



}
