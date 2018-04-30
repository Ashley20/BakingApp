package com.example.sahip.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.RecipeDetailActivity;
import com.example.sahip.bakingapp.StepDetailActivity;
import com.example.sahip.bakingapp.models.Step;

import java.util.ArrayList;

public class StepsAdapter extends ArrayAdapter<Step>{
    private static final int BTN_CLICK = 0;
    private boolean isTwoPane;
    private ArrayList<Step> mStepList;
    private Context mContext;
    private FragmentManager fragmentManager;

    public StepsAdapter(Context context, ArrayList<Step> steps, boolean isTwoPane, FragmentManager fragmentManager) {
        super(context, 0, steps);
        this.mContext = context;
        this.isTwoPane = isTwoPane;
        this.mStepList = steps;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        final Step step = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_item, parent, false);
        }
        // Get reference to the step button
        final Button stepBtn = (Button) convertView.findViewById(R.id.step_btn);

        // Populate the data into the template view using the data object
        stepBtn.setText(step != null ? step.getShortDescription() : null);

        final View finalConvertView = convertView;
        stepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTwoPane){
                    final DescriptionPartFragment descriptionFragment = new DescriptionPartFragment();
                    VideoPartFragment videoFragment = new VideoPartFragment();

                    descriptionFragment.setmStepList(mStepList);
                    descriptionFragment.setIndex(position);

                    videoFragment.setmStepList(mStepList);
                    videoFragment.setIndex(position);

                    fragmentManager.beginTransaction()
                            .replace(R.id.description_container, descriptionFragment)
                            .replace(R.id.video_container, videoFragment)
                            .commit();

                    for (int i = 0; i < parent.getChildCount(); i++) {
                        if(position == i ){
                            parent.getChildAt(i).findViewById(R.id.step_btn)
                                    .setBackgroundColor(Color.CYAN);
                        }else{
                            parent.getChildAt(i).findViewById(R.id.step_btn)
                                    .setBackgroundColor(Color.TRANSPARENT);
                        }
                    }

                }else {

                    Intent intent = new Intent(getContext(), StepDetailActivity.class);
                    Bundle bundle = new Bundle();

                    if (step != null) {
                        bundle.putInt(StepDetailActivity.POSITION, position);
                    }

                    intent.putExtras(bundle);
                    getContext().startActivity(intent);

                }



            }
        });


        return convertView;

    }
}
