package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarDayActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView tvMonthYear, tvHead;
    private RecyclerView rvDay;
    private ImageView ivBackMonth, ivAdd;

    //private Button btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day);
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        rvDay = findViewById(R.id.rv_calendar_day);
        tvMonthYear = findViewById(R.id.tv_calendar_day);
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
                startActivity(intent);
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


    /*public void addEventToday(View view) {
        //go to Add Event Activity
    }*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume() {
        super.onResume();
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now(); //change to selected date from month view
        setWeekView();
    }

}