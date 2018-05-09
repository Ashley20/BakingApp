package com.example.sahip.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.StepDetailActivity;
import com.example.sahip.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DescriptionPartFragment extends Fragment {
    @BindView(R.id.description_part_text_view) TextView stepDescriptionTv;
    private List<Step> mStepList;
    private int index;
    private Unbinder unbinder;

    public DescriptionPartFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_description_part, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        if(mStepList != null){
            stepDescriptionTv.setText(mStepList.get(index).getDescription());
            // Set action bar title as the step number
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Step " + mStepList.get(index).getId());
        }



        return rootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        stepDescriptionTv.setText(mStepList.get(index).getDescription());
        // Change action bar title as the step number
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Step " + mStepList.get(index).getId());
    }

    public void goPreviousStep(){
        if(index <= mStepList.size() - 1 && index > 0){
            index--;
        }else{
            index = 0;
        }

        stepDescriptionTv.setText(mStepList.get(index).getDescription());
        // Set action bar title as the step number
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Step " + mStepList.get(index).getId());
    }
}


