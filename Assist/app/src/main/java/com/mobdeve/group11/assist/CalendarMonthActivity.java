package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdeve.group11.assist.database.AssistViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarMonthActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{



    private TextView tvMonthYear, tvHead;
    private RecyclerView rvMonth;
    private ImageView ivBackYear, ivAdd;
    private Activity activity = CalendarMonthActivity.this;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);
        CalendarUtils.selectedDate = LocalDate.now();
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        rvMonth = findViewById(R.id.rv_calendar_month);
        tvMonthYear = findViewById(R.id.tv_calendar_month);
        ivBackYear = findViewById(R.id.iv_toolbar_left);
        ivAdd = findViewById(R.id.iv_toolbar_right);
        //tvHead = findViewById(R.id.tv_toolbar_date_title);

        //this.tvHead.setText(""+ CalendarUtils.selectedDate.getYear());

        ivBackYear.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(CalendarMonthActivity.this, AddEventActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        tvMonthYear.setText(CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate));
        tvHead.setText(""+ CalendarUtils.selectedDate.getYear());
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);
        CalendarAdapter cma = new CalendarAdapter(daysInMonth,this);
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
            activity.startActivityForResult(intent, 1);
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
        setMonthView();
    }
}