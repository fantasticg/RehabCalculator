package com.example.rehabcalculator.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rehabcalculator.R;
import com.example.rehabcalculator.ui.main.adapter.CalendarAdapter;
import com.example.rehabcalculator.ui.main.content.CalendarItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.Nullable;
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
    private MainViewModel mViewModel;


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mViewModel= new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mViewModel.setCalendarMap(ReadCalendarData(requireActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(clickview ->{
            NavHostFragment.findNavController(CalendarFragment.this)
                    .navigate(R.id.action_Calendar_to_Add);
        } );

        FloatingActionButton cal = view.findViewById(R.id.cal);
        cal.setOnClickListener(clickview -> Snackbar.make(view, mViewModel.getCountMap().toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        // Set the adapter
        if (view.findViewById(R.id.list) instanceof RecyclerView) {
            RecyclerView recyclerView = view.findViewById(R.id.list);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            mAdapter = new CalendarAdapter(requireActivity(), mViewModel, mListener, date);
            recyclerView.setAdapter(mAdapter);
        }


        return view;
    }

    private CalendarAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.addTherapyList();//치료정보 추가 하고 난 후 캘린더에 데이터 업데이트
        setCalendarPref(requireActivity(), mViewModel.getCalendarMap());
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        RecyclerView recyclerView = getView().findViewById(R.id.list);
        ((CalendarAdapter)recyclerView.getAdapter()).clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
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

/*
    private void setTherapyPref(Context context, ArrayList<TherapyContents> values) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(values);
        editor.putString("Therapy", json);
        editor.commit();
    }

    private ArrayList<TherapyContents> ReadTherapyData(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Therapy", "EMPTY");
        Type type = new TypeToken<ArrayList<TherapyContents>>() {
        }.getType();
        ArrayList<TherapyContents> arrayList = gson.fromJson(json, type);
        return arrayList;
    } */

    private void setCalendarPref(Context context, HashMap<String, CalendarItem> values) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(values);
        editor.putString("Calendar", json);
        editor.commit();
    }

    private HashMap<String, CalendarItem> ReadCalendarData(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Calendar", "EMPTY");
        Type type = new TypeToken<HashMap<String, CalendarItem>>(){}.getType();
        if(json.equals("EMPTY")) {
            return null;
        }
        HashMap<String, CalendarItem> map = gson.fromJson(json, type);
        return map;
    }


}
