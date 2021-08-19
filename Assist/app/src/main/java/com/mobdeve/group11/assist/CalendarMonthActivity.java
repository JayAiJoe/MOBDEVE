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
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarMonthActivity extends AppCompatActivity implements CalendarMonthAdapter.OnItemListener{

    private TextView tvMonthYear;
    private RecyclerView rvMonth;
    private LocalDate selectedDate;

    private Button btnPrev;
    private Button btnNext;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets(){
        rvMonth = findViewById(R.id.rv_calendar_month);
        tvMonthYear = findViewById(R.id.tv_calendar_month);
        btnPrev = findViewById(R.id.btn_month_left);
        btnNext = findViewById(R.id.btn_month_right);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        tvMonthYear.setText(dateToMonthYear(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarMonthAdapter cma = new CalendarMonthAdapter(daysInMonth, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getApplicationContext(), 7);
        rvMonth.setLayoutManager(lm);
        rvMonth.setAdapter(cma);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth ym = YearMonth.from(date);
        int daysInMonth = ym.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 0; i<=42; i++){
            if(i<=dayOfWeek || i>daysInMonth+dayOfWeek)
                daysInMonthArray.add("");
            else
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
        }

        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String dateToMonthYear(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        //go to CalendarDayActivity
    }
}