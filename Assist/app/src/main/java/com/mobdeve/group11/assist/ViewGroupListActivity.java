package com.mobdeve.group11.assist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewGroupListActivity extends AppCompatActivity {

    private ArrayList<Group> dataGroups = new ArrayList<Group>();
    private RecyclerView rvGroups;
    private GroupAdapter groupAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberGroups;

    private ActivityResultLauncher myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    String name = intent.getStringExtra(GroupInfo.NAME.name());
                    //ArrayList<String> sGroups = new ArrayList<String>(Arrays.asList(tvMembers.getText().toString().split(",")));

                    //dataGroups.add(0, new Group(name));
                }
            }
    );

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_left);
        this.tvNumberGroups = findViewById(R.id.tv_glist_total);

        this.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewGroupListActivity.this, AddGroupActivity.class);
                startActivity(intent);
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

    private void initRecyclerView(){
        DataHelper helper = new DataHelper();
        if (this.dataGroups.size() == 0){
            this.dataGroups = helper.initializeGroups();
        }

        int count = dataGroups.size();
        this.tvNumberGroups.setText(count+" Groups");

        ArrayList<Group> groups = new ArrayList<Group>();

        this.dataGroups = sortList(dataGroups);
        groups = addAlphabets(dataGroups);

        this.rvGroups = findViewById(R.id.rv_glist);
        this.rvGroups.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.groupAdapter = new GroupAdapter(groups, ViewGroupListActivity.this);
        this.rvGroups.setAdapter(this.groupAdapter);
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
        this.initRecyclerView();
    }
}