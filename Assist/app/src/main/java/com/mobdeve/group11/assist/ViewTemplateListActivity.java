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
import com.mobdeve.group11.assist.database.Template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewTemplateListActivity extends AppCompatActivity {

    public static final int NEW_TEMPLATE_ACTIVITY_REQUEST_CODE = 1;

    private AssistViewModel viewModel;

    private ArrayList<UITemplate> dataTemplates = new ArrayList<UITemplate>();
    private RecyclerView rvTemplates;
    private TemplateAdapter templateAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberTemplates;

    //sorted in alphabetical order
    private ArrayList<UITemplate> sortList(ArrayList<UITemplate> list) {
        Collections.sort(list, new Comparator<UITemplate>() {
            @Override
            public int compare(UITemplate t1, UITemplate t2) {
                int ctr = t1.getTitle().compareTo(t2.getTitle());
                return ctr;
            }
        });
        return list;
    }

    /*
    //add alphabets
    //1-alphabet
    //2-name
    ArrayList<UITemplate> addAlphabets(ArrayList<UITemplate> list) {
        int i = 0;
        ArrayList<UITemplate> customList = new ArrayList<UITemplate>();
        UITemplate t1 = new UITemplate();
        t1.setTitle(String.valueOf(list.get(0).getTitle().charAt(0)));
        t1.setType(1);
        customList.add(t1);
        for (i = 0; i < list.size() - 1; i++) {
            UITemplate t2 = new UITemplate();
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
*/

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
            this.templateAdapter.setDataTemplates(templates);
        });
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_left);
        this.tvNumberTemplates = findViewById(R.id.tv_tlist_total);

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
                Intent intent = new Intent(ViewTemplateListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    private void initRecyclerView(){
        DataHelper helper = new DataHelper();
        if (this.dataTemplates.size() == 0){
            this.dataTemplates = helper.initializeTemplates();
        }

        int count = dataTemplates.size();
        this.tvNumberTemplates.setText(count+" Templates");

        ArrayList<UITemplate> templates = new ArrayList<UITemplate>();

        //templates = addAlphabets(dataTemplates);

    }

     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TEMPLATE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Template t = new Template(data.getStringExtra(TemplateInfo.TITLE.name()),
                    data.getStringExtra(TemplateInfo.SUBJECT.name()),
                    data.getStringExtra(TemplateInfo.NOTES.name()));
            viewModel.addTemplate(t);
        }
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
        //this.initRecyclerView();
    }
}
