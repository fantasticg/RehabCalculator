package com.example.rehabcalculator;

import android.os.Bundle;
import android.view.Menu;

import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
