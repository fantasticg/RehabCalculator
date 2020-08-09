package com.example.rehabcalculator.ui.main.content;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class RehabDayTimeInfoItem {

    private String dayOfWeek; //요일
    private int num; // 횟수 - 2회연속 일 경우를 위해
    private Date start; //시작시간
    private int minute; //치료시간 - 45분 치료 일 경우
    private Date end; // 종료시간 start + minute로 계산해서 사용함.

    public RehabDayTimeInfoItem(String dayOfWeek, int num, Date start, Date end) {
        this.dayOfWeek = dayOfWeek;
        this.num = num;
        this.start = start;
        this.end = end;
    }

    private SimpleDateFormat getHourFormat(Date date) {
        return new SimpleDateFormat("HH:mm");
    }

    @NonNull
    @Override
    public String toString() {
        return "요일:" + dayOfWeek.toString() + " 횟수:" + num + " 시간 : " + getHourFormat(start)+"~"+getHourFormat(end) ;
    }
}
