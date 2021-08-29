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
import com.mobdeve.group11.assist.database.ContactGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupActivity extends AppCompatActivity{

    public static final int EDIT_REQUEST = 1;

    private AssistViewModel viewModel;

    private ImageView ivBack, ivEdit, ivPic;
    private TextView tvName, tvMembers, tvHead;
    private Button btnDelete;

    private ContactGroup group = new ContactGroup("");
    private Activity activity = GroupActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);
    }

    private void initComponents() {
        this.ivBack = findViewById(R.id.iv_toolbar_view_left);
        this.ivEdit = findViewById(R.id.iv_toolbar_view_right);
        this.btnDelete = findViewById(R.id.btn_view_group_delete);

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

                Intent intent = new Intent(v.getContext(), EditGroupActivity.class);

                //add photo to database
                intent.putExtra(GroupInfo.NAME.name(), group.getName());
                //add as group to the members in database

                activity.startActivityForResult(intent, EDIT_REQUEST);
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteGroup(group);
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            group.setName(data.getStringExtra(GroupInfo.NAME.name()));
            viewModel.updateGroup(group);
        } else {
            Toast.makeText(getApplicationContext(), R.string.not_saved, Toast.LENGTH_LONG).show();
        }
    }

    private void initInfo(){
        this.ivPic = findViewById(R.id.iv_view_group_picture);
        this.tvName = findViewById(R.id.tv_view_group_name);
        this.tvMembers = findViewById(R.id.tv_view_group_members);
        this.tvHead = findViewById(R.id.tv_toolbar_view_title);

        Intent intent = getIntent();
        viewModel.getGroupById(intent.getIntExtra(GroupInfo.ID.name(), 0)).observe(this, curr_group -> {
            this.group = curr_group;
            if(curr_group != null)
                this.tvName.setText(group.getName());
        });

        this.tvHead.setText("Group");
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

}
