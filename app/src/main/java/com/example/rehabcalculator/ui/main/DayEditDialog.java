package com.example.rehabcalculator.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
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
    private CheckBox mAllCheck;
    private ArrayList<Boolean> mCheckList;
    private int mYear = 0;
    private int mMonth = 0;
    private int mDay = 0;
    private DialogListener mDialogListener;

    private CompoundButton.OnCheckedChangeListener mCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView == mAllCheck) {
                for(int i = 0 ; i < mCheckList.size(); i++) {
                    mCheckList.set(i, isChecked);
                }
                mAdapter.notifyDataSetChanged();
                buttonsSettings();
                return;
            }
            mCheckList.set((Integer) buttonView.getTag(), isChecked);

            boolean checked = true;
            for(int i = 0 ; i < mCheckList.size()-1; i++) {
                checked = checked && mCheckList.get(i);
            }

            mAllCheck.setOnCheckedChangeListener(null);
            mAllCheck.setChecked(checked);
            mAllCheck.setOnCheckedChangeListener(mCheckListener);

            buttonsSettings();


        }
    };

    private void buttonsSettings() {
        int check_count = mAdapter.checkCount();
        if (check_count == 0) {
            mAdd.setEnabled(true);
            mEdit.setEnabled(false);
            mDel.setEnabled(false);
        } else if (check_count == 1) {
            mAdd.setEnabled(false);
            mEdit.setEnabled(true);
            mDel.setEnabled(true);
        } else {
            mAdd.setEnabled(false);
            mEdit.setEnabled(false);
            mDel.setEnabled(true);
        }
    }

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

        ArrayList<TherapyContents> theDaySchedules = null;

        if (getArguments() != null) {
            //item_year item_month item_day
            mYear = getArguments().getInt("item_year");
            mMonth = getArguments().getInt("item_month");
            mDay = getArguments().getInt("item_day");

            theDaySchedules = Utils.getTheDaySchedules(mViewModel.getCalendarSaveMap(), mYear, mMonth, mDay);
            mAdapter = new DayEditAdapter(theDaySchedules);
            rc.setAdapter(mAdapter);

        }
        mAllCheck = view.findViewById(R.id.daylist_alldel);
        mAllCheck.setText(Utils.getEditDayTitle(mYear, mMonth,mDay));
        mAllCheck.setOnCheckedChangeListener(mCheckListener);
        mAllCheck.setTag(theDaySchedules != null ? theDaySchedules.size() : 0);
        if(theDaySchedules == null || theDaySchedules.isEmpty()) {
            mAllCheck.setEnabled(false);
        } else {
            mAllCheck.setEnabled(true);
        }
        mAdd = view.findViewById(R.id.daylist_add);
        mAdd.setOnClickListener(v -> {
            mDialogListener.goAddFragment(mYear, mMonth, mDay);
        });
        mEdit = view.findViewById(R.id.daylist_edit);
        mDel = view.findViewById(R.id.daylist_del);
        mDel.setOnClickListener(v -> {

            //선택한 스케줄 삭제.
            HashMap<String, CalendarItem> temp2 = Utils.getYMCalendarItemList(mViewModel.getCalendarSaveMap(), mYear, mMonth, mDay);
            ArrayList<TherapyContents> tempList = temp2.get(Utils.getCalendarMapKey(mYear, mMonth, mDay)).getList();
            for(int i = tempList.size()-1 ; i >= 0 ; i--) {
                if(mCheckList.get(i).equals(Boolean.TRUE)) {
                    mViewModel.delCalendarItemWithContent(mYear, mMonth, mDay, tempList.get(i));
                    mCheckList.remove(i);
                }
            }

            mAdapter.notifyDataSetChanged();

            if(mAllCheck.isChecked()) {
                dismiss();
            }

            buttonsSettings();

        });

        return view;
    }

    public void setDialogListener(DialogListener listener) {
        mDialogListener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialogListener.dialogClose();
    }

    public class DayEditAdapter extends RecyclerView.Adapter<DayEditAdapter.ViewHolder> {

        private ArrayList<TherapyContents> mList;


        DayEditAdapter(ArrayList<TherapyContents> list) {
            mList = list;
            mCheckList = new ArrayList<>();

            //제일 마지막값은 mAllCheck 값임.
            for(int i=0;mList != null && i<= mList.size();i++){
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
            holder.mEditDayCheck.setTag(position);
            holder.mEditDayCheck.setChecked(mCheckList.get(mCheckList.size()-1));
            holder.mEditStartTimeTV.setText(Utils.timeTextOnBtn(mList.get(position).getStartTime()));
            holder.mEditDayCheck.setOnCheckedChangeListener(mCheckListener);
            holder.mView.setOnClickListener(v -> holder.mEditDayCheck.setChecked(!holder.mEditDayCheck.isChecked()));
        }

        @Override
        public int getItemCount() {
            return mList!=null? mList.size() : 0;
        }

        public int checkCount() {
            int ret = 0;
            for(boolean checked : mCheckList) {
                if(checked) {
                    ret++;
                }
            }
            return ret;
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

    public interface DialogListener
    {
        public void dialogClose();//or whatever args you want

        public void goAddFragment(int year, int month, int day);
    }
}
