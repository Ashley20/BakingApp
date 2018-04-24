package com.example.sahip.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.models.Recipe;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipeList;
    private Context mContext;

    public static  class RecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeNameTv;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTv = itemView.findViewById(R.id.recipe_name_tv);
        }
    }

    public MasterListAdapter(Context context, List<Recipe> recipes){
        mContext = context;
        mRecipeList = recipes;
    }


    @Override
    public MasterListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.master_list_card_view, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MasterListAdapter.RecipeViewHolder holder, int position) {
        holder.recipeNameTv.setText(mRecipeList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        if(mRecipeList == null) return 0;
        return mRecipeList.size();
    }

}
