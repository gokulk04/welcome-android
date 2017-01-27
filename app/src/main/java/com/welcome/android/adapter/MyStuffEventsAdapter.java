package com.welcome.android.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welcome.android.R;
import com.welcome.android.objects.Event;

import java.util.List;

/**
 * Created by Gokul on 1/25/2017.
 */
public class MyStuffEventsAdapter extends RecyclerView.Adapter<MyStuffEventsAdapter.ViewHolder>{
    private FragmentActivity activity;
    private List<Event> events;

    public MyStuffEventsAdapter(FragmentActivity activity, List<Event> events) {
        this.activity = activity;
        this.events = events;
    }
    @Override
    public MyStuffEventsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.past_events_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Event event = events.get(i);
        viewHolder.nameView.setText(event.getName());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.txtPrevEventName);
        }
    }
}
