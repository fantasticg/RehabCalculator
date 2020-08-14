package com.example.rehabcalculator.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.adapter.AddAdapter;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.Toast.LENGTH_LONG;

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

    private AddAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        mRecyclerView = view.findViewById(R.id.day_list);


        monBtn = view.findViewById(R.id.toggle_mon);
        monBtn.setChecked(true);
        monBtn.setOnCheckedChangeListener(checkListener);
        tueBtn = view.findViewById(R.id.toggle_tue);
        tueBtn.setOnCheckedChangeListener(checkListener);
        wedBtn = view.findViewById(R.id.toggle_wed);
        wedBtn.setOnCheckedChangeListener(checkListener);
        thuBtn = view.findViewById(R.id.toggle_thu);
        thuBtn.setOnCheckedChangeListener(checkListener);
        friBtn = view.findViewById(R.id.toggle_fri);
        friBtn.setOnCheckedChangeListener(checkListener);
        satBtn = view.findViewById(R.id.toggle_sat);
        satBtn.setOnCheckedChangeListener(checkListener);
        sunBtn = view.findViewById(R.id.toggle_sun);
        sunBtn.setOnCheckedChangeListener(checkListener);
        EditText therapistName = view.findViewById(R.id.textinput_therapistname);
        EditText price = view.findViewById(R.id.textinput_price);
        FloatingActionButton fab = view.findViewById(R.id.save);
        fab.setOnClickListener(clickview ->{

            if(therapistName.getText().toString().isEmpty() || price.getText().toString().isEmpty()) {
                Toast.makeText(requireActivity(), "치료사이름/금액을 입력하세요", LENGTH_LONG).show();
                return;
            }

            ArrayList<TherapyContents> list = adapter.getAddList();

            for(TherapyContents content: list) {
                content.setTherapistName(therapistName.getText().toString());
                content.setPrice(Integer.parseInt(price.getText().toString()));
            }

            mViewModel.setAddList(list);


            NavHostFragment.findNavController(AddFragment.this)
                    .navigate(R.id.action_Add_to_Calendar);
        } );

        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new AddAdapter(requireActivity(), mViewModel);
        mRecyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);

    }

    private CompoundButton.OnCheckedChangeListener checkListener = (buttonView, isChecked) -> {
        if(R.id.toggle_mon == buttonView.getId()) {
            adapter.setOnOff(Const.MONDAY, isChecked);
        } else if(R.id.toggle_tue == buttonView.getId()) {
            adapter.setOnOff(Const.TUESDAY, isChecked);
        } else if(R.id.toggle_wed == buttonView.getId()) {
            adapter.setOnOff(Const.WEDNESDAY, isChecked);
        } else if(R.id.toggle_thu == buttonView.getId()) {
            adapter.setOnOff(Const.THURSDAY, isChecked);
        } else if(R.id.toggle_fri == buttonView.getId()) {
            adapter.setOnOff(Const.FRIDAY, isChecked);
        } else if(R.id.toggle_sat == buttonView.getId()) {
            adapter.setOnOff(Const.SATURDAY, isChecked);
        } else if(R.id.toggle_sun == buttonView.getId()) {
            adapter.setOnOff(Const.SUNDAY, isChecked);
        }
    };

}
