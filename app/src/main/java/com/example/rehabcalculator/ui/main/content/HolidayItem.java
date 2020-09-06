package com.example.rehabcalculator.ui.main.content;

public class HolidayItem {
    private final String locdate;
    private final String holidayName;

    public HolidayItem(String locdate, String holidayName) {
        this.locdate = locdate;
        this.holidayName = holidayName;
    }

    public String getLocdate() {
        return locdate;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public int getDayOfMonth() {
        return Integer.parseInt(this.locdate) % 100;
    }
}
