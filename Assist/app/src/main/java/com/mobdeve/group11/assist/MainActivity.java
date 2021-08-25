package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button calendar, contacts, groups, templates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initComponents() {
        this.calendar = findViewById(R.id.btn_home_calendar);
        this.contacts = findViewById(R.id.btn_home_contacts);
        this.groups = findViewById(R.id.btn_home_groups);
        this.templates = findViewById(R.id.btn_home_templates);

        this.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarMonthActivity.class);
                startActivity(intent);
            }
        });

        this.contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewContactListActivity.class);
                startActivity(intent);
            }
        });

        this.groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewGroupListActivity.class);
                startActivity(intent);
            }
        });

        this.templates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTemplateListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
    }
}