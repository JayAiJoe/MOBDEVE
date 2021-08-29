package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Template;

import java.util.ArrayList;
import java.util.Arrays;

public class TemplateActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST = 1;

    private AssistViewModel viewModel;

    private ImageView ivBack, ivEdit;
    private TextView tvTitle, tvSub, tvNotes, tvHead;
    private Button btnDelete;

    private Activity activity = TemplateActivity.this;
    private Template template = new Template("", "", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);
    }

    private void initComponents() {
        this.ivBack = findViewById(R.id.iv_toolbar_view_left);
        this.ivEdit = findViewById(R.id.iv_toolbar_view_right);
        this.btnDelete = findViewById(R.id.btn_view_template_delete);

        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        this.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), EditTemplateActivity.class);

                intent.putExtra(TemplateInfo.TITLE.name(), template.getTitle());
                intent.putExtra(TemplateInfo.SUBJECT.name(), template.getSubject());
                intent.putExtra(TemplateInfo.NOTES.name(), template.getContent());

                activity.startActivityForResult(intent, EDIT_REQUEST);
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteTemplate(template);
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void initInfo() {
        this.tvTitle = findViewById(R.id.tv_view_template_title);
        this.tvSub = findViewById(R.id.tv_view_template_subject);
        this.tvNotes = findViewById(R.id.tv_view_template_notes);
        this.tvHead = findViewById(R.id.tv_toolbar_view_title);

        Intent intent = getIntent();
        viewModel.getTemplateById(intent.getIntExtra(TemplateInfo.ID.name(), 0)).observe(this, curr_template -> {
            this.template = curr_template;
            if(curr_template != null){
                this.tvTitle.setText(template.getTitle());
                this.tvSub.setText(template.getSubject());
                this.tvNotes.setText(template.getContent());
            }
        });

        this.tvHead.setText("Templates");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            template.setTitle(data.getStringExtra(TemplateInfo.TITLE.name()));
            template.setSubject(data.getStringExtra(TemplateInfo.SUBJECT.name()));
            template.setContent(data.getStringExtra(TemplateInfo.NOTES.name()));
            viewModel.updateTemplate(template);
        }
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

}
