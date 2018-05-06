package com.example.sahip.bakingapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sahip.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.sahip.bakingapp.ui.MasterListFragment;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MasterListFragment.DelayerCallback {
    public static final String TAG =  MainActivity.class.getSimpleName();
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());

        getIdlingResource();

        MasterListFragment masterListFragment = (MasterListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.master_list_fragment);
        masterListFragment.setmIdlingResource(mIdlingResource);
        masterListFragment.getRecipies(MainActivity.this, mIdlingResource);

    }




    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onDone() {

    }
}
