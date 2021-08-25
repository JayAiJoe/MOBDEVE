package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ViewEventActivity extends AppCompatActivity {

    private ImageView ivBackDay, ivEdit;
    private TextView tvName, tvDate, tvTime, tvTemplate, tvRemind, tvGroups, tvHead;
    private Activity activity = ViewEventActivity.this;

    ChipGroup cgViewGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        cgViewGroups = findViewById(R.id.cg_temp_view_group);

        //get and display groups
        String[] groupsArray = getResources().getStringArray(R.array.sample_groups); //sample only
        displayGroups(groupsArray, cgViewGroups);

        initInfo();

    }

    private void initInfo(){
        this.tvName = findViewById(R.id.tv_view_event_name);
        this.tvDate = findViewById(R.id.tv_view_date);
        this.tvTime = findViewById(R.id.tv_view_time);
        this.tvTemplate = findViewById(R.id.tv_view_template);
        this.tvRemind = findViewById(R.id.tv_view_reminder);
        this.tvHead = findViewById(R.id.tv_toolbar_view_title);
        //groups

        Intent intent = getIntent();

        String name = intent.getStringExtra(EventInfo.NAME.name());
        String date = intent.getStringExtra(EventInfo.DATE.name());
        String sTime = intent.getStringExtra(EventInfo.START_TIME.name());
        String eTime = intent.getStringExtra(EventInfo.END_TIME.name());
        String template = intent.getStringExtra(EventInfo.TEMPLATE.name());
        String remind = intent.getStringExtra(EventInfo.REMINDER.name());
        //groups

        this.tvName.setText(name);
        this.tvDate.setText(date);
        this.tvTime.setText(sTime+" - "+eTime);
        this.tvTemplate.setText(template);
        this.tvRemind.setText(remind);

        this.tvHead.setText("August 11");

        ivBackDay = findViewById(R.id.iv_toolbar_view_left);
        ivEdit = findViewById(R.id.iv_toolbar_view_right);

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditEventActivity.class);

                intent.putExtra(EventInfo.NAME.name(), name);
                intent.putExtra(EventInfo.DATE.name(), date);
                intent.putExtra(EventInfo.START_TIME.name(), sTime);
                intent.putExtra(EventInfo.END_TIME.name(), eTime);
                intent.putExtra(EventInfo.TEMPLATE.name(), template);
                intent.putExtra(EventInfo.REMINDER.name(), remind);
                //groups

                activity.startActivityForResult(intent, 1);
            }
        });

        this.ivBackDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarDayActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayGroups(String[] groups , ChipGroup pChipGroup) {
        for(int i=0; i<groups.length; i++){
            Chip lChip = new Chip(this);
            lChip.setText(groups[i]);
            pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
        }
    }
}