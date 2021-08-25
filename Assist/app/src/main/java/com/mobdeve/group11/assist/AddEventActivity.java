package com.mobdeve.group11.assist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private ImageView ivCancel, ivDone;
    private EditText etName, etDate, etSTime, etETime, etTemplate, etRemind;
    private TextView tvHead, tvGroups;//for groups edit
    private Activity activity = AddEventActivity.this;

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

        ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        ivDone = findViewById(R.id.iv_toolbar_edit_right);

        etName = findViewById(R.id.et_add_event_name);
        etDate = findViewById(R.id.et_add_event_date);
        etSTime = findViewById(R.id.et_add_event_start_time);
        etETime = findViewById(R.id.et_add_event_end_time);
        etTemplate = findViewById(R.id.et_add_event_template);
        etRemind = findViewById(R.id.et_add_event_reminder);
        //group

        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);
        this.tvHead.setText("Add Event");

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String date = etDate.getText().toString();
                String sTime = etSTime.getText().toString();
                String eTime = etETime.getText().toString();
                String template = etTemplate.getText().toString();
                String remind = etRemind.getText().toString();
                //group

                if (name.length() > 0 && date.length() > 0 && sTime.length() > 0 && eTime.length() > 0 && template.length() > 0 && remind.length() > 0){

                    Intent intent = new Intent(view.getContext(),ViewEventActivity.class);
                    intent.putExtra(EventInfo.NAME.name(), name);
                    intent.putExtra(EventInfo.DATE.name(), date);
                    intent.putExtra(EventInfo.START_TIME.name(), sTime);
                    intent.putExtra(EventInfo.END_TIME.name(), eTime);
                    intent.putExtra(EventInfo.TEMPLATE.name(), template);
                    intent.putExtra(EventInfo.REMINDER.name(), remind);

                    activity.startActivityForResult(intent, 1);
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(),
                            "You have not yet entered in all of the required fields!",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
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
