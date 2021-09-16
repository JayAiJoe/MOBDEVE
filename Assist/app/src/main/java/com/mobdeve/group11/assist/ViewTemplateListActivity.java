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
import com.mobdeve.group11.assist.database.Template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//activity for viewing all the templates
public class ViewTemplateListActivity extends AppCompatActivity {

    public static final int NEW_TEMPLATE_ACTIVITY_REQUEST_CODE = 1;

    private AssistViewModel viewModel;

    private RecyclerView rvTemplates;
    private TemplateAdapter templateAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberTemplates;

    //add alphabets headings
    //type 1-alphabet, type 2-name
    List<Template> addAlphabets(List<Template> list) {
        if (list.size() != 0) {
            int i = 0;
            List<Template> customList = new ArrayList<>();
            Template t1 = new Template();
            t1.setTitle(String.valueOf(list.get(0).getTitle().charAt(0)).toUpperCase());
            t1.setType(1);
            customList.add(t1);
            for (i = 0; i < list.size() - 1; i++) {
                Template t2 = new Template();
                char name1 = list.get(i).getTitle().toUpperCase().charAt(0);
                char name2 = list.get(i + 1).getTitle().toUpperCase().charAt(0);
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
        else {
            return list;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_list);

        this.rvTemplates = findViewById(R.id.rv_tlist);
        this.templateAdapter = new TemplateAdapter(ViewTemplateListActivity.this);
        this.rvTemplates.setAdapter(this.templateAdapter);
        this.rvTemplates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //**** define the viewModel
        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        viewModel.getAllTemplates().observe(this, templates -> {
            this.templateAdapter.setDataTemplates(addAlphabets(templates));
            this.tvNumberTemplates = findViewById(R.id.tv_tlist_total);
            int count = templates.size();
            this.tvNumberTemplates.setText(count+" Templates");
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
                Intent intent = new Intent(ViewTemplateListActivity.this, AddTemplateActivity.class);
                startActivityForResult(intent, NEW_TEMPLATE_ACTIVITY_REQUEST_CODE);
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
