package com.example.rehabcalculator.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.content.TherapyContents;
import com.example.rehabcalculator.ui.main.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DayEditDialog extends DialogFragment {

    private MainViewModel mViewModel;
    private DayEditAdapter mAdapter;
    private Button mAdd;
    private Button mEdit;
    private Button mDel;


    public static DayEditDialog newInstance() {

        Bundle args = new Bundle();

        DayEditDialog fragment = new DayEditDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel= new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogFragmentStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_dayedit, container);
        RecyclerView rc = view.findViewById(R.id.daylist_list);
        rc.setLayoutManager(new LinearLayoutManager(requireContext()));

        int year =0;
        int month =0;
        int day =0;

        if (getArguments() != null) {
            //item_year item_month item_day
            year = getArguments().getInt("item_year");
            month = getArguments().getInt("item_month");
            day = getArguments().getInt("item_day");

            HashMap<String, CalendarItem> temp = Utils.getYMCalendarItemList(mViewModel.getCalendarSaveMap(), year, month, day);
            mAdapter = new DayEditAdapter(temp.get(Utils.getCalendarMapKey(year, month, day)).getList());
            rc.setAdapter(mAdapter);

        }

        view.findViewById(R.id.daylist_alldel).setOnClickListener(v-> {
            mAdapter.allCheckOrNone(((CheckBox)v).isChecked());
        });
        mAdd = view.findViewById(R.id.daylist_add);
        mEdit = view.findViewById(R.id.daylist_edit);
        mDel = view.findViewById(R.id.daylist_del);

        return view;
    }

    public class DayEditAdapter extends RecyclerView.Adapter<DayEditAdapter.ViewHolder> {

        private ArrayList<TherapyContents> mList;

        private ArrayList<Boolean> mCheckList;

        private int checkCount = 0;

        DayEditAdapter(ArrayList<TherapyContents> list) {
            mList = list;
            mCheckList = new ArrayList<>();
            for(int i=0;i< mList.size();i++){
                mCheckList.add(new Boolean(Boolean.FALSE));
            }
        }



        @NonNull
        @Override
        public DayEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.editday_listitem, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mEditDayCheck.setText(mList.get(position).getTherapistName());
            holder.mEditDayCheck.setChecked(mCheckList.get(position));
            holder.mEditStartTimeTV.setText(Utils.timeTextOnBtn(mList.get(position).getStartTime()));
            holder.mEditDayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        checkCount++;
                        mCheckList.set(position, Boolean.TRUE);
                    } else {
                        checkCount--;
                        mCheckList.set(position, Boolean.FALSE);
                    }
                    Log.d("hkyeom", "checkCount : " + checkCount);
                }
            });
            holder.mView.setOnClickListener(v -> holder.mEditDayCheck.setChecked(!holder.mEditDayCheck.isChecked()));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void allCheckOrNone(boolean checked) {
            for(int i = 0 ; i < mCheckList.size(); i++) {
                mCheckList.set(i, checked);
            }
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final CheckBox mEditDayCheck;
            public final TextView mEditStartTimeTV;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mEditDayCheck = view.findViewById(R.id.listitem_edit_thpname);
                mEditStartTimeTV = view.findViewById(R.id.listitem_edit_starttime);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mEditDayCheck.getText() + "'";
            }
        }

    }
}
