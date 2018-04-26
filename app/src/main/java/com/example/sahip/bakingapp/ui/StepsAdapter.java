package com.example.sahip.bakingapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.sahip.bakingapp.R;
import com.example.sahip.bakingapp.models.Step;

import java.util.ArrayList;

public class StepsAdapter extends ArrayAdapter<Step>{
    public StepsAdapter(Context context, ArrayList<Step> steps) {
        super(context, 0, steps);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Step step = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_item, parent, false);
        }
        // Get reference to the step button
        Button stepName = (Button) convertView.findViewById(R.id.step_btn);

        // Populate the data into the template view using the data object
        stepName.setText(step != null ? step.getShortDescription() : null);

        return convertView;

    }
}
