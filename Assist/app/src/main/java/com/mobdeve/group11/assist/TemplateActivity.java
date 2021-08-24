package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class TemplateActivity extends AppCompatActivity {
    private ImageView ivBack, ivEdit;
    private TextView tvTitle, tvSub, tvNotes;
    private Button btnDelete;

    private String title, sub, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
    }

    private void initComponents() {
        this.ivBack = findViewById(R.id.iv_toolbar_view_left);
        this.ivEdit = findViewById(R.id.iv_toolbar_view_right);
        this.btnDelete = findViewById(R.id.btn_view_template_delete);

        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewTemplateListActivity.class);
                startActivity(intent);
            }
        });

        this.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                //add photo to database
                intent.putExtra(TemplateInfo.TITLE.name(), title);
                intent.putExtra(TemplateInfo.SUBJECT.name(), sub);
                intent.putExtra(TemplateInfo.NOTES.name(), notes);

                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from database
                Intent intent = new Intent(v.getContext(), ViewTemplateListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        this.tvTitle = findViewById(R.id.tv_view_template_title);
        this.tvSub = findViewById(R.id.tv_view_template_subject);
        this.tvNotes = findViewById(R.id.tv_view_template_notes);

        Intent intent = getIntent();

        this.title = intent.getStringExtra(TemplateInfo.TITLE.name());
        this.sub = intent.getStringExtra(TemplateInfo.SUBJECT.name());
        this.notes = intent.getStringExtra(TemplateInfo.NOTES.name());

        this.tvTitle.setText(title);
        this.tvSub.setText(sub);
        this.tvNotes.setText(notes);

    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

}
