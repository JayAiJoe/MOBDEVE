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
    private Button btnDelete;

    private Activity activity = EditGroupActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
    }

    private void initComponents() {
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.btnDelete = findViewById(R.id.btn_edit_group_delete);

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewGroupListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        this.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();

                if (name.length() > 0) {
                    Intent intent = new Intent(view.getContext(), ViewGroupListActivity.class);

                    intent.putExtra(GroupInfo.NAME.name(), name);

                    activity.startActivityForResult(intent, 1);
                }
                else {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "You have not yet entered in all of the required fields!",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from database
                Intent intent = new Intent(v.getContext(), ViewGroupListActivity.class);
                startActivity(intent);
            }
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
