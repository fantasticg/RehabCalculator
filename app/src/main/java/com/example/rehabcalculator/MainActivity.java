package com.example.rehabcalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.rehabcalculator.ui.main.CalendarFragment;
import com.example.rehabcalculator.ui.main.DayEditDialog;
import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

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
    protected void onStop() {
        super.onStop();
        Utils.setCalendarPref(this, mViewModel.getCalendarSaveMap());
        Utils.setAddPref(this, mViewModel.getSavedlist());
    }

    @Override
    public void onListFragmentInteraction(CalendarItem item) {
        Log.d("hkyeom", "aaaa");
        showThedaySchedule(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void showThedaySchedule(CalendarItem item) {

        Bundle result = new Bundle();
        result.putInt("item_year", item.getYear());
        result.putInt("item_month", item.getMonth());
        result.putInt("item_day", item.getDay());

        DayEditDialog de = DayEditDialog.newInstance();
        de.setArguments(result);
        de.show(getSupportFragmentManager(), "EditDayDialog");
    }

}
