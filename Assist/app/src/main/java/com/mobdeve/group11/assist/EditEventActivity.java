package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    private ImageView ivCancel, ivDone;
    private EditText etName, etTemplate, etRemind;
    private TextView tvDate, tvSTime, tvETime, tvHead, tvGroups;//for groups edit
    private Activity activity = EditEventActivity.this;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private LocalDate selectedDate;

    private TimePickerDialog.OnTimeSetListener startSetListener;
    private TimePickerDialog.OnTimeSetListener endSetListener;
    private LocalTime startTime;
    private LocalTime endTime;

    Button btnEditGroups;
    ChipGroup cgEditGroups;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        tvDate = findViewById(R.id.tv_add_event_date);
        tvSTime = findViewById(R.id.tv_add_event_start_time);
        tvETime = findViewById(R.id.tv_add_event_end_time);
        etTemplate = findViewById(R.id.et_add_event_template);
        etRemind = findViewById(R.id.et_add_event_reminder);

        setDateAndTimes();



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
        this.tvDate.setText(date);
        this.tvSTime.setText(sTime);
        this.tvETime.setText(eTime);
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
                String date = tvDate.getText().toString();
                String sTime = tvSTime.getText().toString();
                String eTime = tvETime.getText().toString();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDateAndTimes(){

        selectedDate = LocalDate.now(); // change to actual selected date
        tvDate.setText(selectedDate.getMonth() + " " + selectedDate.getDayOfMonth() + ", " + selectedDate.getYear());

        tvDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DatePickerDialog dpDialog = new DatePickerDialog(EditEventActivity.this,
                        android.R.style.Theme_Holo_Dialog, dateSetListener,
                        selectedDate.getYear(), selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth());
                dpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = LocalDate.of(year,month+1,dayOfMonth);
                tvDate.setText(selectedDate.getMonth() + " " + selectedDate.getDayOfMonth() + ", " + selectedDate.getYear());
            }
        };

        startTime = LocalTime.of(8, 0);
        endTime = LocalTime.of(10, 0);
        tvSTime.setText(String.format("%2d:%02d", startTime.getHour(), startTime.getMinute()));
        tvETime.setText(String.format("%2d:%02d", endTime.getHour(), endTime.getMinute()));

        tvSTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpDialog = new TimePickerDialog(EditEventActivity.this,
                        android.R.style.Theme_Holo_Dialog, startSetListener,
                        startTime.getHour(), startTime.getMinute(), true);
                tpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tpDialog.show();
            }
        });

        tvETime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpDialog = new TimePickerDialog(EditEventActivity.this,
                        android.R.style.Theme_Holo_Dialog, endSetListener,
                        endTime.getHour(), endTime.getMinute(), true);
                tpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tpDialog.show();
            }
        });

        startSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTime = LocalTime.of(hourOfDay,minute);
                tvSTime.setText(String.format("%2d:%02d", startTime.getHour(), startTime.getMinute()));
            }
        };

        endSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTime = LocalTime.of(hourOfDay,minute);
                tvETime.setText(String.format("%2d:%02d", endTime.getHour(), endTime.getMinute()));
            }
        };
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
