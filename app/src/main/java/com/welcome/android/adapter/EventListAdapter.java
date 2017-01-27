package com.welcome.android.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;
import com.welcome.android.R;
import com.welcome.android.objects.Event;
import com.welcome.android.objects.Organization;

import java.util.List;

/**
 * Created by Gokul on 1/25/2017.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{
    private FragmentActivity activity;
    private List<Event> events;

    public EventListAdapter(FragmentActivity activity, List<Event> events) {
        this.activity = activity;
        this.events = events;
    }

    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.event_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Event event = events.get(i);
        String picUrl = event.getEventPicUrl();
        Picasso.with(activity).load(picUrl).into(viewHolder.imageView);
        viewHolder.eventDetails.setText(event.getDescription());
        Organization.getById(event.getOrganizationId()).addOnSuccessListener(new OnSuccessListener<Organization>() {
            @Override
            public void onSuccess(Organization organization) {
                // TODO: will this work ???
                viewHolder.eventOrgName.setText(organization.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView eventOrgName;
        public TextView eventDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgEvent);
            eventOrgName = (TextView) itemView.findViewById(R.id.eventOrgName);
            eventDetails = (TextView) itemView.findViewById(R.id.eventDetails);
        }
    }
}
