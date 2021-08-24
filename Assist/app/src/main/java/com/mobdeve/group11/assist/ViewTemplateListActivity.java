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

public class ViewTemplateListActivity extends AppCompatActivity {

    private ArrayList<Template> dataTemplates = new ArrayList<Template>();
    private RecyclerView rvTemplates;
    private TemplateAdapter templateAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberTemplates;

    private ActivityResultLauncher myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    String title = intent.getStringExtra(TemplateInfo.TITLE.name());
                    String sub = intent.getStringExtra(TemplateInfo.SUBJECT.name());
                    String notes = intent.getStringExtra(TemplateInfo.NOTES.name());

                    dataTemplates.add(0, new Template(title, sub, notes));
                }
            }
    );

    //sorted in alphabetical order
    private ArrayList<Template> sortList(ArrayList<Template> list) {
        Collections.sort(list, new Comparator<Template>() {
            @Override
            public int compare(Template t1, Template t2) {
                int ctr = t1.getTitle().compareTo(t2.getTitle());
                return ctr;
            }
        });
        return list;
    }

    //add alphabets
    //1-alphabet
    //2-name
    ArrayList<Template> addAlphabets(ArrayList<Template> list) {
        int i = 0;
        ArrayList<Template> customList = new ArrayList<Template>();
        Template t1 = new Template();
        t1.setTitle(String.valueOf(list.get(0).getTitle().charAt(0)));
        t1.setType(1);
        customList.add(t1);
        for (i = 0; i < list.size() - 1; i++) {
            Template t2 = new Template();
            char name1 = list.get(i).getTitle().charAt(0);
            char name2 = list.get(i + 1).getTitle().charAt(0);
            if (name1 == name2) {
                list.get(i).setType(2);
                customList.add(list.get(i));
            } else {
                list.get(i).setType(2);
                customList.add(list.get(i));
                t2.setTitle(String.valueOf(name2));
                t2.setType(1);
                customList.add(t2);
            }
        }
        list.get(i).setType(2);
        customList.add(list.get(i));
        return customList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_list);
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_view_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_view_left);
        this.tvNumberTemplates = findViewById(R.id.tv_tlist_total);

        this.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTemplateListActivity.this, AddTemplateActivity.class);
                startActivity(intent);
            }
        });

        this.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTemplateListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(){
        DataHelper helper = new DataHelper();
        if (this.dataTemplates.size() == 0){
            this.dataTemplates = helper.initializeTemplates();
        }

        int count = dataTemplates.size();
        this.tvNumberTemplates.setText(count+" Templates");

        ArrayList<Template> templates = new ArrayList<Template>();

        this.dataTemplates = sortList(dataTemplates);
        templates = addAlphabets(dataTemplates);

        this.rvTemplates = findViewById(R.id.rv_tlist);
        this.rvTemplates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.templateAdapter = new TemplateAdapter(templates, ViewTemplateListActivity.this);
        this.rvTemplates.setAdapter(this.templateAdapter);
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
        this.initRecyclerView();
    }
}
