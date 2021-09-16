package com.mobdeve.group11.assist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdeve.group11.assist.database.AssistViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//monthly calendar activity
public class CalendarMonthActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private AssistViewModel viewModel;

    private TextView tvMonthYear, tvHead;
    private RecyclerView rvMonth;
    private ImageView ivBackYear, ivAdd;
    private Activity activity = CalendarMonthActivity.this;

    private CalendarAdapter cma;
    private ArrayList<LocalDate> daysInMonth;

    int nCores = Runtime.getRuntime().availableProcessors();
    int maxPool = nCores + 8;
    int keepAlive = 1000;
    TimeUnit keepAliveUnit = TimeUnit.MILLISECONDS;

    private EventCountRunnable eventCountRunnable;
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(nCores, maxPool, keepAlive, keepAliveUnit, new LinkedBlockingDeque<Runnable>());

    //handler for counting the number of events per day
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message message){
            super.handleMessage(message);

            Bundle data = message.getData();
            int index = data.getInt("INDEX");

            viewModel.countEventsOfTheDay(daysInMonth.get(index)).observe(CalendarMonthActivity.this, count->{
                if(cma != null)
                    if(cma.getViewByPosition(index) != null)
                            cma.getViewByPosition(index).setCount(count);
            });
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);
        CalendarUtils.selectedDate = LocalDate.now();
        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        initWidgets();
        setMonthView();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        rvMonth = findViewById(R.id.rv_calendar_month);
        tvMonthYear = findViewById(R.id.tv_calendar_month);
        ivBackYear = findViewById(R.id.iv_toolbar_left);
        ivAdd = findViewById(R.id.iv_toolbar_right);

        //ensure no "blinking" happens
        ((SimpleItemAnimator) rvMonth.getItemAnimator()).setSupportsChangeAnimations(false);

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


    //fill the recycler view with the correct days
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        tvMonthYear.setText(CalendarUtils.dateToMonthYear(CalendarUtils.selectedDate));
        //tvHead.setText(""+ CalendarUtils.selectedDate.getYear());
        daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);
        cma = new CalendarAdapter(daysInMonth,this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getApplicationContext(), 7);
        rvMonth.setLayoutManager(lm);
        rvMonth.setAdapter(cma);

        for (int i = 0; i < daysInMonth.size(); i++) {
            LocalDate dayTemp = daysInMonth.get(i);
            if(dayTemp != null){
                eventCountRunnable = new EventCountRunnable(i, handler);
                threadPoolExecutor.execute(eventCountRunnable);
            }
        }

    }

    //actions when a day is clicked
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null){
            CalendarUtils.selectedDate = date;
            //setMonthView();
            if(cma != null)
                cma.showSelected();

            //go to calendar day view
            Intent intent = new Intent(CalendarMonthActivity.this, CalendarDayActivity.class);
            activity.startActivityForResult(intent, 1);
        }
    }

    //show the previous month
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prevMonth(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    //show the next month
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonth(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    //update the calendar with the currently selected day on resume
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume() {
        super.onResume();
        setMonthView();
    }

}