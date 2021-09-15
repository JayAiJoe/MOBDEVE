package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    private Context context;

    private Activity activity = CalendarDayActivity.this;

    //private Button btnAddEvent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day);

        context = this;


        rvEvents = findViewById(R.id.rv_day_events);
        eventAdapter = new EventAdapter(this);
        rvEvents.setAdapter(eventAdapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //**** define the viewModel
        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);


        viewModel.getEventsByDay(CalendarUtils.selectedDate).observe(this, events -> {
            if(events != null)
                this.eventAdapter.setDataEvents(events);
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        rvDay = findViewById(R.id.rv_calendar_day);
        tvMonthYear = findViewById(R.id.tv_toolbar_date_title);

        //btnAddEvent = findViewById(R.id.btn_calendar_day_add_event);

        ivBackMonth = findViewById(R.id.iv_toolbar_date_left);
        ivAdd = findViewById(R.id.iv_toolbar_date_right);

        /*String temp[] = CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate).split(" ");*/

        //tvHead = findViewById(R.id.tv_toolbar_date_title);
        //this.tvHead.setText(CalendarUtils.selectedDate.getMonth().toString());

        ivBackMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarDayActivity.this, AddEventActivity.class);
                activity.startActivityForResult(intent, NEW_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });

        rvDay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                tvHead.setText(CalendarUtils.selectedDate.getMonth().toString());
                viewModel.getEventsByDay(CalendarUtils.selectedDate).observe(CalendarDayActivity.this, events -> {
                    if(events != null)
                        eventAdapter.setDataEvents(events);
                });

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView(){
        tvMonthYear.setText(CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate));
        //tvHead.setText(CalendarUtils.selectedDate.getMonth().toString());
        ArrayList<LocalDate> daysInWeek = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter cma = new CalendarAdapter(daysInWeek, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getApplicationContext(), 7);
        rvDay.setLayoutManager(lm);
        rvDay.setAdapter(cma);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEventsView(){
        viewModel.getEventsByDay(CalendarUtils.selectedDate).observe(this, events -> {
            if(events != null)
                this.eventAdapter.setDataEvents(events);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prevWeek(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
        setEventsView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeek(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
        setEventsView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
        setEventsView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume() {
        super.onResume();
        initWidgets();
        setWeekView();
    }

}