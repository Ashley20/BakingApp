package com.example.sahip.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.sahip.bakingapp.models.Recipe;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter {
    private List<Recipe> mRecipeList;
    private Context mContext;

    public MasterListAdapter(){}

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
