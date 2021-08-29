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
import com.mobdeve.group11.assist.database.ContactGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewGroupListActivity extends AppCompatActivity {

    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;



    private AssistViewModel viewModel;

    private ArrayList<Group> dataGroups = new ArrayList<Group>();
    private RecyclerView rvGroups;
    private GroupAdapter groupAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberGroups;

    //sorted in alphabetical order
    private ArrayList<Group> sortList(ArrayList<Group> list) {
        Collections.sort(list, new Comparator<Group>() {
            @Override
            public int compare(Group g1, Group g2) {
                int ctr = g1.getName().compareTo(g2.getName());
                return ctr;
            }
        });
        return list;
    }

    /*
    //add alphabets
    //1-alphabet
    //2-name
    ArrayList<Group> addAlphabets(ArrayList<Group> list) {
        int i = 0;
        ArrayList<Group> customList = new ArrayList<Group>();
        Group g1 = new Group();
        g1.setName(String.valueOf(list.get(0).getName().charAt(0)));
        g1.setType(1);
        customList.add(g1);
        for (i = 0; i < list.size() - 1; i++) {
            Group g2 = new Group();
            char name1 = list.get(i).getName().charAt(0);
            char name2 = list.get(i + 1).getName().charAt(0);
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

     */


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
            this.groupAdapter.setDataGroups(groups);
        });
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_left);
        this.tvNumberGroups = findViewById(R.id.tv_glist_total);

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
                Intent intent = new Intent(ViewGroupListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_GROUP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            ContactGroup g = new ContactGroup(data.getStringExtra(GroupInfo.NAME.name()));
            viewModel.addGroup(g);
        }
    }

    private void initRecyclerView(){
        //int count = groupList.size();
        //this.tvNumberGroups.setText(count+" Groups");

        //groups = addAlphabets(dataGroups);

        /*
        this.rvGroups = findViewById(R.id.rv_glist);
        this.rvGroups.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.groupAdapter = new GroupAdapter(groupList, ViewGroupListActivity.this);
        this.rvGroups.setAdapter(this.groupAdapter);
         */
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
        //this.initRecyclerView();
    }
}