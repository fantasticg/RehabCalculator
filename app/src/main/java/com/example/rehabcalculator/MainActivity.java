package com.example.rehabcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.rehabcalculator.ui.main.CalendarFragment;
import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.CalendarItem;

import org.json.JSONArray;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnListFragmentInteractionListener{


    public final String PREFERENCE = "com.example.rehabcalculator";
    public final String key01 = "key01";
    public final String key02 = "key02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }


    @Override
    public void onListFragmentInteraction(CalendarItem item) {

    }



}
