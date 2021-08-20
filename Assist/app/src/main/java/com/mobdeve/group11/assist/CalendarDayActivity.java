package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarDayActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView tvMonthYear;
    private RecyclerView rvDay;

    private Button btnAddEvent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now(); //change to selected date from month view
        setWeekView();
    }

    private void initWidgets(){
        rvDay = findViewById(R.id.rv_calendar_day);
        tvMonthYear = findViewById(R.id.tv_calendar_day);
        btnAddEvent = findViewById(R.id.btn_calendar_day_add_event);
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


    public void addEventToday(View view) {
        //go to Add Event Activity
    }
}