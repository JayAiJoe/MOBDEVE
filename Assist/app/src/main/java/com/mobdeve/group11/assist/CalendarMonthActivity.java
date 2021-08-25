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

public class CalendarMonthActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView tvMonthYear, tvHead;
    private RecyclerView rvMonth;
    private ImageView ivBackYear, ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        rvMonth = findViewById(R.id.rv_calendar_month);
        tvMonthYear = findViewById(R.id.tv_calendar_month);
        ivBackYear = findViewById(R.id.iv_toolbar_date_left);
        ivAdd = findViewById(R.id.iv_toolbar_date_right);
        tvHead = findViewById(R.id.tv_toolbar_date_title);

        /*String temp[] = CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate).split(" ");

        tvHead = findViewById(R.id.tv_toolbar_date_title);*/
        this.tvHead.setText("2021");

        ivBackYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarMonthActivity.this, MainActivity.class); //main (gawin year once oks)
                startActivity(intent);
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarMonthActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        tvMonthYear.setText(CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);
        CalendarAdapter cma = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getApplicationContext(), 7);
        rvMonth.setLayoutManager(lm);
        rvMonth.setAdapter(cma);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null){
            CalendarUtils.selectedDate = date;
            setMonthView();

            //go to calendar day view
            Intent intent = new Intent(CalendarMonthActivity.this, CalendarDayActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prevMonth(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonth(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume() {
        super.onResume();
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
    }
}