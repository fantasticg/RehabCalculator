package com.example.rehabcalculator.ui.main.content;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class TherapyContents {

    public static final int START_TIME = 0;
    public static final int END_TIME = 1;

    private int day; //요일
    private int num; // 횟수 - 2회연속 일 경우를 위해
    private Date[] dates = new  Date[2]; //시작시간
    private TherapistNamePriceItem centerInfo;

    public TherapyContents(int day, int num, Date start, Date end, TherapistNamePriceItem info) {
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
        return "센터이름:" + getTherapistName() + "가격:" + getPrice() + "요일:" + day + " 횟수:" + num + " 시간 : " + getHMFormat(dates[START_TIME])+"~"+getHMFormat(dates[END_TIME]);
    }

    public String getTherapistName() {
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

    public void setTherapistName(String therapistName) {
        if(centerInfo == null) {
            centerInfo = new TherapistNamePriceItem(therapistName, 0);
        } else {
            centerInfo.setTherapist(therapistName);
        }
    }

    public void setPrice(int p) {
        if(centerInfo == null) {
            centerInfo = new TherapistNamePriceItem("", p);
        } else {
            centerInfo.setPrice(p);
        }
    }

    public void setDate(Date[] date) {
        this.dates = date;
    }

    public void setNum(int num) {
        this.num = num;
    }


}
