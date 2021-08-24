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

public class GroupActivity extends AppCompatActivity{
    private ImageView ivBack, ivEdit, ivPic;
    private TextView tvName, tvMembers;
    private Button btnDelete;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
    }

    private void initComponents() {
        this.ivBack = findViewById(R.id.iv_toolbar_view_left);
        this.ivEdit = findViewById(R.id.iv_toolbar_view_right);
        this.btnDelete = findViewById(R.id.btn_view_group_delete);

        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewGroupListActivity.class);
                startActivity(intent);
            }
        });
        this.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                //add photo to database
                intent.putExtra(GroupInfo.NAME.name(), name);
                //add as group to the members in database

                setResult(Activity.RESULT_OK, intent);
                finish();
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

    private void initInfo(){
        this.ivPic = findViewById(R.id.iv_view_group_picture);
        this.tvName = findViewById(R.id.tv_view_group_name);
        this.tvMembers = findViewById(R.id.tv_view_group_members);

        Intent intent = getIntent();

        this.name = intent.getStringExtra(GroupInfo.NAME.name());
        //ArrayList<String> sMembers = new ArrayList<String>(Arrays.asList(tvMembers.getText().toString().split(",")));

        //pic
        this.tvName.setText(name);
        //members

    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

}
