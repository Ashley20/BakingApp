package com.example.sahip.bakingapp;


import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.sahip.bakingapp.ui.DescriptionPartFragment;
import com.example.sahip.bakingapp.ui.VideoPartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity {

    public static final String STEP_DESCRIPTION = "step_description";
    public static final String STEP_VIDEO_URL = "step_video_url";

    private DescriptionPartFragment descriptionFragment;
    private VideoPartFragment videoFragment;
    @BindView(R.id.step_prev_btn) Button mPrevBtn;
    @BindView(R.id.step_next_btn) Button mNextBtn;

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

        // Get extras
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            descriptionFragment.setDescription(extras.getString(STEP_DESCRIPTION));
            videoFragment.setVideoUrl(extras.getString(STEP_VIDEO_URL));
        }


        FragmentManager fragmentManager = getSupportFragmentManager();

        // Fragment transaction
        fragmentManager.beginTransaction()
                .add(R.id.description_container, descriptionFragment)
                .add(R.id.video_container, videoFragment)
                .commit();

    }


    @Override
    protected void onPause() {
        super.onPause();
        videoFragment.releasePlayer();
    }
}
