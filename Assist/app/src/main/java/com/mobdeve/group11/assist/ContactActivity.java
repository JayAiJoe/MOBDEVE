package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST = 1;

    private ImageView ivBack, ivEdit, ivPic;
    private TextView tvName, tvPNum, tvGuardian, tvGroups, tvHead;
    private Button btnDelete;
    private ListView lvGroups;

    private String fName, lName, pNum, guardian;
    private Activity activity = ContactActivity.this;

    private AssistViewModel viewModel;

    private Contact contact = new Contact("","","","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);
    }

    private void initComponents(){
        this.ivBack = findViewById(R.id.iv_toolbar_view_left);
        this.ivEdit = findViewById(R.id.iv_toolbar_view_right);
        this.btnDelete = findViewById(R.id.btn_view_contact_delete);

        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewContactListActivity.class);
                startActivity(intent);
            }
        });

        this.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), EditContactActivity.class);

                intent.putExtra(ContactInfo.FIRST_NAME.name(), contact.getFirstName());
                intent.putExtra(ContactInfo.LAST_NAME.name(), contact.getLastName());
                intent.putExtra(ContactInfo.PHONE_NUMBER.name(), contact.getContactNumber());
                intent.putExtra(ContactInfo.GUARDIAN.name(), contact.getGuardian());

                activity.startActivityForResult(intent, EDIT_REQUEST);
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from database
                viewModel.deleteContact(contact);
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void initInfo(){
        this.ivPic = findViewById(R.id.iv_view_contact_pic);
        this.tvName = findViewById(R.id.tv_view_contact_name);
        this.tvPNum = findViewById(R.id.tv_view_contact_number);
        this.tvGuardian = findViewById(R.id.tv_view_contact_guardian);
        this.tvHead = findViewById(R.id.tv_toolbar_view_title);

        Intent intent = getIntent();

        //photo
        this.fName = intent.getStringExtra(ContactInfo.FIRST_NAME.name());
        this.lName = intent.getStringExtra(ContactInfo.LAST_NAME.name());
        this.pNum = intent.getStringExtra(ContactInfo.PHONE_NUMBER.name());
        this.guardian = intent.getStringExtra(ContactInfo.GUARDIAN.name());
        //ArrayList<String> sGroups = new ArrayList<String>(Arrays.asList(tvGroups.getText().toString().split(",")));

        //groups

        this.tvHead.setText("Contacts");

        viewModel.getContactById(intent.getIntExtra(GroupInfo.ID.name(), 0)).observe(this, curr_contact -> {
            this.contact = curr_contact;
            if(curr_contact != null){
                this.tvName.setText(contact.getFirstName()+" "+contact.getLastName());
                this.tvPNum.setText(contact.getContactNumber());
                this.tvGuardian.setText(contact.getGuardian());
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            contact.setFirstName(data.getStringExtra(ContactInfo.FIRST_NAME.name()));
            contact.setLastName(data.getStringExtra(ContactInfo.LAST_NAME.name()));
            contact.setContactNumber(data.getStringExtra(ContactInfo.PHONE_NUMBER.name()));
            contact.setGuardian(data.getStringExtra(ContactInfo.GUARDIAN.name()));
            viewModel.updateContact(contact);
        }
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }
}
