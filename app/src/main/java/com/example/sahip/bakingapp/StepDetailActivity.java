package com.example.sahip.bakingapp;


import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.sahip.bakingapp.database.TinyDB;
import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.ui.DescriptionPartFragment;
import com.example.sahip.bakingapp.ui.VideoPartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity {


    public static final String RECIPE = "recipe";
    public static final String POSITION = "step_position";
    private Recipe recipe;

    private DescriptionPartFragment descriptionFragment;
    private VideoPartFragment videoFragment;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);


        // Set up timber
        Timber.plant(new Timber.DebugTree());

        // Set up butterknife
        ButterKnife.bind(this);

        descriptionFragment = new DescriptionPartFragment();
        videoFragment = new VideoPartFragment();

        // Get recipe object
        TinyDB tiny = new TinyDB(this);
        recipe = tiny.getObject(RECIPE, Recipe.class);

        // Get extras
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            position = extras.getInt(POSITION);

            descriptionFragment.setmStepList(recipe.getSteps());
            descriptionFragment.setIndex(position);

            videoFragment.setmStepList(recipe.getSteps());
            videoFragment.setIndex(position);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();

        // Fragment transaction
        fragmentManager.beginTransaction()
                .add(R.id.description_container, descriptionFragment)
                .add(R.id.video_container, videoFragment)
                .commit();

    }



    @OnClick(R.id.step_next_btn)
    public void nextButtonClick(){
        Timber.d("Next button click");
        descriptionFragment.goNextStep();
        videoFragment.goNextStep();
    }

    @OnClick(R.id.step_prev_btn)
    public void prevButtonClick(){
        Timber.d("Prev button click");
        descriptionFragment.goPreviousStep();
        videoFragment.goPreviousStep();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoFragment.releasePlayer();
    }



}
