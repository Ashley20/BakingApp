package com.example.sahip.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoPartFragment extends Fragment {
    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    List<Step> mStepList;
    private int index;
    private SimpleExoPlayer mExoplayer;
    private Unbinder unbinder;

    // Mandatory constructor
    public VideoPartFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_part, container, false);

        unbinder = ButterKnife.bind(this, rootView);

       // mPlayerView.requestFocus();

        if(mStepList != null){
            String url = mStepList.get(index).getVideoURL();
            initializePlayer(Uri.parse(url));
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initializePlayer(Uri videoUri){
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

        releasePlayer();
        String url = mStepList.get(index).getVideoURL();
        initializePlayer(Uri.parse(url));
    }

    public void goPreviousStep(){
        if(index <= mStepList.size() - 1 && index > 0){
            index--;
        }else{
            index = 0;
        }

        releasePlayer();
        String url = mStepList.get(index).getVideoURL();
        initializePlayer(Uri.parse(url));
    }
}
