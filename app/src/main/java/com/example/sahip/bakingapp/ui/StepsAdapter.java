package com.example.sahip.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.StepDetailActivity;
import com.example.sahip.bakingapp.models.Step;

import java.util.ArrayList;

public class StepsAdapter extends ArrayAdapter<Step>{
    public StepsAdapter(Context context, ArrayList<Step> steps) {
        super(context, 0, steps);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Step step = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_item, parent, false);
        }
        // Get reference to the step button
        Button stepBtn = (Button) convertView.findViewById(R.id.step_btn);

        // Populate the data into the template view using the data object
        stepBtn.setText(step != null ? step.getShortDescription() : null);

        // When button clicked navigate the user to step detail view
        stepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StepDetailActivity.class);
                Bundle bundle = new Bundle();

                if (step != null) {
                    bundle.putString(StepDetailActivity.STEP_DESCRIPTION, step.getDescription());
                    bundle.putString(StepDetailActivity.STEP_VIDEO_URL, step.getVideoURL());
                }

                intent.putExtras(bundle);
                getContext().startActivity(intent);

            }
        });

        return convertView;

    }
}
