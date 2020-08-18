package com.example.rehabcalculator.ui.main.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Const;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {

    private Context context = null;
    private final ArrayList<TherapyContents> mValues;
    private ArrayList<TherapyContents> mAddList;
    private final MainViewModel model;
    int[] on = new int[]{-1, Const.OFF, Const.ON, Const.OFF, Const.OFF, Const.OFF, Const.OFF, Const.OFF};
    private Calendar start_cal, end_cal;

    TimePickerDialog dialog;

    private ViewGroup parent;
    public AddAdapter(Context context, MainViewModel model) {
        this.context = context;
        this.model = model;
        this.mValues = model.getDayInitList();
        start_cal = Calendar.getInstance();
        end_cal = Calendar.getInstance();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        start_cal.setTime(date);
        end_cal.setTime(date);

    }

    public void setOnOff(int dayOfWeek, boolean checked){
        if(checked) {
            on[dayOfWeek] = 1;
        } else {
            on[dayOfWeek] = 0;
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


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        if(on[holder.mItem.getDayOfWeek()] < 1) {
            holder.mView.setVisibility(View.GONE);
            holder.mView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
        } else {
            holder.mView.setVisibility(View.VISIBLE);
            holder.mView.setLayoutParams(new AbsListView.LayoutParams(-1,-2));
        }
        holder.mDayView.setText(context.getResources().getStringArray(R.array.dayofweek)[holder.mItem.getDayOfWeek()]);
        holder.mStartDate.setText(Utils.timeTextOnBtn(start_cal.getTime()));
        holder.mStartDate.setOnClickListener(v -> {
            dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                start_cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                start_cal.set(Calendar.MINUTE, minute);
                end_cal.set(Calendar.HOUR_OF_DAY, hourOfDay+1);
                end_cal.set(Calendar.MINUTE, minute);
                holder.mStartDate.setText(Utils.timeTextOnBtn(start_cal.getTime()));
                holder.mEndDate.setText(Utils.timeTextOnBtn(end_cal.getTime()));
            }, start_cal.get(Calendar.HOUR_OF_DAY), start_cal.get(Calendar.MINUTE), false);
            dialog.show();
        });


        holder.mEndDate.setText(Utils.timeTextOnBtn(end_cal.getTime()));
        holder.mEndDate.setOnClickListener(v -> {
            dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                end_cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                end_cal.set(Calendar.MINUTE, minute);
                holder.mEndDate.setText(Utils.timeTextOnBtn(end_cal.getTime()));
            }, end_cal.get(Calendar.HOUR_OF_DAY), end_cal.get(Calendar.MINUTE), false);
            dialog.show();
        });

        holder.mNumber.setText("1");
        holder.mItem.setNum(Integer.parseInt(holder.mNumber.getText().toString()));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<TherapyContents> getAddList() {
        mAddList = new ArrayList<>();
        TherapyContents newContents;
        for(TherapyContents content : mValues) {
            if(on[content.getDayOfWeek()] == Const.ON) {
                //hkyeom
                // newContents = new TherapyContent(content);
                mAddList.add(content);
            }
        }

        return mAddList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDayView;
        public final Button mStartDate;
        public final Button mEndDate;
        public final EditText mNumber;
        public TherapyContents mItem;

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
