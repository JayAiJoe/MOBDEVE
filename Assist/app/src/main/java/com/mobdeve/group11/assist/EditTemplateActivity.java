package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Template;

import java.util.ArrayList;
import java.util.Arrays;

public class EditTemplateActivity extends AppCompatActivity {

    private AssistViewModel viewModel;

    private ImageView ivCancel, ivDone;
    private TextView tvHead;
    private EditText etTitle, etSub, etNotes;

    private Activity activity = EditTemplateActivity.this;

    private Template template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);
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

            String title = etTitle.getText().toString().trim();
            String sub = etSub.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            if (title.length() > 0 && sub.length() > 0 && notes.length() > 0){
                Intent intent = new Intent();

                template.setTitle(title);
                template.setSubject(sub);
                template.setContent(notes);
                viewModel.updateTemplate(template);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            else {
                Toast t = Toast.makeText(getApplicationContext(),
                        "You have not yet entered in all of the required fields!",
                        Toast.LENGTH_LONG);
                t.show();
            }
        });


    }

    private void initInfo(){
        this.etTitle = findViewById(R.id.et_edit_template_title);
        this.etSub = findViewById(R.id.et_edit_template_subject);
        this.etNotes = findViewById(R.id.et_edit_template_notes);
        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);

        viewModel.getTemplateById(getIntent().getIntExtra(TemplateInfo.ID.name(), 0)).observe( this, template -> {
            if(template != null){
                this.template = template;
                this.etTitle.setText(template.getTitle());
                this.etSub.setText(template.getSubject());
                this.etNotes.setText(template.getContent());
                this.tvHead.setText("Edit Template");
            }
            else{
                Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

}
