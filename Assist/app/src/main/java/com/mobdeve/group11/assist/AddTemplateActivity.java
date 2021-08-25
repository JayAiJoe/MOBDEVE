package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddTemplateActivity extends AppCompatActivity {
    private ImageView ivCancel, ivDone;
    private EditText etTitle, etSub, etNotes;
    private TextView tvHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
    }

    private void initComponents() {
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.etTitle = findViewById(R.id.et_add_template_title);
        this.etSub = findViewById(R.id.et_add_template_subject);
        this.etNotes = findViewById(R.id.et_add_template_notes);

        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);
        this.tvHead.setText("Add Template");

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewTemplateListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        this.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String sub = etSub.getText().toString();
                String notes = etNotes.getText().toString();

                if (title.length() > 0 && sub.length() > 0 && notes.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(TemplateInfo.TITLE.name(), title);
                    intent.putExtra(TemplateInfo.SUBJECT.name(), sub);
                    intent.putExtra(TemplateInfo.NOTES.name(), notes);

                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                else {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "You have not yet entered in all of the required fields!",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
    }

}
