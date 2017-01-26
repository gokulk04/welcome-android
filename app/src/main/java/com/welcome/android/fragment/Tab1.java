package com.welcome.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welcome.android.R;
import com.welcome.android.adapter.EventListAdapter;


/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab1 extends Fragment {

    private TextView txtScanning;
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

        //GET FILTERED EVENT OBJECTS FOR NEARBY EVENTS FROM FIREBASE


        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.INVISIBLE);
        txtScanning = (TextView) v.findViewById(R.id.txtScanning);
        llScanning = (LinearLayout) v.findViewById(R.id.llScanning);

        //CURRENTLY A DUMMY PROGRESS GUI FOR SCANNING NEARBY EVENTS. REPLACE TIMER WITH CALLBACK FOR WHEN FIREBASE HAS FOUND NEARBY EVENTS

       new Thread(){
            @Override
            public void run(){
                try{
                    synchronized (this){
                        wait(3000);

                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                txtScanning.setVisibility(View.INVISIBLE);
                                llScanning.setVisibility(View.INVISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        EventListAdapter rvAdapter = new EventListAdapter(getActivity());
        recyclerView.setAdapter(rvAdapter);




        return v;
    }

}