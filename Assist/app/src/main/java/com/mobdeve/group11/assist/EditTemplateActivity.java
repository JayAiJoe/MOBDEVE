package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class EditTemplateActivity extends AppCompatActivity {
    private ImageView ivCancel, ivDone;
    private TextView tvHead;
    private EditText etTitle, etSub, etNotes;

    private Activity activity = EditTemplateActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);
    }

    private void initComponents() {
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        this.ivDone.setOnClickListener(view -> {
            Intent intent = new Intent();
            String title = etTitle.getText().toString();
            String sub = etSub.getText().toString();
            String notes = etNotes.getText().toString();

            if (title.length() > 0 && sub.length() > 0 && notes.length() > 0){
                intent.putExtra(TemplateInfo.TITLE.name(), title);
                intent.putExtra(TemplateInfo.SUBJECT.name(), sub);
                intent.putExtra(TemplateInfo.NOTES.name(), notes);
                setResult(Activity.RESULT_OK, intent);
            }
            else {
                Toast t = Toast.makeText(getApplicationContext(),
                        "You have not yet entered in all of the required fields!",
                        Toast.LENGTH_LONG);
                t.show();
            }
            finish();
        });


    }

    private void initInfo(){
        this.etTitle = findViewById(R.id.et_edit_template_title);
        this.etSub = findViewById(R.id.et_edit_template_subject);
        this.etNotes = findViewById(R.id.et_edit_template_notes);
        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);

        if(getIntent().hasExtra(TemplateInfo.TITLE.name()) && getIntent().hasExtra(TemplateInfo.SUBJECT.name()) &&
                getIntent().hasExtra(TemplateInfo.NOTES.name())) {
            //photo
            this.etTitle.setText(getIntent().getStringExtra(TemplateInfo.TITLE.name()));
            this.etSub.setText(getIntent().getStringExtra(TemplateInfo.SUBJECT.name()));
            this.etNotes.setText(getIntent().getStringExtra(TemplateInfo.NOTES.name()));

            //groups

            this.tvHead.setText("Edit Template");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

}
