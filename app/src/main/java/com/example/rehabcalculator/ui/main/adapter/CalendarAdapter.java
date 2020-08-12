package com.example.rehabcalculator.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.CalendarFragment.OnListFragmentInteractionListener;
import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private final MainViewModel mModel;
    private final ArrayList<CalendarItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private final int mMonth;
    private final int mdaysHeader = 7;
    private int mStartDayPosition;

    public CalendarAdapter(Context context, MainViewModel model, OnListFragmentInteractionListener listener, int month) {
        mContext = context;
        mModel = model;
        mListener = listener;
        mMonth = month;

        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        Calendar cal = Calendar.getInstance();

        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        cal.set(Integer.parseInt(curYearFormat.format(date)), mMonth - 1, 1);
        mStartDayPosition = cal.get(Calendar.DAY_OF_WEEK) -1;

        int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        mValues = model.getCalendarInitList(dayOfMonth);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_griditemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(position < mdaysHeader) { //월,화,수...
            holder.mDayView.setText(mContext.getResources().getStringArray(R.array.dayofweek)[position]);
            holder.mContentView.setVisibility(View.GONE);
        } else if(position < mdaysHeader + mStartDayPosition) { //1일 전에 비어있는 칸
            holder.mDayView.setText("");
            holder.mContentView.setText("");
        } else {
            int nPosition = position - mdaysHeader - mStartDayPosition;
            holder.mItem = mValues.get(nPosition);
            holder.mDayView.setText(String.valueOf(mValues.get(nPosition).getDay()));
            holder.mContentView.setText("");

            holder.mView.setOnClickListener(v -> {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mdaysHeader + mStartDayPosition + mValues.size();
    }

    public void clear() {
        int size = mValues.size();
        mValues.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDayView;
        public final TextView mContentView;
        public CalendarItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDayView = view.findViewById(R.id.day);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
