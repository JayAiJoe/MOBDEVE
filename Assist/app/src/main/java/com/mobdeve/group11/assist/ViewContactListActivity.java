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

public class ViewContactListActivity extends AppCompatActivity {

    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    private AssistViewModel viewModel;

    private ArrayList<Contact> dataContacts = new ArrayList<Contact>();;
    private RecyclerView rvContacts;
    private ContactAdapter contactAdapter;

    private ImageView ivAdd, ivMenu;
    private TextView tvNumberContacts;

    private ActivityResultLauncher myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    String fName = intent.getStringExtra(ContactInfo.FIRST_NAME.name());
                    String lName = intent.getStringExtra(ContactInfo.LAST_NAME.name());
                    String pNum = intent.getStringExtra(ContactInfo.PHONE_NUMBER.name());
                    String guardian = intent.getStringExtra(ContactInfo.GUARDIAN.name());

                    //dataContacts.add(0, new Contact(fName, lName, pNum, guardian));
                    viewModel.addContact(new Contact(fName, lName, pNum, guardian));
                }
            }
    );

    /*
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
     */

    //add alphabets
    //1-alphabet
    //2-name
    /*
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
     */


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
            this.contactAdapter.setDataContacts(contacts);
        });
    }

    private void initComponents(){
        this.ivAdd = findViewById(R.id.iv_toolbar_right);
        this.ivMenu = findViewById(R.id.iv_toolbar_left);
        this.tvNumberContacts = findViewById(R.id.tv_view_clist_total);

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
                Intent intent = new Intent(ViewContactListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Contact c = new Contact(data.getStringExtra(ContactInfo.FIRST_NAME.name()),
                    data.getStringExtra(ContactInfo.LAST_NAME.name()),
                    data.getStringExtra(ContactInfo.PHONE_NUMBER.name()),
                    data.getStringExtra(ContactInfo.GUARDIAN.name()));
            viewModel.addContact(c);
        } else {
            Toast.makeText(getApplicationContext(), R.string.not_saved, Toast.LENGTH_LONG).show();
        }
    }

    private void initRecyclerView(){
        /*
        DataHelper helper = new DataHelper();
        if (this.dataContacts.size() == 0){
            this.dataContacts = helper.initializeContacts();
        }

        int count = dataContacts.size();
        this.tvNumberContacts.setText(count+" Contacts");

        ArrayList<Contact> contacts = new ArrayList<Contact>();

        this.dataContacts = sortList(dataContacts);
        contacts = addAlphabets(dataContacts);

        this.rvContacts = findViewById(R.id.rv_view_clist);
        this.rvContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.contactAdapter = new ContactAdapter(contacts, ViewContactListActivity.this);
        this.rvContacts.setAdapter(this.contactAdapter);
         */
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
        //this.initRecyclerView();
    }
}
