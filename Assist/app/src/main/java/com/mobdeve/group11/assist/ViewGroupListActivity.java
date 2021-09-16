package com.mobdeve.group11.assist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;
import com.mobdeve.group11.assist.database.GroupMembership;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//activity for viewing all the contact groups
public class ViewGroupListActivity extends AppCompatActivity {

    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;

    private AssistViewModel viewModel;

    private RecyclerView rvGroups;
    private GroupAdapter groupAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberGroups;


    //add alphabets headings
    //type 1-alphabet, type 2-name
    List<ContactGroup> addAlphabets(List<ContactGroup> list) {
        if (list.size() != 0){
            int i = 0;
            List<ContactGroup> customList = new ArrayList<>();
            ContactGroup g1 = new ContactGroup();
            g1.setName(String.valueOf(list.get(0).getName().charAt(0)).toUpperCase());
            g1.setType(1);
            customList.add(g1);
            for (i = 0; i < list.size() - 1; i++) {
                ContactGroup g2 = new ContactGroup();
                char name1 = list.get(i).getName().toUpperCase().charAt(0);
                char name2 = list.get(i + 1).getName().toUpperCase().charAt(0);
                if (name1 == name2) {
                    list.get(i).setType(2);
                    customList.add(list.get(i));
                } else {
                    list.get(i).setType(2);
                    customList.add(list.get(i));
                    g2.setName(String.valueOf(name2));
                    g2.setType(1);
                    customList.add(g2);
                }
            }
            list.get(i).setType(2);
            customList.add(list.get(i));
            return customList;
        }
        else {
            return list;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);

        rvGroups = findViewById(R.id.rv_glist);
        groupAdapter = new GroupAdapter(ViewGroupListActivity.this);
        rvGroups.setAdapter(this.groupAdapter);
        rvGroups.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        //**** define the viewModel
        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        viewModel.getAllGroups().observe(this, groups -> {
            this.groupAdapter.setDataGroups(addAlphabets(groups));
            this.tvNumberGroups = findViewById(R.id.tv_glist_total);
            int count = groups.size();
            this.tvNumberGroups.setText(count+" Groups");
        });
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_left);

        this.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewGroupListActivity.this, AddGroupActivity.class);
                startActivityForResult(intent, NEW_GROUP_ACTIVITY_REQUEST_CODE);
            }
        });

        this.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}