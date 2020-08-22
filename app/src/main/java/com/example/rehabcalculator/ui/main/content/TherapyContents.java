package com.example.rehabcalculator.ui.main.content;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class TherapyContents implements Cloneable{

    public static final int START_TIME = 0;
    public static final int END_TIME = 1;

    private int dayOfWeek; //요일
    private int num; // 횟수 - 2회연속 일 경우를 위해
    private Date[] dates = new  Date[2]; //시작시간
    private TherapistNamePriceItem therapistInfo;

    public TherapyContents(int dayOfWeek, int num, Date start, Date end, TherapistNamePriceItem info) {
        this.dayOfWeek = dayOfWeek;
        this.num = num;
        this.dates[START_TIME] = start;
        this.dates[END_TIME] = end;
        this.therapistInfo = info;
    }

    private SimpleDateFormat getHMFormat(Date date) {
        return new SimpleDateFormat("HH:mm");
    }

    @NonNull
    @Override
    public String toString() {
        return "센터이름:" + getTherapistName() + "가격:" + getPrice() + "요일:" + dayOfWeek + " 횟수:" + num + " 시간 : " + getHMFormat(dates[START_TIME])+"~"+getHMFormat(dates[END_TIME]);
    }

    public String getTherapistName() {
        return therapistInfo.getTherapist();
    }

    public int getPrice() {
        return therapistInfo.getPrice();
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getMonthlyFee() {
        return therapistInfo.getMonthlyfee();
    }


    public void setNum(int num) {
        this.num = num;
    }

    public TherapistNamePriceItem getTherapistInfo() {
        return therapistInfo;
    }

    public void setTherapistNamePriceItem( TherapistNamePriceItem item) {
        this.therapistInfo = item;
    }

    @Override
    public TherapyContents clone() {
        TherapyContents clone = null;
        try {
            clone = (TherapyContents) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

}
