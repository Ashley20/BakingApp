package com.example.sahip.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.database.TinyDB;
import com.example.sahip.bakingapp.ui.RecipeDetailFragment;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String TAG = RecipeDetailActivity.class.getSimpleName();
    public static final String RECIPE_POSITION = "position" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get extras from the calling intent and send it to the relative fragment
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            // Get reference to the relative fragment
            RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.recipe_detail_fragment);
            // Set intent extras as arguments
            recipeDetailFragment.setArguments(bundle);

        }
        
    }
}
