package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;
import com.mobdeve.group11.assist.database.Event;
import com.mobdeve.group11.assist.database.EventGrouping;
import com.mobdeve.group11.assist.database.GroupMembership;
import com.mobdeve.group11.assist.database.Template;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    private ImageView ivCancel, ivDone;
    private EditText etName;
    private TextView tvDate, tvSTime, tvETime, tvHead, tvAddGroups, tvTemplate, tvReminder;
    private Activity activity = EditEventActivity.this;
    private ListView lvGroups;

    private AssistViewModel viewModel;
    private Event event;
    private List<ContactGroup> groupList;
    private boolean[] checkedGroups = new boolean[0];
    private final List<ContactGroup> selectedGroups = new ArrayList<ContactGroup>();
    private ArrayAdapter<String> adapter;


    private DatePickerDialog.OnDateSetListener dateSetListener;
    private LocalDate selectedDate;

    private TimePickerDialog.OnTimeSetListener startSetListener;
    private TimePickerDialog.OnTimeSetListener endSetListener;
    private LocalTime startTime;
    private LocalTime endTime;

    private List<Template> templateList;
    private int checkedTemplate = -1;
    private int templateIndex = -1;

    private int reminderIndex = 0;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        etName = findViewById(R.id.et_edit_event_title);
        tvDate = findViewById(R.id.et_edit_event_date);
        tvSTime = findViewById(R.id.et_edit_event_start_time);
        tvETime = findViewById(R.id.et_edit_event_end_time);
        tvTemplate = findViewById(R.id.tv_edit_event_template);
        tvReminder = findViewById(R.id.tv_edit_event_reminder);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        initInfo();
        setButtons();

        tvHead = findViewById(R.id.tv_toolbar_edit_title);
        tvHead.setText("Edit Event");


    }

    private void setButtons(){
        tvAddGroups = findViewById(R.id.tv_add_groups);
        tvAddGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(EditEventActivity.this);

                builder.setTitle("Select groups");

                builder.setMultiChoiceItems(AppUtils.getGroupNames(groupList), checkedGroups,
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
                        adapter.clear();
                        String[] names = AppUtils.getGroupNames(selectedGroups);
                        Arrays.sort(names);
                        adapter.addAll(names);
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something
                    }
                });
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        ivDone = findViewById(R.id.iv_toolbar_edit_right);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivDone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (etName.getText().toString().length() > 0 && checkedTemplate != -1 && reminderIndex != -1){

                    event.setTitle(etName.getText().toString().trim());
                    event.setDate(selectedDate);
                    event.setTimeStart(startTime);
                    event.setTimeEnd(endTime);
                    event.setTemplateId(checkedTemplate);
                    event.setReminder(reminderIndex);

                    viewModel.deleteAllGroupingsInEvent(event.getId());

                    ArrayList<Integer> gIds = AppUtils.getGroupIds(selectedGroups);
                    for(int i=0; i< gIds.size(); i++){
                        viewModel.addGrouping(new EventGrouping(gIds.get(i), event.getId()));
                    }

                    viewModel.updateEvent(event);

                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Missing fields",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

        tvTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditEventActivity.this);
                alertDialog.setTitle("Select a template");

                final String[] listItems = AppUtils.getTemplateTitles(templateList);

                alertDialog.setSingleChoiceItems(listItems, templateIndex, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        templateIndex = which;
                        checkedTemplate = templateList.get(which).getId();
                        tvTemplate.setText(listItems[which]);
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                AlertDialog customAlertDialog = alertDialog.create();
                customAlertDialog.show();
            }
        });

        tvReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditEventActivity.this);
                alertDialog.setTitle("Select announcement time");


                alertDialog.setSingleChoiceItems(AppUtils.getReminderChoices(), reminderIndex, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reminderIndex = which;
                        tvReminder.setText(AppUtils.getReminderText(which));
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                AlertDialog customAlertDialog = alertDialog.create();
                customAlertDialog.show();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDateAndTimes(){

        selectedDate = event.getDate();
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

        startTime = event.getTimeStart();
        endTime = event.getTimeEnd();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initInfo(){

        Integer id = getIntent().getIntExtra(EventInfo.ID.name(), 1);
        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, new ArrayList<String>(Arrays.asList(AppUtils.getGroupNames(selectedGroups))));
        lvGroups = findViewById(R.id.lv_edit_event);
        lvGroups.setAdapter(adapter);

        viewModel.getEventById(id).observe(this, event->{
            this.event = event;
            if(event != null){
                etName.setText(event.getTitle());
                tvDate.setText(event.getDate().toString());
                tvSTime.setText(event.getTimeStart().toString());
                tvETime.setText(event.getTimeEnd().toString());
                tvTemplate.setText("None");

                viewModel.getTemplateById(event.getTemplateId()).observe(EditEventActivity.this, template -> {
                    if(template != null){
                        tvTemplate.setText(template.getTitle());
                        checkedTemplate = event.getTemplateId();
                    }

                });

                tvReminder.setText(AppUtils.getReminderText(event.getReminder()));

                setDateAndTimes();
            }
        });

        viewModel.getAllGroups().observe(this, groups -> {
            groupList = groups;
            checkedGroups = new boolean[groupList.size()];

            viewModel.getGroupIdsInEvent(id).observe(this, groupsInEvent->{
                for(int i=0; i<groupList.size();i++){
                    if(groupsInEvent.contains(groupList.get(i).getId())) {
                        checkedGroups[i] = true;
                        selectedGroups.add(groupList.get(i));
                    }
                }
                adapter.clear();
                String[] names = AppUtils.getGroupNames(selectedGroups);
                Arrays.sort(names);
                adapter.addAll(names);
            });
        });

        viewModel.getAllTemplates().observe(this, templates -> {
            templateList = templates;
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAlarm() {
        int num = (int)System.currentTimeMillis();
        LocalDate date = event.getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth(), startTime.getHour(), startTime.getMinute(), startTime.getSecond());

        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent =  PendingIntent.getBroadcast(this.getApplicationContext(), num,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pIntent);
    }

}
