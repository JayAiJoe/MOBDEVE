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

public class AddGroupActivity extends AppCompatActivity {
    private ImageView ivPic, ivCancel, ivDone;
    private TextView tvPic;
    private EditText etName;
    private TextInputLayout tilMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    private void initComponents() {
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.ivPic = findViewById(R.id.iv_add_group_picture);
        this.tvPic = findViewById(R.id.tv_add_group_add_pic);
        this.etName = findViewById(R.id.et_add_group_gname);
        this.tilMembers = findViewById(R.id.til_add_group_members);

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
                String name = etName.getText().toString();
                if (name.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(GroupInfo.NAME.name(), name);

                    //add members

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else {
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
