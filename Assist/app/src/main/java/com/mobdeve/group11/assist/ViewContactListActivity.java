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

public class ViewContactListActivity extends AppCompatActivity {

    private ArrayList<Contact> dataContacts;
    private RecyclerView rvContacts;
    private ContactAdapter contactAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberContacts;

    //sorted in alphabetical order
    private ArrayList<Contact> sortList(ArrayList<Contact> list) {
        Collections.sort(list, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                int ctr = c1.getLName().compareTo(c2.getLName());
                if (ctr != 0){
                    return ctr;
                }
                else {
                    return c1.getFName().compareTo(c2.getFName());
                }
            }
        });
        return list;
    }

    //add alphabets
    //1-alphabet
    //2-name
    ArrayList<Contact> addAlphabets(ArrayList<Contact> list) {
        int i = 0;
        ArrayList<Contact> customList = new ArrayList<Contact>();
        Contact c1 = new Contact();
        c1.setLName(String.valueOf(list.get(0).getLName().charAt(0)));
        c1.setType(1);
        customList.add(c1);
        for (i = 0; i < list.size() - 1; i++) {
            Contact c2 = new Contact();
            char name1 = list.get(i).getLName().charAt(0);
            char name2 = list.get(i + 1).getLName().charAt(0);
            if (name1 == name2) {
                list.get(i).setType(2);
                customList.add(list.get(i));
            } else {
                list.get(i).setType(2);
                customList.add(list.get(i));
                c2.setLName(String.valueOf(name2));
                c2.setType(1);
                customList.add(c2);
            }
        }
        list.get(i).setType(2);
        customList.add(list.get(i));
        return customList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_view_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_view_left);
        this.tvNumberContacts = findViewById(R.id.tv_view_clist_total);

        this.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContactListActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

        this.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(){
        DataHelper helper = new DataHelper();
        this.dataContacts = helper.initializeContacts();

        int count = dataContacts.size();
        this.tvNumberContacts.setText(count+" Contacts");

        this.dataContacts = sortList(dataContacts);
        this.dataContacts = addAlphabets(dataContacts);

        this.rvContacts = findViewById(R.id.rv_view_clist);
        this.rvContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.contactAdapter = new ContactAdapter(this.dataContacts);
        this.rvContacts.setAdapter(this.contactAdapter);
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
        this.initRecyclerView();
    }
}
