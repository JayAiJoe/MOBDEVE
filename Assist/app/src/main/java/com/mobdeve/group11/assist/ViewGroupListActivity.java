package com.mobdeve.group11.assist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ViewGroupListActivity extends AppCompatActivity {
    private RecyclerView rvGroups;
    private ImageView ivLeft, ivRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);

        this.rvGroups = findViewById(R.id.rvListWithAlphabet);
        this.ivLeft = findViewById(R.id.ivLeft);
        this.ivRight = findViewById(R.id.ivRight);

        this.ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewGroupListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        this.ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewGroupListActivity.this, AddGroupActivity.class);
                startActivity(intent);
            }
        });

    }

}
