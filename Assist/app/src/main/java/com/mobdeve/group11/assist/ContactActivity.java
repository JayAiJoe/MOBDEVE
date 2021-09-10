package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;
import com.mobdeve.group11.assist.database.GroupMembership;
import com.mobdeve.group11.assist.database.ThumbnailImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST = 1;

    private AssistViewModel viewModel;

    private ImageView ivBack, ivEdit, ivPic, ivEmpty;
    private TextView tvName, tvPNum, tvGuardian, tvHead, tvEmpty, tvGroupsTitle;
    private Button btnDelete;
    private ListView lvGroups;

    private Contact contact = new Contact("","","","");
    private List<Integer> ids = new ArrayList<Integer>();
    private Activity activity = ContactActivity.this;

    private List<ContactGroup> groupList = new ArrayList<ContactGroup>();
    private ArrayAdapter<String> adapter;

    private ThumbnailImage thumbnail;


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
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        this.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditContactActivity.class);
                intent.putExtra(ContactInfo.ID.name(), contact.getId());
                activity.startActivityForResult(intent, EDIT_REQUEST);
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from database
                if(thumbnail != null) {
                    viewModel.deleteThumbnail(thumbnail);
                }
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
        this.lvGroups = findViewById(R.id.lv_groups);

        this.tvGroupsTitle = findViewById(R.id.tv_groups_title);
        this.ivEmpty = findViewById(R.id.iv_view_contact_empty);
        this.tvEmpty = findViewById(R.id.tv_view_contact_empty);

        Intent intent = getIntent();
        Integer cId = intent.getIntExtra(ContactInfo.ID.name(), 0);

        viewModel.getContactById(cId).observe(this, curr_contact -> {
            this.contact = curr_contact;
            if(curr_contact != null) {
                this.tvName.setText(contact.getFirstName() + " " + contact.getLastName());
                this.tvPNum.setText(contact.getContactNumber());
                this.tvGuardian.setText(contact.getGuardian());

                viewModel.getThumbnailById(curr_contact.getThumbnailId()).observe(this, thumbnail ->{
                    this.thumbnail = thumbnail;
                    if(thumbnail != null)
                        this.ivPic.setImageBitmap(thumbnail.getImage());
                });
            }
        });

        viewModel.getGroupIdsOfContact(cId).observe(this, ids -> {
            this.ids = ids;
            viewModel.getManyCGroupsById(ids).observe(this, groups -> {
                this.groupList = groups;
                if (this.groupList.size() == 0) {
                    ivEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvGroupsTitle.setVisibility(View.GONE);
                }
                else {
                    adapter = new ArrayAdapter<String>(this, R.layout.listview_item, new ArrayList<String>(Arrays.asList(getNames(groupList))));
                    lvGroups.setAdapter(adapter);
                    ivEmpty.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.GONE);
                    tvGroupsTitle.setVisibility(View.VISIBLE);
                }

            });

        });

        this.tvHead.setText("Contacts");

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            /*
            contact.setFirstName(data.getStringExtra(ContactInfo.FIRST_NAME.name()));
            contact.setLastName(data.getStringExtra(ContactInfo.LAST_NAME.name()));
            contact.setContactNumber(data.getStringExtra(ContactInfo.PHONE_NUMBER.name()));
            contact.setGuardian(data.getStringExtra(ContactInfo.GUARDIAN.name()));
            viewModel.updateContact(contact);
             */
        }
    }

    private String[] getNames(List<ContactGroup> gList){
        String[] strArray = new String[gList.size()];
        for(int i=0; i<gList.size();i++)
        {
            strArray[i] = gList.get(i).getName();
        }
        return strArray;
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }
}
