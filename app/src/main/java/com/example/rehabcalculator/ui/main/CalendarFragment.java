package com.example.rehabcalculator.ui.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.adapter.CalendarAdapter;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.example.rehabcalculator.ui.main.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CalendarFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 7;
    private OnListFragmentInteractionListener mListener;
    private OnEditDialogListener mDialogListener;
    private MainViewModel mViewModel;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CalendarFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CalendarFragment newInstance(int columnCount, MainViewModel model) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mViewModel= new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mListener = item -> showThedaySchedule(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);

        Calendar current_cal = mViewModel.getSelectCal();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        current_cal.setTime(date);

        Button btn = view.findViewById(R.id.changemonth_btn);
        btn.setText(Utils.calendarMonthBtn(date));

        view.findViewById(R.id.changemonth_btn).setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(getContext(), 0, (dateview, year, month, dayOfMonth) -> {

                current_cal.set(Calendar.YEAR, year);
                current_cal.set(Calendar.MONTH, month);
                current_cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mAdapter.saveCalendarData();
                mAdapter = new CalendarAdapter(requireActivity(), mViewModel, mListener);
                recyclerView.setAdapter(mAdapter);
                btn.setText(Utils.calendarMonthBtn(current_cal.getTime()));
                mViewModel.setSelectCal(current_cal);

            }, current_cal.get(Calendar.YEAR), current_cal.get(Calendar.MONTH), current_cal.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(clickview ->{
            NavHostFragment.findNavController(CalendarFragment.this)
                    .navigate(R.id.action_Calendar_to_Add);
        } );

        FloatingActionButton cal = view.findViewById(R.id.cal);
        cal.setOnClickListener(clickview -> {
            Snackbar.make(view,
                    mAdapter.getMonthCheck().toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        });


        // Set the adapter
        if (view.findViewById(R.id.list) instanceof RecyclerView) {
            recyclerView = view.findViewById(R.id.list);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
            mAdapter = new CalendarAdapter(requireActivity(), mViewModel, mListener);
            recyclerView.setAdapter(mAdapter);
        }


        return view;
    }

    private CalendarAdapter mAdapter;

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.saveCalendarData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        RecyclerView recyclerView = getView().findViewById(R.id.list);
        ((CalendarAdapter)recyclerView.getAdapter()).clear();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CalendarItem item);
    }

    public interface OnEditDialogListener {
        // TODO: Update argument type and name
        void onAddFragmentInteraction();
    }

    private void showThedaySchedule(CalendarItem item) {

        Bundle result = new Bundle();
        result.putInt("item_year", item.getYear());
        result.putInt("item_month", item.getMonth());
        result.putInt("item_day", item.getDay());

        DayEditDialog de = DayEditDialog.newInstance();
        de.setArguments(result);
        de.show(requireActivity().getSupportFragmentManager(), "EditDayDialog");
        de.setDialogListener(new DayEditDialog.DialogListener() {
            @Override
            public void dialogClose() {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void goAddFragment(int year, int month, int day) {
                de.dismiss();
                NavHostFragment.findNavController(CalendarFragment.this)
                        .navigate(R.id.action_Calendar_to_Add);
            }
        });
    }

}
