package com.example.rehabcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.rehabcalculator.ui.main.CalendarFragment;
import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnListFragmentInteractionListener{

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mViewModel= new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.setCalendarSaveMap(Utils.ReadCalendarData(this));
        mViewModel.setAddSaveList(Utils.ReadAddData(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.setCalendarPref(this, mViewModel.getCalendarSaveMap());
        Utils.setAddPref(this, mViewModel.getSavedlist());
    }

    @Override
    public void onListFragmentInteraction(CalendarItem item) {

    }



}
