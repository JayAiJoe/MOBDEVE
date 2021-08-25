package com.mobdeve.group11.assist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    private ImageView ivCancel, ivDone;
    private EditText etName, etDate, etSTime, etETime, etTemplate, etRemind;
    private TextView tvHead, tvGroups;//for groups edit
    private Activity activity = EditEventActivity.this;

    Button btnEditGroups;
    ChipGroup cgEditGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        cgEditGroups = findViewById(R.id.cg_temp_edit_group);
        btnEditGroups = findViewById(R.id.btn_temp_edit_groups);

        setButtons();

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

        Intent intent = getIntent();

        //photo
        String name = intent.getStringExtra(EventInfo.NAME.name());
        String date = intent.getStringExtra(EventInfo.DATE.name());
        String sTime = intent.getStringExtra(EventInfo.START_TIME.name());
        String eTime = intent.getStringExtra(EventInfo.END_TIME.name());
        String template = intent.getStringExtra(EventInfo.TEMPLATE.name());
        String remind = intent.getStringExtra(EventInfo.REMINDER.name());
        //groups

        this.etName.setText(name);
        this.etDate.setText(date);
        this.etSTime.setText(sTime);
        this.etETime.setText(eTime);
        this.etTemplate.setText(template);
        this.etRemind.setText(remind);
        //groups

        this.tvHead.setText("Edit Event");

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

                    Intent intent = new Intent(view.getContext(),CalendarDayActivity.class);
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
