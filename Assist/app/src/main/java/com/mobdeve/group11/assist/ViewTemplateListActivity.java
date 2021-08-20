package com.mobdeve.group11.assist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ViewTemplateListActivity extends AppCompatActivity {
    private RecyclerView rvtemplates;
    private ImageView ivLeft, ivRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_list);

        this.rvtemplates = findViewById(R.id.rvListWithAlphabet);
        this.ivLeft = findViewById(R.id.ivLeft);
        this.ivRight = findViewById(R.id.ivRight);

        this.ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTemplateListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        this.ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTemplateListActivity.this, AddTemplateActivity.class);
                startActivity(intent);
            }
        });

    }

}
