package com.example.sahip.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.StepDetailActivity;

public class DescriptionPartFragment extends Fragment {
    private TextView stepDescriptionTv;
    private String description;
    public DescriptionPartFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_description_part, container, false);

        stepDescriptionTv = rootView.findViewById(R.id.description_part_text_view);

        if(description != null){
            stepDescriptionTv.setText(description);
        }

        return rootView;

    }

    public void setDescription(String description) {
        this.description = description;
    }
}
