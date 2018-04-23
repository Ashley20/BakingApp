package com.example.sahip.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sahip.bakingapp.MainActivity;
import com.example.sahip.bakingapp.R;


public class MasterListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the fragment_master_list xml layout file
        RecyclerView recyclerView = rootView.findViewById(R.id.recipes_recycler_view);

        // Set a LinearLayoutManager on the recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the recipes to display
        MasterListAdapter masterListAdapter = new MasterListAdapter();

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(masterListAdapter);

        return rootView;
    }
}
