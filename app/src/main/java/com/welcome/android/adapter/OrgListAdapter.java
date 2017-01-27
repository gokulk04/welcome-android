package com.welcome.android.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.welcome.android.R;
import com.welcome.android.objects.Organization;

import java.util.List;

/**
 * Created by Gokul on 1/25/2017.
 */
public class OrgListAdapter extends RecyclerView.Adapter<OrgListAdapter.ViewHolder>{
    private FragmentActivity activity;
    private List<Organization> orgs;

    public OrgListAdapter(FragmentActivity activity, List<Organization> orgs) {
        this.activity = activity;
        this.orgs = orgs;
    }
    @Override
    public OrgListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.org_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Organization org = orgs.get(i);
        Picasso.with(activity).load(org.getLogoUrl()).into(viewHolder.imgProfile);
        viewHolder.orgName.setText(org.getTitle());
        int numAttended = 0; // TODO: figure out how to derive attended
        viewHolder.attendedText.setText("Attended | " + numAttended);
        int numMissed = 0; // TODO: figure out how to derive missed
        viewHolder.missedText.setText("Missed | " + numMissed);
        int numRemaining = 0; // TODO: figure out how to derive attended
        viewHolder.remainingText.setText("Remaining | " + numRemaining);
    }

    @Override
    public int getItemCount() {
        return orgs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProfile;
        public TextView orgName;
        public TextView attendedText;
        public TextView missedText;
        public TextView remainingText;
        public ViewHolder(View itemView) {
            super(itemView);
            imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            orgName = (TextView) itemView.findViewById(R.id.orgName);
            attendedText = (TextView) itemView.findViewById(R.id.attendedText);
            missedText = (TextView) itemView.findViewById(R.id.missedText);
            remainingText = (TextView) itemView.findViewById(R.id.remainingText);
        }
    }
}
