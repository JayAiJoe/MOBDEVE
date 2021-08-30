package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.ContactGroup;
import com.mobdeve.group11.assist.database.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarDayActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    public static final int NEW_EVENT_ACTIVITY_REQUEST_CODE = 1;

    private AssistViewModel viewModel;

    private TextView tvMonthYear, tvHead;
    private RecyclerView rvDay, rvEvents;
    private ImageView ivBackMonth, ivAdd;

    private EventAdapter eventAdapter;

    //private Button btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day);

        //**** define the viewModel
        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        rvDay = findViewById(R.id.rv_calendar_day);
        tvMonthYear = findViewById(R.id.tv_calendar_day);
        rvEvents = findViewById(R.id.rv_day_events);
        //btnAddEvent = findViewById(R.id.btn_calendar_day_add_event);

        ivBackMonth = findViewById(R.id.iv_toolbar_date_left);
        ivAdd = findViewById(R.id.iv_toolbar_date_right);

        /*String temp[] = CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate).split(" ");*/

        tvHead = findViewById(R.id.tv_toolbar_date_title);
        this.tvHead.setText("August");

        ivBackMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarDayActivity.this, CalendarMonthActivity.class);
                startActivity(intent);
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarDayActivity.this, AddEventActivity.class);
                startActivityForResult(intent, NEW_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView(){
        tvMonthYear.setText(CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInWeek = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter cma = new CalendarAdapter(daysInWeek, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getApplicationContext(), 7);
        rvDay.setLayoutManager(lm);
        rvDay.setAdapter(cma);
    }


    private void setEventsView(){
        eventAdapter = new EventAdapter(this);
        rvEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvEvents.setAdapter(eventAdapter);


        viewModel.getAllEvents().observe(this, events -> {
            if(events != null)
                this.eventAdapter.setDataEvents(events);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prevWeek(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeek(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();

        //display events of the day
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Event e = new Event(data.getStringExtra(EventInfo.NAME.name()),
                    LocalDate.parse(data.getStringExtra(EventInfo.DATE.name())),
                    LocalTime.parse(data.getStringExtra(EventInfo.START_TIME.name())),
                    LocalTime.parse(data.getStringExtra(EventInfo.END_TIME.name())),
                    data.getIntExtra(EventInfo.TEMPLATE.name(), 0),
                    data.getIntExtra(EventInfo.REMINDER.name(), 0));
            viewModel.addEvent(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume() {
        super.onResume();
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now(); //change to selected date from month view
        setWeekView();
        setEventsView();
    }

}