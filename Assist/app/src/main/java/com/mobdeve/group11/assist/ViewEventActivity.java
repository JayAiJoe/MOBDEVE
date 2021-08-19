package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ViewEventActivity extends AppCompatActivity {

    ChipGroup cgViewGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        cgViewGroups = findViewById(R.id.cg_view_groups);

        //get and display groups
        String[] groupsArray = getResources().getStringArray(R.array.sample_groups); //sample only
        displayGroups(groupsArray, cgViewGroups);


    }

    private void displayGroups(String[] groups , ChipGroup pChipGroup) {
        for(int i=0; i<groups.length; i++){
            Chip lChip = new Chip(this);
            lChip.setText(groups[i]);
            pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
        }
    }
}