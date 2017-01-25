package com.welcome.android;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gokul on 1/25/2017.
 */
public class MyStuffEventsAdapter extends RecyclerView.Adapter<MyStuffEventsAdapter.ViewHolder>{
    private FragmentActivity activity;


    public MyStuffEventsAdapter(FragmentActivity activity) {
        this.activity = activity;
    }
    @Override
    public MyStuffEventsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(activity).inflate(R.layout.past_events_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
