package com.kadry_tekno.mmr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    private ReminderDatabase rb;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.tabs, container, false);
        return inflater.inflate(R.layout.tabs, container, false);
//
//        rb = new ReminderDatabase(getActivity());
//       RecyclerView mList = (RecyclerView) view.findViewById(R.id.reminder_list);
//       TextView mNoReminderView = (TextView) view.findViewById(R.id.no_reminder_text);
//        // To check is there are saved reminders
//        // If there are no reminders display a message asking the user to create reminders
//        List<Reminder> mTest = rb.getAllReminders();
//        if (mTest.isEmpty()) {
//            mNoReminderView.setVisibility(View.VISIBLE);
//        }
//
//        return view;
    }




}
