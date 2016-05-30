package com.kadry_tekno.mmr;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {
    private static MultiSelector multiSelector;
    private RecyclerView mList;
    private ArrayList<ReminderItem> mItems;
    private int mTempPost;
    private LinkedHashMap<Integer, Integer> IDmap = new LinkedHashMap<>();
    private TextView mDateAndTimeText;
    private ReminderDatabase rb;

    public SimpleAdapter() {
        mItems = new ArrayList<>();
    }


    public void setItemCount(int count) {
        mItems.clear();
        mItems.addAll(generateData(count));
        notifyDataSetChanged();
    }

    public void onDeleteItem(int count) {
        mItems.clear();
        mItems.addAll(generateData(count));
    }

    public void removeItemSelected(int selected) {
        if (mItems.isEmpty()) return;
        mItems.remove(selected);
        notifyItemRemoved(selected);
    }

    @Override
    public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View root = inflater.inflate(R.layout.recycle_items, container, false);

        return new VerticalItemHolder(root, this);

    }

    @Override
    public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
        ReminderItem item = mItems.get(position);
        itemHolder.setReminderTitle(item.mTitle);
        itemHolder.setReminderDateTime(item.mDateTime);
        itemHolder.setReminderRepeatInfo(item.mRepeat, item.mRepeatNo, item.mRepeatType);
        itemHolder.setActiveImage(item.mActive);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public class VerticalItemHolder extends SwappingHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
        private ImageView mActiveImage, mThumbnailImage;
        private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
        private TextDrawable mDrawableBuilder;
        private SimpleAdapter mAdapter;
        private MultiSelector mMultiSelector = new MultiSelector();

        public VerticalItemHolder(View itemView, SimpleAdapter adapter) {
            super(itemView, multiSelector);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setLongClickable(true);
            // Initialize adapter for the items
            mAdapter = adapter;

            // Initialize views
            mTitleText = (TextView) itemView.findViewById(R.id.recycle_title);
            mDateAndTimeText = (TextView) itemView.findViewById(R.id.recycle_date_time);
            mRepeatInfoText = (TextView) itemView.findViewById(R.id.recycle_repeat_info);
            mActiveImage = (ImageView) itemView.findViewById(R.id.active_image);
            mThumbnailImage = (ImageView) itemView.findViewById(R.id.thumbnail_image);

        }

        // On clicking a reminder item
        @Override
        public void onClick(View v) {
//            if (!mMultiSelector.tapSelection(this)) {
//                mTempPost = mList.getChildAdapterPosition(v);
//
//                int mReminderClickID = IDmap.get(mTempPost);
//                selectReminder(mReminderClickID);
//
//            } else if (mMultiSelector.getSelectedPositions().isEmpty()) {
//                mAdapter.setItemCount(getDefaultItemCount());
//            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public void setReminderTitle(String mTitle) {
            mTitleText.setText(mTitle);
            String letter = "A";

            if (mTitle != null && !mTitle.isEmpty()) {
                letter = mTitle.substring(0, 1);
            }

            int color = mColorGenerator.getRandomColor();

            // Create a circular icon consisting of  a random background colour and first letter of title
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color);
            mThumbnailImage.setImageDrawable(mDrawableBuilder);
        }

        public void setReminderDateTime(String mDateTime) {
        }

        public void setReminderRepeatInfo(String mRepeat, String mRepeatNo, String mRepeatType) {
            if (mRepeat.equals("true")) {
                mRepeatInfoText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
            } else if (mRepeat.equals("false")) {
                mRepeatInfoText.setText("Repeat Off");
            }
        }

        public void setActiveImage(String mActive) {
            if (mActive.equals("true")) {
                mActiveImage.setImageResource(R.drawable.ic_notifications_on_white_24dp);
            } else if (mActive.equals("false")) {
                mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
            }
        }
    }


    private void selectReminder(int mReminderClickID) {
        mDateAndTimeText.setText(mReminderClickID);

    }

    // Class for recycler view items
    public class ReminderItem {
        public String mTitle;
        public String mDateTime;
        public String mRepeat;
        public String mRepeatNo;
        public String mRepeatType;
        public String mActive;

        public ReminderItem(String Title, String DateTime, String Repeat, String RepeatNo, String RepeatType, String Active) {
            this.mTitle = Title;
            this.mDateTime = DateTime;
            this.mRepeat = Repeat;
            this.mRepeatNo = RepeatNo;
            this.mRepeatType = RepeatType;
            this.mActive = Active;
        }
    }
    // Class to compare date and time so that items are sorted in ascending order
    public class DateTimeComparator implements Comparator {
        DateFormat f = new SimpleDateFormat("dd/mm/yyyy hh:mm");

        public int compare(Object a, Object b) {
            String o1 = ((DateTimeSorter) a).getDateTime();
            String o2 = ((DateTimeSorter) b).getDateTime();

            try {
                return f.parse(o1).compareTo(f.parse(o2));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }


    // Generate real data for each item
    public List<ReminderItem> generateData(int count) {
        ArrayList<SimpleAdapter.ReminderItem> items = new ArrayList<>();

        // Get all reminders from the database
        List<Reminder> reminders = rb.getAllReminders();

        // Initialize lists
        List<String> Titles = new ArrayList<>();
        List<String> Repeats = new ArrayList<>();
        List<String> RepeatNos = new ArrayList<>();
        List<String> RepeatTypes = new ArrayList<>();
        List<String> Actives = new ArrayList<>();
        List<String> DateAndTime = new ArrayList<>();
        List<Integer> IDList = new ArrayList<>();
        List<DateTimeSorter> DateTimeSortList = new ArrayList<>();

        // Add details of all reminders in their respective lists
        for (Reminder r : reminders) {
            Titles.add(r.getTitle());
            DateAndTime.add(r.getDate() + " " + r.getTime());
            Repeats.add(r.getRepeat());
            RepeatNos.add(r.getRepeatNo());
            RepeatTypes.add(r.getRepeatType());
            Actives.add(r.getActive());
            IDList.add(r.getID());
        }

        int key = 0;

        // Add date and time as DateTimeSorter objects
        for (int k = 0; k < Titles.size(); k++) {
            DateTimeSortList.add(new DateTimeSorter(key, DateAndTime.get(k)));
            key++;
        }

        // Sort items according to date and time in ascending order
        Collections.sort(DateTimeSortList, new DateTimeComparator());

        int k = 0;

        // Add data to each recycler view item
        for (DateTimeSorter item : DateTimeSortList) {
            int i = item.getIndex();

            items.add(new SimpleAdapter.ReminderItem(Titles.get(i), DateAndTime.get(i), Repeats.get(i),
                    RepeatNos.get(i), RepeatTypes.get(i), Actives.get(i)));
            IDmap.put(k, IDList.get(i));
            k++;
        }
        return items;
    }
}