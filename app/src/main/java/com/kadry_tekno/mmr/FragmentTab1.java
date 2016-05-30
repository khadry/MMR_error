package com.kadry_tekno.mmr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FragmentTab1 extends Fragment {
    private SimpleAdapter mAdapter;

    private ReminderDatabase rb;
    private SimpleAdapter adapter;

    public FragmentTab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_tab1, container, false);
//        return inflater.inflate(R.layout.tabs, container, false);

        rb = new ReminderDatabase(getActivity());
        RecyclerView mList = (RecyclerView) view.findViewById(R.id.reminder_list);
        TextView mNoReminderView = (TextView) view.findViewById(R.id.no_reminder_text);
        // To check is there are saved reminders
        // If there are no reminders display a message asking the user to create reminders
        List<Reminder> mTest = rb.getAllReminders();
        if (mTest.isEmpty()) {
            mNoReminderView.setVisibility(View.VISIBLE);
        }
        // Create recycler view
        mList.setLayoutManager(getLayoutManager());
        registerForContextMenu(mList);
        mAdapter = new SimpleAdapter();
        mAdapter.setItemCount(getDefaultItemCount());
        mList.setAdapter(mAdapter);

        return view;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {


//        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        return null;
    }

    protected int getDefaultItemCount() {
        return 100;
    }


}
