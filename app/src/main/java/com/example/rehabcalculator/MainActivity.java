package com.example.rehabcalculator;

import android.os.Bundle;

import com.example.rehabcalculator.ui.main.CalendarFragment;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public void onListFragmentInteraction(CalendarItem item) {

    }

}
