package com.example.rehabcalculator.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.CalendarFragment.OnListFragmentInteractionListener;
import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private final MainViewModel mModel;
    private final ArrayList<CalendarItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private final Date mDate;
    private final int mdaysHeader = 7;
    private int dayOfWeek_1st;
    private int mStartDayPosition;


    public CalendarAdapter(Context context, MainViewModel model, OnListFragmentInteractionListener listener, Date date) {
        mContext = context;
        mModel = model;
        mListener = listener;
        mDate = date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);

        dayOfWeek_1st = cal.get(Calendar.DAY_OF_WEEK);
        mStartDayPosition = dayOfWeek_1st-1;

        int enddayofmonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        mValues = model.getCalendarInitList(enddayofmonth);

    }

    public void addList() {
        if(mModel.getAddList() != null) {
            for (TherapyContents content : mModel.getAddList()) {
                ArrayList<Integer> somdays = getTheDatesOfSomeDayOfWeek(content.getDayOfWeek());
                for (Integer i : somdays) {
                    mValues.get(i-1).setListItem(content);
                }
            }
        }

        notifyDataSetChanged();
    }

    private int getDayOfWeek(int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        cal.set(Calendar.DATE, dayOfMonth);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    private ArrayList<Integer> getTheDatesOfSomeDayOfWeek(int dayOfweek) {
        ArrayList<Integer> ret = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        for(int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            if(getDayOfWeek(i) == dayOfweek) {
                ret.add(i);
            }
        }
        return ret;
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
            holder.mDayView.setText(mContext.getResources().getStringArray(R.array.dayofweek)[position+1]);
            holder.mContainer.setVisibility(View.GONE);
        } else if(position < mdaysHeader + mStartDayPosition) { //1일 전에 비어있는 칸
            holder.mDayView.setVisibility(View.INVISIBLE);
            holder.mContainer.setVisibility(View.INVISIBLE);
        } else {
            int nPosition = position - mdaysHeader - mStartDayPosition;
            holder.mItem = mValues.get(nPosition);
            holder.mDayView.setText(String.valueOf(mValues.get(nPosition).getDay()));
            if(mValues.get(nPosition).getList() != null) {
                for(int i =0; i < mValues.get(nPosition).getList().size() ; i++) {
                    holder.mContent1View.setText(mValues.get(nPosition).getList().get(i).getTherapistName());
                }
            }


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
        public final LinearLayout mContainer;
        public final TextView mContent1View;
        public final TextView mContent2View;
        public final TextView mContent3View;
        public CalendarItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDayView = view.findViewById(R.id.day);
            mContainer = view.findViewById(R.id.contents_container);
            mContent1View = view.findViewById(R.id.content1);
            mContent2View = view.findViewById(R.id.content2);
            mContent3View = view.findViewById(R.id.content3);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
