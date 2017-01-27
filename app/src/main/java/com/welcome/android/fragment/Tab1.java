package com.welcome.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.welcome.android.R;
import com.welcome.android.adapter.EventListAdapter;
import com.welcome.android.objects.Event;
import com.welcome.android.objects.iBeacon;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab1 extends Fragment {

    private TextView txtScanning;
    private ImageButton imgCamera;
    private LinearLayout llScanning;

   public Tab1(){

   }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.tab_1, container, false);

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.INVISIBLE);
        txtScanning = (TextView) v.findViewById(R.id.txtScanning);
        llScanning = (LinearLayout) v.findViewById(R.id.llScanning);
        imgCamera = (ImageButton) v.findViewById(R.id.imgCamera);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
                //TODO: get QR code from image taken and retrieve "organizationId/eventId"
            }
        });

        final List<iBeacon> iBeacons = new ArrayList<>();
        iBeacon.getAll().continueWithTask(new Continuation<List<iBeacon>, Task<Event>>() {
            @Override
            public Task<Event> then(@NonNull Task<List<iBeacon>> task) throws Exception {
                iBeacons.addAll(task.getResult());
                // TODO: do some ble stuff to get closest event
                String eventId = "-KaMai8mOz_8jX1i9t1R";
                String orgId = "-KaMaOqNGGMXHrxhiNyJ";
                return Event.getById(orgId, eventId);
            }
        }).addOnSuccessListener(new OnSuccessListener<Event>() {
            @Override
            public void onSuccess(Event event) {
                List<Event> events = new ArrayList<Event>();
                events.add(event);

                // TODO: not sure if this would work?
                EventListAdapter rvAdapter = new EventListAdapter(getActivity(), events);
                recyclerView.setAdapter(rvAdapter);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtScanning.setVisibility(View.INVISIBLE);
                        llScanning.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        imgCamera.setVisibility(View.INVISIBLE);

                    }
                });
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);

        return v;
    }

}