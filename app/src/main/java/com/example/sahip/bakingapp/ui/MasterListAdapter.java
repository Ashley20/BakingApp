package com.example.sahip.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.RecipeDetailActivity;
import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.database.TinyDB;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipeList;
    private Context mContext;

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeNameTv;
        private CardView recipeCardView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            recipeCardView = itemView.findViewById(R.id.recipe_card_view);
            recipeNameTv = itemView.findViewById(R.id.recipe_name_tv);
        }

    }

    public MasterListAdapter(Context context, List<Recipe> recipes){
        mContext = context;
        mRecipeList = recipes;
    }


    @Override
    public MasterListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.master_list_card_view, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MasterListAdapter.RecipeViewHolder holder, final int position) {
        holder.recipeNameTv.setText(mRecipeList.get(position).getName());

        holder.recipeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                // Put selected recipe's position as an extra in the intent
                intent.putExtra(RecipeDetailActivity.RECIPE_POSITION, position);
                // Start intent
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mRecipeList == null) return 0;
        return mRecipeList.size();
    }

}
