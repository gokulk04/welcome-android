package com.welcome.android.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.welcome.android.R;

/**
 * Created by Gokul on 1/25/2017.
 */
public class OrgListAdapter extends RecyclerView.Adapter<OrgListAdapter.ViewHolder>{
    private FragmentActivity activity;


    public OrgListAdapter(FragmentActivity activity) {
        this.activity = activity;
    }
    @Override
    public OrgListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(activity).inflate(R.layout.org_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
