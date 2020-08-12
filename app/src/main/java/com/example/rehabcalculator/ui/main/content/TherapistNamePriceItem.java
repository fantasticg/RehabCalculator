package com.example.rehabcalculator.ui.main.content;

import androidx.annotation.NonNull;

public class TherapistNamePriceItem {
    private String therapist; //치료사 이름
    private int price; //금액

    public TherapistNamePriceItem(String name, int price) {
        this.therapist = name;
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return  this.therapist +" " + String.valueOf(this.price);
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
}

