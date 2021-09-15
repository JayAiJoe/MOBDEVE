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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewContactListActivity extends AppCompatActivity {

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    private AssistViewModel viewModel;

    private RecyclerView rvContacts;
    private ContactAdapter contactAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberContacts;


    //add alphabets
    //1-alphabet
    //2-name
    List <Contact> addAlphabets(List<Contact> list) {
        if (list.size() != 0){
            int i = 0;
            List<Contact> customList = new ArrayList<>();
            Contact c1 = new Contact();
            c1.setLastName(String.valueOf(list.get(0).getLastName().charAt(0)).toUpperCase());
            c1.setType(1);
            customList.add(c1);
            for (i = 0; i < list.size() - 1; i++) {
                Contact c2 = new Contact();
                char name1 = list.get(i).getLastName().toUpperCase().charAt(0);
                char name2 = list.get(i + 1).getLastName().toUpperCase().charAt(0);
                if (name1 == name2) {
                    list.get(i).setType(2);
                    customList.add(list.get(i));
                } else {
                    list.get(i).setType(2);
                    customList.add(list.get(i));
                    c2.setLastName(String.valueOf(name2));
                    c2.setType(1);
                    customList.add(c2);
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
        setContentView(R.layout.activity_contacts_list);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        this.rvContacts = findViewById(R.id.rv_view_clist);
        this.rvContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.contactAdapter = new ContactAdapter(ViewContactListActivity.this);
        this.rvContacts.setAdapter(this.contactAdapter);

        viewModel.getAllContacts().observe(this, contacts -> {
            this.contactAdapter.setDataContacts(addAlphabets(contacts));
            this.tvNumberContacts = findViewById(R.id.tv_view_clist_total);
            int count = contacts.size();
            this.tvNumberContacts.setText(count+" Contacts");
        });
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_left);

        this.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContactListActivity.this, AddContactActivity.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
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

    public void onResume() {
        super.onResume();
        this.initComponents();
    }
}
