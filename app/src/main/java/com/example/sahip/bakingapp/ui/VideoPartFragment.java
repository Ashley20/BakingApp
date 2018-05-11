package com.example.sahip.bakingapp.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.StepDetailActivity;
import com.example.sahip.bakingapp.database.TinyDB;
import com.example.sahip.bakingapp.models.Recipe;
import com.example.sahip.bakingapp.models.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.example.sahip.bakingapp.StepDetailActivity.POSITION;

public class VideoPartFragment extends Fragment implements ExoPlayer.EventListener{
    public static final String INDEX = "index";
    public static final String POSITION = "video_position";

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    List<Step> mStepList;
    private int index;
    private SimpleExoPlayer mExoplayer;
    private Unbinder unbinder;
    private long position;

    // Mandatory constructor
    public VideoPartFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_part, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        position = C.TIME_UNSET;

        if(savedInstanceState != null){
            index = savedInstanceState.getInt(INDEX);
            position = savedInstanceState.getLong(POSITION, C.TIME_UNSET);
            // Get recipe object
            TinyDB tiny = new TinyDB(getContext());
            Recipe recipe = tiny.getObject(StepDetailActivity.RECIPE, Recipe.class);
            mStepList = recipe.getSteps();
        }


        if(mStepList != null){
            String url = mStepList.get(index).getVideoURL();
            initializePlayer(Uri.parse(url));
        }

        mExoplayer.addListener(this);


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(INDEX, index);
        outState.putLong(POSITION, position);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoplayer != null) {
            position = mExoplayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void initializePlayer(Uri videoUri){
        if(videoUri.toString().equals("")){
            Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.novideo);
            mPlayerView.setDefaultArtwork(icon);
            mPlayerView.setUseController(false);
        } else {
            mPlayerView.setUseController(true);
        }

        if(mExoplayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoplayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoplayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");

            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(),
                    null, null);

            if (position != C.TIME_UNSET){
                mExoplayer.seekTo(position);
            }

            mExoplayer.prepare(mediaSource);
            mExoplayer.setPlayWhenReady(true);
        }

    }

    public void releasePlayer() {
        mExoplayer.stop();
        mExoplayer.release();
        mExoplayer = null;
    }

    public void setmStepList(List<Step> mStepList) {
        this.mStepList = mStepList;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public void goNextStep(){
        if(index < mStepList.size() - 1){
            index++;
        }else{
            index = 0;
        }

        if(mExoplayer != null) {
            releasePlayer();
        }

        String url = mStepList.get(index).getVideoURL();
        position = C.TIME_UNSET;
        initializePlayer(Uri.parse(url));
    }

    public void goPreviousStep(){
        if(index <= mStepList.size() - 1 && index > 0){
            index--;
        }else{
            index = 0;
        }

        if(mExoplayer != null){
            releasePlayer();
        }

        String url = mStepList.get(index).getVideoURL();
        position = C.TIME_UNSET;
        initializePlayer(Uri.parse(url));
    }

    public SimpleExoPlayer getmExoplayer() {
        return mExoplayer;
    }

    public void setmExoplayer(SimpleExoPlayer mExoplayer) {
        this.mExoplayer = mExoplayer;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        switch (error.type) {
            case ExoPlaybackException.TYPE_SOURCE:
                Timber.e("TYPE_SOURCE: " + error.getSourceException().getMessage());
                mExoplayer.release();
                break;

            case ExoPlaybackException.TYPE_RENDERER:
                Timber.e("TYPE_RENDERER: " + error.getRendererException().getMessage());
                break;

            case ExoPlaybackException.TYPE_UNEXPECTED:
                Timber.e("TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
                break;
        }

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
