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

public class ContactActivity extends AppCompatActivity {

    private ImageView ivBack, ivEdit, ivPic;
    private TextView tvName, tvPNum, tvGuardian, tvGroups;
    private Button btnDelete;

    private String fName, lName, pNum, guardian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    private void initComponents(){
        this.ivBack = findViewById(R.id.iv_toolbar_view_left);
        this.ivEdit = findViewById(R.id.iv_toolbar_view_right);
        this.btnDelete = findViewById(R.id.btn_view_contact_delete);

        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewContactListActivity.class);
                startActivity(intent);
            }
        });

        this.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                //add photo to database
                intent.putExtra(ContactInfo.FIRST_NAME.name(), fName);
                intent.putExtra(ContactInfo.LAST_NAME.name(), lName);
                intent.putExtra(ContactInfo.PHONE_NUMBER.name(), pNum);
                intent.putExtra(ContactInfo.GUARDIAN.name(), guardian);
                //add as member to the groups in database

                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from database
                Intent intent = new Intent(v.getContext(), ViewContactListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initInfo(){
        this.ivPic = findViewById(R.id.iv_view_contact_pic);
        this.tvName = findViewById(R.id.tv_view_contact_name);
        this.tvPNum = findViewById(R.id.tv_view_contact_number);
        this.tvGuardian = findViewById(R.id.tv_view_contact_guardian);
        this.tvGroups = findViewById(R.id.tv_view_contact_groups);

        Intent intent = getIntent();

        //photo
        this.fName = intent.getStringExtra(ContactInfo.FIRST_NAME.name());
        this.lName = intent.getStringExtra(ContactInfo.LAST_NAME.name());
        this.pNum = intent.getStringExtra(ContactInfo.PHONE_NUMBER.name());
        this.guardian = intent.getStringExtra(ContactInfo.GUARDIAN.name());
        //ArrayList<String> sGroups = new ArrayList<String>(Arrays.asList(tvGroups.getText().toString().split(",")));

        //photo
        this.tvName.setText(fName+" "+lName);
        this.tvPNum.setText(pNum);
        this.tvGuardian.setText(guardian);
        //groups
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }
}
