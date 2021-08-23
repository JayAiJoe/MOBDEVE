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

public class AddContactActivity extends AppCompatActivity {

    /* todos
    add pic
    rv of group!!!!
    add group
    delete group
     */

    private ImageView ivPic, ivCancel, ivDone;
    private TextView tvPic;
    private EditText etFName, etLName, etPNum, etGuardian;
    private TextInputLayout tilGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    private void initComponents(){
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.ivPic = findViewById(R.id.iv_add_contact_picture);
        this.tvPic = findViewById(R.id.tv_add_contact_add_pic);
        this.etFName = findViewById(R.id.et_add_contact_fname);
        this.etLName = findViewById(R.id.et_add_contact_lname);
        this.etPNum = findViewById(R.id.et_add_contact_number);
        this.etGuardian = findViewById(R.id.et_add_contact_guardian);
        this.tilGroup = findViewById(R.id.til_add_contact_groups);

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
                //ArrayList<Group> groups

                if (fName.length() > 0 && lName.length() > 0 && pNum.length() > 0 && guardian.length() > 0){

                    Intent intent = new Intent();
                    intent.putExtra(ContactInfo.FIRST_NAME.name(), fName);
                    intent.putExtra(ContactInfo.LAST_NAME.name(), lName);
                    intent.putExtra(ContactInfo.PHONE_NUMBER.name(), pNum);
                    intent.putExtra(ContactInfo.GUARDIAN.name(), guardian);

                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(),
                            "You have not yet entered anything!",
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
