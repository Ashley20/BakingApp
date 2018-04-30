package com.example.sahip.bakingapp;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.database.TinyDB;
import com.example.sahip.bakingapp.ui.DescriptionPartFragment;
import com.example.sahip.bakingapp.ui.RecipeDetailFragment;
import com.example.sahip.bakingapp.ui.VideoPartFragment;

import java.util.Objects;

import timber.log.Timber;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String TAG = RecipeDetailActivity.class.getSimpleName();
    public static final String RECIPE_POSITION = "position" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Timber.plant(new Timber.DebugTree());

        Timber.d("RecipeDetailActivity: onCreate called");

        // Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get extras from the calling intent and send it to the relative fragment
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            if(findViewById(R.id.two_pane_layout) != null){
                bundle.putBoolean(RecipeDetailFragment.IS_TWO_PANE, true);
            }else {
                bundle.putBoolean(RecipeDetailFragment.IS_TWO_PANE, false);
            }
            // Get reference to the relative fragment
            RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.recipe_detail_fragment);
            // Set intent extras as arguments
            recipeDetailFragment.setArguments(bundle);

        }
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
