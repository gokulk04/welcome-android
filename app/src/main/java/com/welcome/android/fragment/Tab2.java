package com.welcome.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.welcome.android.R;
import com.welcome.android.adapter.MyStuffEventsAdapter;
import com.welcome.android.adapter.OrgListAdapter;

public class Tab2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2, container, false);

        //GET USER PROFILE INFO FROM FIREBASE

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerOrgs);
        final RecyclerView recyclerViewEvents = (RecyclerView) v.findViewById(R.id.recyclerEvents);
        recyclerViewEvents.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);



        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        OrgListAdapter orgAdapter = new OrgListAdapter (getActivity());
        recyclerView.setAdapter(orgAdapter);

        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm2);
        MyStuffEventsAdapter mseAdapter = new MyStuffEventsAdapter(getActivity());
        recyclerViewEvents.setAdapter(mseAdapter);




        return v;
    }
}