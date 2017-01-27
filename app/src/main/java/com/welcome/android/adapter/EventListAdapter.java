package com.welcome.android.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.welcome.android.R;

/**
 * Created by Gokul on 1/25/2017.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{
    private FragmentActivity activity;



    public EventListAdapter(FragmentActivity activity) {
        this.activity = activity;
    }
    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(activity).inflate(R.layout.event_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
       // Picasso.with(activity).load(R.drawable.p1).into(viewHolder.imageView);
       /* viewHolder.txtViewTitle.setText(this.adapterStringList.get(i));
        viewHolder.imageView.setImageResource(this.adapterImageList.get(i));
*/

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            //imageView = (ImageView) itemView.findViewById(R.id.imgEvent);
            //    txtViewTitle = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}