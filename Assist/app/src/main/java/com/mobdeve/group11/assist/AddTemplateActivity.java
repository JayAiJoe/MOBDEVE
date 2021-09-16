package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Template;

import java.util.ArrayList;

public class AddTemplateActivity extends AppCompatActivity {

    private AssistViewModel viewModel;

    private ImageView ivCancel, ivDone;
    private EditText etTitle, etSub, etNotes;
    private TextView tvHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        this.initComponents();
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
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        this.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString().trim();
                String sub = etSub.getText().toString().trim();
                String notes = etNotes.getText().toString().trim();

                if (title.length() > 0 && sub.length() > 0 && notes.length() > 0) {
                    Intent intent = new Intent();
                    viewModel.addTemplate(new Template(title,sub, notes));
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

}
