package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    //**** add viewModel
    private AssistViewModel viewModel;

    private ImageView ivCancel, ivDone;
    private EditText etName;
    private TextView tvHead, tvSTime, tvETime, tvGroups, tvDate, tvTemplate, tvReminder;
    private Activity activity = AddEventActivity.this;
    private ListView lvGroups;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private LocalDate selectedDate;

    private TimePickerDialog.OnTimeSetListener startSetListener;
    private TimePickerDialog.OnTimeSetListener endSetListener;
    private LocalTime startTime;
    private LocalTime endTime;

    private final List<ContactGroup> selectedGroups = new ArrayList<ContactGroup>();
    private List<ContactGroup> groupList;
    private boolean[] checkedGroups = new boolean[0];
    private ArrayAdapter<String> adapter;

    private List<Template> templateList;
    private int checkedTemplate = -1;
    private int templateIndex = -1;

    private int reminderIndex = 0; //default

    private String message = "";
    private Event event;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        viewModel.getAllGroups().observe(this, groups -> {
            groupList = groups;
            checkedGroups = new boolean[groupList.size()];
        });

        viewModel.getAllTemplates().observe(this, templates -> {
            templateList = templates;
        });

        initComponents();
        setButtons();

        setDateAndTimes();

    }

    public void onResume() {
        super.onResume();
    }

    private void initComponents(){
        ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        ivDone = findViewById(R.id.iv_toolbar_edit_right);
        tvGroups = findViewById(R.id.tv_add_event_groups);

        etName = findViewById(R.id.et_add_event_name);
        tvDate = findViewById(R.id.tv_add_event_date);
        tvSTime = findViewById(R.id.tv_add_event_start_time);
        tvETime = findViewById(R.id.tv_add_event_end_time);
        tvTemplate = findViewById(R.id.tv_add_event_template);
        tvReminder = findViewById(R.id.tv_add_event_reminder);

        tvReminder.setText(AppUtils.getReminderText(reminderIndex));

        tvHead = findViewById(R.id.tv_toolbar_edit_title);
        tvHead.setText("Add Event");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDateAndTimes(){

        selectedDate = CalendarUtils.selectedDate;// change to actual selected date
        tvDate.setText(selectedDate.getMonth() + " " + selectedDate.getDayOfMonth() + ", " + selectedDate.getYear());

        tvDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DatePickerDialog dpDialog = new DatePickerDialog(AddEventActivity.this,
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
                TimePickerDialog tpDialog = new TimePickerDialog(AddEventActivity.this,
                        android.R.style.Theme_Holo_Dialog, startSetListener,
                        startTime.getHour(), startTime.getMinute(), true);
                tpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tpDialog.show();
            }
        });

        tvETime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpDialog = new TimePickerDialog(AddEventActivity.this,
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
                if(startTime.isAfter(endTime))
                {
                    endTime = LocalTime.of(hourOfDay,minute);
                    tvETime.setText(String.format("%2d:%02d", endTime.getHour(), endTime.getMinute()));
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Invalid end time adjusted",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
        };

        endSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTime = LocalTime.of(hourOfDay,minute);
                tvETime.setText(String.format("%2d:%02d", endTime.getHour(), endTime.getMinute()));
                if(endTime.isBefore(startTime))
                {
                    startTime = LocalTime.of(hourOfDay,minute);
                    tvSTime.setText(String.format("%2d:%02d", startTime.getHour(), startTime.getMinute()));
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Invalid start time adjusted",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setButtons(){

        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, new ArrayList<String>(Arrays.asList(AppUtils.getGroupNames(selectedGroups))));
        lvGroups = findViewById(R.id.lv_add_event);
        lvGroups.setAdapter(adapter);


        tvGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);


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

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        this.ivDone.setOnClickListener(view -> {
            Intent intent = new Intent();

            String name = etName.getText().toString().trim();

            if (name.length() > 0  && checkedTemplate != -1 && reminderIndex != -1){

                Event e = new Event(name, selectedDate, startTime, endTime, checkedTemplate, reminderIndex);
                Integer eId = (int) viewModel.addEventGetId(e);

                ArrayList<Integer> gIds = AppUtils.getGroupIds(selectedGroups);

                for(int i = 0; i<selectedGroups.size(); i++){
                    viewModel.addGrouping(new EventGrouping(gIds.get(i), eId));
                }

                String.valueOf(viewModel.getTemplateById(checkedTemplate));
                viewModel.getTemplateById(checkedTemplate).observe(AddEventActivity.this, template -> {
                    message = template.getSubject() + "\n\n" + template.getContent();
                });

                if (ContextCompat.checkSelfPermission(AddEventActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddEventActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                }
                else{
                    //set alarm for message
                    for (int i = 0; i < selectedGroups.size(); i++) {
                        int a = i*1000;
                        viewModel.getContactIdsInGroup(selectedGroups.get(i).getId()).observe(AddEventActivity.this, contacts -> {
                            for (int j = 0; j < contacts.size(); j++) {
                                int aid = j+a;
                                viewModel.getContactById(contacts.get(j)).observe(AddEventActivity.this, contact -> {
                                    setAlarm(contact.getContactNumber(), message, eId*100+aid);
                                });
                            }
                        });
                    }
                }

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            else {
                Toast t = Toast.makeText(getApplicationContext(),
                        "You have not yet entered in all of the required fields!",
                        Toast.LENGTH_LONG);
                t.show();
            }

        });

        tvTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddEventActivity.this);
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddEventActivity.this);
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
    public void setAlarm(String number, String message, int id) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("Number", number);
        bundle.putCharSequence("Message", message);

        LocalDate date = selectedDate;
        //int num = (int)(System.currentTimeMillis() % 1000);

        LocalTime remTime = startTime;
        if (reminderIndex == 1) {
            remTime = remTime.minusMinutes(10);
        }
        else if (reminderIndex == 2) {
            remTime = remTime.minusMinutes(30);
        }
        else if (reminderIndex == 3) {
            remTime = remTime.minusHours(1);
        }
        else if (reminderIndex == 4) {
            remTime = remTime.minusHours(3);
        }
        else if (reminderIndex == 5) {
            date = date.minusDays(1);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth(), remTime.getHour(), remTime.getMinute(), remTime.getSecond());

        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtras(bundle);
        PendingIntent pIntent =  PendingIntent.getBroadcast(this.getApplicationContext(), id, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pIntent);
        Toast.makeText(getApplication(), "Alarm set: " + remTime.getHour() + ":" + remTime.getMinute() , Toast.LENGTH_SHORT).show();
    }

}
