package com.mobdeve.group11.assist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.ContactGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    //**** add viewModel
    private AssistViewModel viewModel;

    Button btnAddGroups;
    ChipGroup cgAddGroups;

    List<ContactGroup> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        cgAddGroups = findViewById(R.id.cg_temp_add_group);
        btnAddGroups = findViewById(R.id.btn_temp_add_group);

        setButtons();

        //**** define the viewModel
        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        //****add observers to always get updated data
        viewModel.getAllGroups().observe(this, groups -> {
            groupList = groups; //groups are updated
        });

    }

    private void setButtons(){
        btnAddGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);

                //sample choices
                boolean[] checkedGroups = {false, false, false, false}; //remove hard code later
                final List<ContactGroup> selectedGroups = new ArrayList<ContactGroup>();

                builder.setTitle("Select groups");

                builder.setMultiChoiceItems(getNames(groupList), checkedGroups,
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
                        displayGroups(getNames(selectedGroups), cgAddGroups);
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

    //****helper function to get the string names only
    private String[] getNames(List<ContactGroup> gList){
        String[] strArray = new String[gList.size()];
        for(int i=0; i<gList.size();i++)
        {
            strArray[i] = gList.get(i).getName();
        }
        return strArray;
    }
}
