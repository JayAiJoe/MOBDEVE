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

public class EditGroupActivity extends AppCompatActivity {

    private ImageView ivCancel, ivDone, ivPic;
    private TextView tvPic, tvMembers, tvHead;
    private EditText etName;

    private Activity activity = EditGroupActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
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
            String name = etName.getText().toString();

            if (name.length() > 0) {
                intent.putExtra(GroupInfo.NAME.name(), name);
                setResult(Activity.RESULT_OK, intent);
            }
            else{
                setResult(Activity.RESULT_CANCELED, intent);
            }
            finish();
        });
    }

    private void initInfo() {
        this.ivPic = findViewById(R.id.iv_edit_group_picture);
        this.tvPic = findViewById(R.id.tv_edit_group_add_pic);
        this.etName = findViewById(R.id.et_edit_group_gname);
        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);

        if(getIntent().hasExtra(GroupInfo.NAME.name())) {
            //photo
            this.etName.setText(getIntent().getStringExtra(GroupInfo.NAME.name()));

            this.tvHead.setText("Edit Group");

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
