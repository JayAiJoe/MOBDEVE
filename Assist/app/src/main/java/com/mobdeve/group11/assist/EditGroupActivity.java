package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditGroupActivity extends AppCompatActivity {

    private ImageView ivCancel, ivDone, ivPic;
    private TextView tvPic, tvMembers, tvHead;
    private EditText etName;
    private ListView lvMembers;

    private AssistViewModel viewModel;
    private List<Contact> contactList;
    private boolean[] checkedContacts = new boolean[0];
    private final List<Contact> selectedContacts = new ArrayList<Contact>();
    private ArrayAdapter<String> adapter;

    private Activity activity = EditGroupActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        //****add observers to always get updated data
        viewModel.getAllContacts().observe(this, contacts -> {
            contactList = contacts;
            checkedContacts = new boolean[contactList.size()];

            Integer id = getIntent().getIntExtra(GroupInfo.ID.name(), 0);

            viewModel.getContactIdsInGroup(id).observe(this, contactsInGroup->{

                for(int i=0; i<contactList.size();i++){
                    if(contactsInGroup.contains(contactList.get(i).getId())) {
                        checkedContacts[i] = true;
                        selectedContacts.add(contactList.get(i));
                    }
                }

                adapter.clear();
                String[] names = getNames(selectedContacts);
                Arrays.sort(names);
                adapter.addAll(names);
            });
        });
    }

    private void initComponents() {
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.lvMembers = findViewById(R.id.lv_edit_group);
        this.tvMembers = findViewById(R.id.tv_add_group_members);

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });


        this.ivDone.setOnClickListener(view -> {
            Intent intent = new Intent();
            String name = etName.getText().toString();

            if (name.length() > 0) {
                intent.putExtra(GroupInfo.NAME.name(), name);
                intent.putExtra(GroupInfo.MEMBERS.name(), getIds(selectedContacts));
                setResult(Activity.RESULT_OK, intent);
            }
            else{
                setResult(Activity.RESULT_CANCELED, intent);
            }
            finish();
        });

        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, new ArrayList<String>(Arrays.asList(getNames(selectedContacts))));
        lvMembers.setAdapter(adapter);
    }

    private String[] getNames(List<Contact> cList){
        String[] strArray = new String[cList.size()];
        for(int i=0; i<cList.size();i++)
        {
            strArray[i] = cList.get(i).getFirstName() + " " + cList.get(i).getLastName();
        }
        return strArray;
    }

    private void initInfo() {
        this.ivPic = findViewById(R.id.iv_edit_group_picture);
        this.tvPic = findViewById(R.id.tv_edit_group_add_pic);
        this.etName = findViewById(R.id.et_edit_group_gname);
        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);

        if(getIntent().hasExtra(GroupInfo.NAME.name())) {
            //photo
            this.etName.setText(getIntent().getStringExtra(GroupInfo.NAME.name()));

            this.tvHead.setText("Edit Group");

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtons(){

        tvMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(EditGroupActivity.this);

                builder.setTitle("Select members");

                builder.setMultiChoiceItems(getNames(contactList), checkedContacts,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedContacts.add(contactList.get(which));
                                } else {
                                    selectedContacts.remove(contactList.get(which));
                                }
                            }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //displayGroups(getNames(selectedGroups), cgAddGroups);
                        adapter.clear();
                        String[] names = getNames(selectedContacts);
                        Arrays.sort(names);
                        adapter.addAll(names);
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something
                    }
                });

                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private ArrayList<Integer> getIds(List<Contact> cList){
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        for(int i=0; i<cList.size();i++)
        {
            idArray.add(cList.get(i).getId());
        }
        return idArray;
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
        this.setButtons();
    }

}
