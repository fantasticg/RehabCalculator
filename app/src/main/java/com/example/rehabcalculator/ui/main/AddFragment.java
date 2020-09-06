package com.example.rehabcalculator.ui.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.adapter.AddAdapter;
import com.example.rehabcalculator.ui.main.content.TherapistNamePriceItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    private CheckBox fixCheckBox;

    private ToggleButton sunBtn;
    private ToggleButton monBtn;
    private ToggleButton tueBtn;
    private ToggleButton wedBtn;
    private ToggleButton thuBtn;
    private ToggleButton friBtn;
    private ToggleButton satBtn;

    private AddAdapter adapter;

    private Calendar startcal = Calendar.getInstance();
    private Calendar endcal = Calendar.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        mRecyclerView = view.findViewById(R.id.day_list);

        fixCheckBox = view.findViewById(R.id.check_fix);
        fixCheckBox.setOnCheckedChangeListener(checkListener);

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
        EditText therapistName = view.findViewById(R.id.edittext_therapistname);
        EditText price = view.findViewById(R.id.edit_price);
        EditText monthlyfee = view.findViewById(R.id.edittext_monthlyfee);

        FloatingActionButton fab = view.findViewById(R.id.save);
        fab.setOnClickListener(clickview ->{

            if(therapistName.getText().toString().isEmpty() || price.getText().toString().isEmpty()) {
                Toast.makeText(requireActivity(), "치료사이름/금액을 입력하세요", LENGTH_LONG).show();
                return;
            }

            ArrayList<TherapyContents> list = adapter.getAddList();

            if(list.size() ==0) {
                Toast.makeText(requireActivity(), "시간을 입력하세요", LENGTH_LONG).show();
                return;
            }

            if(fixCheckBox.isChecked()) { //고정
                TherapistNamePriceItem item = new TherapistNamePriceItem(therapistName.getText().toString(),
                        Integer.parseInt(price.getText().toString()),
                        (monthlyfee.getText().toString().equals("") ? 0 : Integer.parseInt(monthlyfee.getText().toString())));
                for (TherapyContents content : list) {
                    content.setTherapistNamePriceItem(item);
                    //content.setTherapistName(therapistName.getText().toString());
                    //content.setPrice(Integer.parseInt(price.getText().toString()));
                    //content.setMonthlyFee();
                }

                mViewModel.setNewAddList(list);

            } else { //1회성
                EditText onetime_numbers = view.findViewById(R.id.onetime_numbers);
                TherapyContents contents = new TherapyContents(-1, Integer.parseInt(onetime_numbers.getText().toString()),startcal.getTime(), endcal.getTime(),
                        new TherapistNamePriceItem(therapistName.getText().toString(), Integer.parseInt(price.getText().toString()), 0));

                mViewModel.addCalendarItemWithContent(startcal.get(Calendar.YEAR), startcal.get(Calendar.MONTH), startcal.get(Calendar.DAY_OF_MONTH), contents);
            }

            NavHostFragment.findNavController(AddFragment.this)
                    .navigate(R.id.action_Add_to_Calendar);
        } );

        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new AddAdapter(requireActivity(), mViewModel);
        mRecyclerView.setAdapter(adapter);

        Date date = mViewModel.getSelectCal().getTime();
        startcal.setTime(date);
        endcal.setTime(date);

        Button onetime_starttime = view.findViewById(R.id.onetime_starttime);
        Button onetime_endtime = view.findViewById(R.id.onetime_endtime);

        onetime_starttime.setText(Utils.datetimeTextOnBtn(startcal.getTime()));
        onetime_starttime.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (timeview, hourOfDay, minute) -> {
                startcal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startcal.set(Calendar.MINUTE, minute);
                endcal.set(Calendar.HOUR_OF_DAY, hourOfDay+1);
                endcal.set(Calendar.MINUTE, minute);
                onetime_starttime.setText(Utils.datetimeTextOnBtn(startcal.getTime()));
                onetime_endtime.setText(Utils.timeTextOnBtn(endcal.getTime()));
            }, startcal.get(Calendar.HOUR_OF_DAY), startcal.get(Calendar.MINUTE), false);


            DatePickerDialog datePicker = new DatePickerDialog(getContext(), 0, (dateview, year, month, dayOfMonth) -> {
                startcal.set(Calendar.YEAR, year);
                startcal.set(Calendar.MONTH, month);
                startcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                timePickerDialog.show();

            }, startcal.get(Calendar.YEAR), startcal.get(Calendar.MONTH), startcal.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });


        onetime_endtime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (timeview, hourOfDay, minute) -> {
                endcal.set(Calendar.HOUR_OF_DAY, hourOfDay+1);
                endcal.set(Calendar.MINUTE, minute);
                onetime_endtime.setText(Utils.timeTextOnBtn(endcal.getTime()));
            }, startcal.get(Calendar.HOUR_OF_DAY), startcal.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        });

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
        } else if(R.id.check_fix == buttonView.getId()) {
            if (isChecked) {
                getView().findViewById(R.id.fix_subcontent_layout).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.onetime_subcontent_layout).setVisibility(View.GONE);
                getView().findViewById(R.id.monthlyfee_layout).setVisibility(View.VISIBLE);
            } else {
                getView().findViewById(R.id.fix_subcontent_layout).setVisibility(View.GONE);
                getView().findViewById(R.id.onetime_subcontent_layout).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.monthlyfee_layout).setVisibility(View.GONE);
            }
        }
    };


}
