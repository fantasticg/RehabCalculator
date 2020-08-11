package com.example.rehabcalculator;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.CenterContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {

    private Context context = null;
    private final ArrayList<CenterContents> mValues;
    private final MainViewModel model;
    int[] on = new int[]{Const.OFF, Const.ON, Const.OFF, Const.OFF, Const.OFF, Const.OFF, Const.OFF};
    private Calendar start_cal, end_cal;

    TimePickerDialog dialog;

    private ViewGroup parent;
    public AddAdapter(Context context, MainViewModel model) {
        this.context = context;
        this.model = model;
        this.mValues = model.getDayEmptyList();
        start_cal = Calendar.getInstance();
        end_cal = Calendar.getInstance();
    }

    public void setOnOff(int position, boolean checked){
        if(checked) {
            on[position] = 1;
        } else {
            on[position] = 0;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_listitem, parent, false);
        return new ViewHolder(view);
    }

    private Date[] dates = new Date[]{Utils.getDate(15, 0), Utils.getDate(16, 0)};

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(on[position] < 1) {
            holder.mView.setVisibility(View.GONE);
            holder.mView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
        } else {
            holder.mView.setVisibility(View.VISIBLE);
            holder.mView.setLayoutParams(new AbsListView.LayoutParams(-1,-2));
        }
        holder.mDayView.setText(context.getResources().getStringArray(R.array.dayofweek)[position]);
        holder.mStartDate.setText("AM 3:00");
        start_cal.setTime(dates[0]);
        holder.mStartDate.setOnClickListener(v -> {
            dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                Date[] dates = new Date[]{Utils.getDate(hourOfDay, minute), Utils.getDate(hourOfDay+1, minute)};
                holder.mStartDate.setText(Utils.timeTextOnBtn(dates[0]));
                holder.mEndDate.setText(Utils.timeTextOnBtn(dates[1]));
                //mValues.get(position).setDate();
            }, start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE), false);
            dialog.show();
        });


        holder.mEndDate.setText("AM 4:00");
        end_cal.setTime(dates[1]);
        holder.mEndDate.setOnClickListener(v -> {
            dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                Date[] dates = new Date[]{Utils.getDate(start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE)), Utils.getDate(hourOfDay, minute)};
                holder.mStartDate.setText(Utils.timeTextOnBtn(dates[0]));
                holder.mEndDate.setText(Utils.timeTextOnBtn(dates[1]));
                //mValues.get(position).setDate();
            }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE), false);
            dialog.show();
        });

        holder.mNumber.setText("1");

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDayView;
        public final Button mStartDate;
        public final Button mEndDate;
        public final EditText mNumber;
        public CenterContents mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDayView = view.findViewById(R.id.addtime_day);
            mStartDate = view.findViewById(R.id.addtime_startdate);
            mEndDate = view.findViewById(R.id.addtime_enddate);
            mNumber = view.findViewById(R.id.addtime_numbers);
        }

        @Override
        public String toString() {
            return mDayView.toString() + " ";
        }
    }

}
