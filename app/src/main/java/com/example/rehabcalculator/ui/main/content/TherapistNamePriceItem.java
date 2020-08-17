package com.example.rehabcalculator.ui.main.content;

import androidx.annotation.NonNull;

public class TherapistNamePriceItem {
    private String therapist; //치료사 이름
    private int price = 0; //금액
    private int monthlyfee = 0; // 월회비 = 아델리 의상 비용 같은 개념

    public TherapistNamePriceItem(String name, int price, int monthlyfee) {
        this.therapist = name;
        this.price = price;
        this.monthlyfee = monthlyfee;
    }

    @NonNull
    @Override
    public String toString() {
        return  this.therapist +" " + this.price +" "+ this.monthlyfee;
    }

    public String getTherapist() {
        return therapist;
    }

    public void setTherapist(String name) {
        this.therapist = name;
    }

    public int getPrice() {
        return price;
    }

    public  void setPrice(int p) {
        this.price = p;
    }

    public void setMonthlyfee(int f) {
        this.monthlyfee = f;
    }

    public int getMonthlyfee() {
        return this.monthlyfee;
    }
}

