package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
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

public class GroupActivity extends AppCompatActivity{

    public static final int EDIT_REQUEST = 1;

    private AssistViewModel viewModel;

    private ImageView ivBack, ivEdit, ivPic, ivEmpty;
    private TextView tvName, tvHead, tvEmpty, tvMembersTitle;
    private Button btnDelete;
    private ListView lvMembers;

    private ContactGroup group = new ContactGroup("");
    private List<Integer> ids = new ArrayList<Integer>();
    private Activity activity = GroupActivity.this;

    private List<Contact> contactList = new ArrayList<Contact>();
    private ArrayAdapter<String> adapter;

    private ThumbnailImage thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);
    }

    private void initComponents() {
        this.ivBack = findViewById(R.id.iv_toolbar_view_left);
        this.ivEdit = findViewById(R.id.iv_toolbar_view_right);
        this.btnDelete = findViewById(R.id.btn_view_group_delete);


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

                Intent intent = new Intent(v.getContext(), EditGroupActivity.class);
                intent.putExtra(GroupInfo.ID.name(), group.getId());
                activity.startActivityForResult(intent, EDIT_REQUEST);
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thumbnail != null) {
                    viewModel.deleteThumbnail(thumbnail);
                }
                viewModel.deleteGroup(group);
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void initInfo(){
        this.ivPic = findViewById(R.id.iv_view_group_picture);
        this.tvName = findViewById(R.id.tv_view_group_name);
        this.tvHead = findViewById(R.id.tv_toolbar_view_title);
        this.lvMembers = findViewById(R.id.lv_group);

        this.tvMembersTitle = findViewById(R.id.tv_members_title);
        this.ivEmpty = findViewById(R.id.iv_view_group_empty);
        this.tvEmpty = findViewById(R.id.tv_view_group_empty);

        Intent intent = getIntent();
        viewModel.getGroupById(intent.getIntExtra(GroupInfo.ID.name(), 0)).observe(this, curr_group -> {
            this.group = curr_group;
            if(curr_group != null){
                this.tvName.setText(group.getName());
                viewModel.getThumbnailById(curr_group.getThumbnailId()).observe(this, thumbnail ->{
                    this.thumbnail = thumbnail;
                    if(thumbnail != null)
                        this.ivPic.setImageBitmap(thumbnail.getImage());
                });
            }

        });

        viewModel.getContactIdsInGroup(intent.getIntExtra(GroupInfo.ID.name(), 0)).observe(this, ids -> {
            this.ids = ids;
            viewModel.getManyContactsById(ids).observe(this, contacts -> {
                this.contactList = contacts;
                if (this.contactList.size() == 0) {
                    ivEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvMembersTitle.setVisibility(View.GONE);
                }
                else {
                    ivEmpty.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.GONE);
                    tvMembersTitle.setVisibility(View.VISIBLE);
                }
                adapter = new ArrayAdapter<String>(this, R.layout.listview_item, new ArrayList<String>(Arrays.asList(getNames(contactList))));
                lvMembers.setAdapter(adapter);

            });

        });

        this.tvHead.setText("Group");
    }

    private String[] getNames(List<Contact> cList){
        String[] strArray = new String[cList.size()];
        for(int i=0; i<cList.size();i++)
        {
            strArray[i] = cList.get(i).getFirstName() + " " + cList.get(i).getLastName();
        }
        return strArray;
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

}
