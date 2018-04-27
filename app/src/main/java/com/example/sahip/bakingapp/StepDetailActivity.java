package com.example.sahip.bakingapp;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sahip.bakingapp.ui.DescriptionPartFragment;

import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity {

    public static final String STEP_DESCRIPTION = "step_description";
    public static final String STEP_VIDEO_URL = "step_video_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        // Set up timber
        Timber.plant(new Timber.DebugTree());

        DescriptionPartFragment descriptionFragment = new DescriptionPartFragment();

        // Get extras
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            descriptionFragment.setDescription(extras.getString(STEP_DESCRIPTION));
        }


        FragmentManager fragmentManager = getSupportFragmentManager();

        // Fragment transaction
        fragmentManager.beginTransaction()
                .add(R.id.description_container, descriptionFragment)
                .commit();

    }
}
