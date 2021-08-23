package com.mobdeve.group11.assist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    Button btnEditGroups;
    ChipGroup cgEditGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        cgEditGroups = findViewById(R.id.cg_temp_edit_group);
        btnEditGroups = findViewById(R.id.btn_temp_edit_groups);

        setButtons();


    }

    private void setButtons(){
        btnEditGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditEventActivity.this);

                //sample choices
                String[] groupsArray = getResources().getStringArray(R.array.sample_groups);
                boolean[] checkedGroups = {false, false};

                final List<String> groupList = Arrays.asList(groupsArray);
                final List<String> selectedGroups = new ArrayList<String>();

                builder.setTitle("Select groups");

                builder.setMultiChoiceItems(groupsArray, checkedGroups,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedGroups.add(groupList.get(which));
                                } else {
                                    selectedGroups.remove(groupList.get(which));
                                }
                            }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayGroups(selectedGroups.toArray(new String[selectedGroups.size()]), cgEditGroups);
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void displayGroups(String[] groups , ChipGroup pChipGroup) {
        pChipGroup.removeAllViewsInLayout();
        Arrays.sort(groups, Collections.reverseOrder());
        for(int i=0; i<groups.length; i++){
            Chip lChip = new Chip(this);
            lChip.setText(groups[i]);
            pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
        }
    }
}
