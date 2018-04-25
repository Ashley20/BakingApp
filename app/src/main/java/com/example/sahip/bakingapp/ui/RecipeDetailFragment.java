package com.example.sahip.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.database.TinyDB;
import com.example.sahip.bakingapp.models.Recipe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailFragment extends Fragment {
    public static final String TAG = RecipeDetailFragment.class.getSimpleName();
    public static final String RECIPES_KEY = "recipes";
    public static final String RECIPE_POSITION = "position";

    private TextView recipeNameTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false );

        recipeNameTv = rootView.findViewById(R.id.ingredients_tv);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if(args != null){
            int position = args.getInt(RECIPE_POSITION, 0);

            TinyDB tiny = new TinyDB(getContext());

            ArrayList<Object> recipeObjects = tiny.getListObject(RECIPES_KEY, Recipe.class);
            ArrayList<Recipe> mRecipeList = new ArrayList<Recipe>();

            for(Object objs : recipeObjects){
                mRecipeList.add((Recipe) objs);
            }

            recipeNameTv.setText(mRecipeList.get(position).getName());
        }

    }
}
