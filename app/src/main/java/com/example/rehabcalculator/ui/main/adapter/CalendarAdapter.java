package com.example.rehabcalculator.ui.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.CalendarFragment.OnListFragmentInteractionListener;
import com.example.rehabcalculator.ui.main.MainViewModel;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.HolidayItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.recyclerview.widget.RecyclerView;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private final MainViewModel mModel;
    private HashMap<String, CalendarItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private final Date mDate;
    private final int mdaysHeader = 7;
    private int mStartDayPosition;
    private int mFirstSundayDay;
    private Calendar cal;
    private HashMap<Integer, HolidayItem> holidays;

    public CalendarAdapter(Context context, MainViewModel model, OnListFragmentInteractionListener listener) {
        mContext = context;
        mModel = model;
        mListener = listener;
        cal = model.getSelectCal();
        mDate = cal.getTime();
        cal.set(Calendar.DATE, 1);

        int dayOfWeek_1st = cal.get(Calendar.DAY_OF_WEEK); //2020.08.01 => 7(토요일)
        mFirstSundayDay = 9 - dayOfWeek_1st;

        mStartDayPosition = dayOfWeek_1st-1;

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        try {
             holidays = Utils.getRestDeInfo(year, month);
            Log.d("hkyeom", "aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int enddayofmonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(model.getCalendarSaveMap() == null ||
                model.getCalendarSaveMap().get(Utils.getCalendarMapKey(year, month, 1))==null) {
            mValues = model.getCalendarInitList(year, month, enddayofmonth);
        } else {
            // mValues = (HashMap<String, CalendarItem>) ;
            mValues = Utils.getYMCalendarItemList(model.getCalendarSaveMap(), year, month, enddayofmonth);
        }
        addTherapyInfo();
    }

    public HashMap<String, Integer> getMonthCheck() {
        return Utils.getMonthCheck(mValues, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }


    //치료정보 추가 페이지에서 추가한 정보를 달력에 기록한다.
    public void addTherapyInfo() {

        ArrayList<TherapyContents> add_list;
        if(mModel.getNewAddList() != null) {
            add_list = mModel.getNewAddList();
        } else {
            add_list = mModel.getSavedlist();
        }

        for (TherapyContents content : add_list) {
            TherapyContents clone = content.clone();
            if(!mModel.getSavedlist().contains(content)) {
                mModel.addItemSavedlist(clone);
            }

            ArrayList<Integer> somdays = getTheDatesOfSomeDayOfWeek(content.getDayOfWeek());
            for (Integer i : somdays) {
                boolean equal = false;
                ArrayList<TherapyContents> idayConList = mValues.get(Utils.getCalendarMapKey(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i)).getList();
                for(int j = 0; idayConList != null &&  j < idayConList.size(); j++) {
                    TherapyContents contents = idayConList.get(j);
                    if(contents.equals(clone)) {
                        equal = true;
                    }
                }

                //hkyeom
                if(!equal) {
                    mValues.get(Utils.getCalendarMapKey(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i)).addListItem(clone);
                }
            }
        }
        mModel.setNewAddList(null);

        notifyDataSetChanged();
    }

    private ArrayList<Integer> getTheDatesOfSomeDayOfWeek(int dayOfweek) {
        ArrayList<Integer> ret = new ArrayList<>();
        for(int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == dayOfweek) {
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
            String positionKey = Utils.getCalendarMapKey(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), nPosition+1);
            holder.mItem = mValues.get(positionKey);
            //hkyeom

            //빨간날1
            if(mValues.get(positionKey).getDay() %7 == mFirstSundayDay) {
                holder.mDayView.setTextColor(Color.RED);
            } else if(holidays.get(nPosition+1) != null ) {
                holder.mDayView.setTextColor(Color.RED);
                holder.mHolidayView.setText(holidays.get(nPosition+1).getHolidayName());
            }

            holder.mDayView.setText(String.valueOf(mValues.get(positionKey).getDay()));
            if(mValues.get(positionKey).getList() != null) {
                for(int i = 0 ; i < mValues.get(positionKey).getList().size() && i <3; i++) {
                    holder.mContents[i].setText(mValues.get(positionKey).getList().get(i).getTherapistName());
                }
                if(mValues.get(positionKey).getList().size() > 3) {
                    holder.mMoreView.setText(mValues.get(positionKey).getList().size()-3+"+");
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
    }

    @Override
    public int getItemCount() {
        return mdaysHeader + mStartDayPosition + mValues.size();
    }

    public void clear() {
        int size = mValues.size();
        //mValues.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void saveCalendarData() {
        mModel.setCalendarSaveMap((HashMap<String, CalendarItem>) mValues.clone());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDayView;
        public final TextView mHolidayView;
        public final LinearLayout mContainer;
        public final TextView mContent1View;
        public final TextView mContent2View;
        public final TextView mContent3View;
        public final TextView mMoreView;
        public CalendarItem mItem;
        public TextView mContents[];

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDayView = view.findViewById(R.id.day);
            mHolidayView = view.findViewById(R.id.holiday_name);
            mContainer = view.findViewById(R.id.contents_container);
            mContent1View = view.findViewById(R.id.content1);
            mContent2View = view.findViewById(R.id.content2);
            mContent3View = view.findViewById(R.id.content3);
            mMoreView = view.findViewById(R.id.more);
            mContents = new TextView[]{mContent1View, mContent2View, mContent3View};
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
