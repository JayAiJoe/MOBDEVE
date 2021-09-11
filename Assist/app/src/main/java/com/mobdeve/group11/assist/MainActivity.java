package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button quick, calendar, contacts, groups, templates;
    private Activity activity = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initComponents() {
        this.quick = findViewById(R.id.btn_home_quick);
        this.calendar = findViewById(R.id.btn_home_calendar);
        this.contacts = findViewById(R.id.btn_home_contacts);
        this.groups = findViewById(R.id.btn_home_groups);
        this.templates = findViewById(R.id.btn_home_templates);

        this.quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuickTextActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });

        this.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarMonthActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });

        this.contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewContactListActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });

        this.groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewGroupListActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });

        this.templates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTemplateListActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });
    }


    public void onResume() {
        super.onResume();
        this.initComponents();
    }
}