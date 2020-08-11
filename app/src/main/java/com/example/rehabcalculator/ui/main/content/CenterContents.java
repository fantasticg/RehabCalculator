package com.example.rehabcalculator.ui.main.content;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class CenterContents {

    public static final int START_TIME = 0;
    public static final int END_TIME = 1;

    private int day; //요일
    private int num; // 횟수 - 2회연속 일 경우를 위해
    private Date[] dates = new  Date[2]; //시작시간
    private CenterNamePriceItem centerInfo;

    public CenterContents(int day, int num, Date start, Date end, CenterNamePriceItem info) {
        this.day = day;
        this.num = num;
        this.dates[START_TIME] = start;
        this.dates[END_TIME] = end;
        this.centerInfo = info;
    }

    private SimpleDateFormat getHMFormat(Date date) {
        return new SimpleDateFormat("HH:mm");
    }

    @NonNull
    @Override
    public String toString() {
        return "센터이름:" + getCenterName() + "가격:" + getPrice() + "요일:" + day + " 횟수:" + num + " 시간 : " + getHMFormat(dates[START_TIME])+"~"+getHMFormat(dates[END_TIME]);
    }

    public String getCenterName() {
        return centerInfo.getTherapist();
    }

    public int getPrice() {
        return centerInfo.getPrice();
    }

    public int getDay() {
        return day;
    }

    public int getNum() {
        return num;
    }

    public Date[] getDate() {
        return dates;
    }

    public void setDate(Date[] date) {
        dates = date;
    }

}
