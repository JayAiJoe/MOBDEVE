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

public class EditContactActivity extends AppCompatActivity {

    private ImageView ivCancel, ivDone, ivPic;
    private TextView tvPic, tvGroups, tvHead;
    private EditText etFName, etLName, etPNum, etGuardian;
    private Button btnDelete;

    private Activity activity = EditContactActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
    }

    private void initComponents(){
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.btnDelete = findViewById(R.id.btn_edit_contact_delete);

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewContactListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        this.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //photo
                String fName = etFName.getText().toString();
                String lName = etLName.getText().toString();
                String pNum = etPNum.getText().toString();
                String guardian = etGuardian.getText().toString();
                //ArrayList<String> sGroups = new ArrayList<String>(Arrays.asList(tvGroups.getText().toString().split(",")));

                if (fName.length() > 0 && lName.length() > 0 && pNum.length() > 0){

                    Intent intent = new Intent(view.getContext(), ViewContactListActivity.class);
                    //add photo to database
                    intent.putExtra(ContactInfo.FIRST_NAME.name(), fName);
                    intent.putExtra(ContactInfo.LAST_NAME.name(), lName);
                    intent.putExtra(ContactInfo.PHONE_NUMBER.name(), pNum);
                    intent.putExtra(ContactInfo.GUARDIAN.name(), guardian);
                    //add as member to the groups in database

                    activity.startActivityForResult(intent, 1);
                }
                else{
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
                Intent intent = new Intent(v.getContext(), ViewContactListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initInfo(){
        this.ivPic = findViewById(R.id.iv_edit_contact_picture);
        this.tvPic = findViewById(R.id.tv_edit_contact_add_pic);
        this.etFName = findViewById(R.id.et_edit_contact_fname);
        this.etLName = findViewById(R.id.et_edit_contact_lname);
        this.etPNum = findViewById(R.id.et_edit_contact_number);
        this.etGuardian = findViewById(R.id.et_edit_contact_guardian);
        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);

        if(getIntent().hasExtra(ContactInfo.FIRST_NAME.name()) && getIntent().hasExtra(ContactInfo.LAST_NAME.name()) &&
                getIntent().hasExtra(ContactInfo.PHONE_NUMBER.name())) {
            //photo
            this.etFName.setText(getIntent().getStringExtra(ContactInfo.FIRST_NAME.name()));
            this.etLName.setText(getIntent().getStringExtra(ContactInfo.LAST_NAME.name()));
            this.etPNum.setText(getIntent().getStringExtra(ContactInfo.PHONE_NUMBER.name()));
            this.etGuardian.setText(getIntent().getStringExtra(ContactInfo.GUARDIAN.name()));
            //groups
            this.tvHead.setText("Edit Contact");
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