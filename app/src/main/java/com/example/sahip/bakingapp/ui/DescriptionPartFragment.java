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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DescriptionPartFragment extends Fragment {
    @BindView(R.id.description_part_text_view) TextView stepDescriptionTv;
    private String description;
    private Unbinder unbinder;

    public DescriptionPartFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_description_part, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        if(description != null){
            stepDescriptionTv.setText(description);
        }

        return rootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


