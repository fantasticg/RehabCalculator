package com.example.rehabcalculator.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.rehabcalculator.AddAdapter;
import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddFragment extends Fragment {

    private MainViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private ToggleButton sunBtn;
    private ToggleButton monBtn;
    private ToggleButton tueBtn;
    private ToggleButton wedBtn;
    private ToggleButton thuBtn;
    private ToggleButton friBtn;
    private ToggleButton satBtn;

    private AddAdapter adpter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mRecyclerView = getView().findViewById(R.id.day_list);


        monBtn = getView().findViewById(R.id.toggle_mon);
        monBtn.setChecked(true);
        monBtn.setOnCheckedChangeListener(checkListener);
        tueBtn = getView().findViewById(R.id.toggle_tue);
        tueBtn.setOnCheckedChangeListener(checkListener);
        wedBtn = getView().findViewById(R.id.toggle_wed);
        wedBtn.setOnCheckedChangeListener(checkListener);
        thuBtn = getView().findViewById(R.id.toggle_thu);
        thuBtn.setOnCheckedChangeListener(checkListener);
        friBtn = getView().findViewById(R.id.toggle_fri);
        friBtn.setOnCheckedChangeListener(checkListener);
        satBtn = getView().findViewById(R.id.toggle_sat);
        satBtn.setOnCheckedChangeListener(checkListener);
        sunBtn = getView().findViewById(R.id.toggle_sun);
        sunBtn.setOnCheckedChangeListener(checkListener);

        FloatingActionButton fab = getView().findViewById(R.id.save);
        fab.setOnClickListener(clickview ->{
            NavHostFragment.findNavController(AddFragment.this)
                    .navigate(R.id.action_Add_to_Calendar);
        } );

        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adpter = new AddAdapter(requireActivity(), mViewModel);
        mRecyclerView.setAdapter(adpter);

    }

    private CompoundButton.OnCheckedChangeListener checkListener = (buttonView, isChecked) -> {
        if(R.id.toggle_mon == buttonView.getId()) {
            adpter.setOnOff(Const.MONDAY, isChecked);
        } else if(R.id.toggle_tue == buttonView.getId()) {
            adpter.setOnOff(Const.TUESDAY, isChecked);
        } else if(R.id.toggle_wed == buttonView.getId()) {
            adpter.setOnOff(Const.WEDNESDAY, isChecked);
        } else if(R.id.toggle_thu == buttonView.getId()) {
            adpter.setOnOff(Const.THURSDAY, isChecked);
        } else if(R.id.toggle_fri == buttonView.getId()) {
            adpter.setOnOff(Const.FRIDAY, isChecked);
        } else if(R.id.toggle_sat == buttonView.getId()) {
            adpter.setOnOff(Const.SATURDAY, isChecked);
        } else if(R.id.toggle_sun == buttonView.getId()) {
            adpter.setOnOff(Const.SUNDAY, isChecked);
        }



    };

}
