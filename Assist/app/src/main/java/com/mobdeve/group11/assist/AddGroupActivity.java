package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {

    private ImageView ivPic, ivCancel, ivDone;
    private TextView tvHead, tvPic, tvMembers;
    private EditText etName;
    private ListView lvMembers;

    private boolean[] selectedMembers;
    private ArrayList<Integer> memberList = new ArrayList<>();
    private DataHelper helper;


    private AssistViewModel viewModel;
    private List<Contact> contactList;
    private boolean[] checkedContacts = new boolean[0];
    private final List<Contact> selectedContacts = new ArrayList<Contact>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        this.tvMembers = findViewById(R.id.tv_add_group_members);
        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        //****add observers to always get updated data
        viewModel.getAllContacts().observe(this, contacts -> {
            contactList = contacts;
            checkedContacts = new boolean[contactList.size()];
        });


    }

    private void initComponents() {
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.ivPic = findViewById(R.id.iv_add_group_picture);
        this.tvPic = findViewById(R.id.tv_add_group_add_pic);
        this.etName = findViewById(R.id.et_add_group_gname);
        this.lvMembers = findViewById(R.id.lv_add_group);

        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);
        this.tvHead.setText("Add Group");

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
                setResult(RESULT_CANCELED, intent);
            }
            finish();
        });

        adapter = new ArrayAdapter<String>(this, R.layout.listview_item, new ArrayList<String>(Arrays.asList(getNames(selectedContacts))));
        lvMembers.setAdapter(adapter);

    }

    private void initGroupsDropDown() {
        this.helper = new DataHelper();
        ArrayList<UIContact> data = helper.initializeContacts();
        data = this.sortList(data);
        ArrayList<String> dataMembers = new ArrayList<>();
        for(int ctr=0; ctr < data.size(); ctr++){
            dataMembers.add(ctr, data.get(ctr).getFName() + " " + data.get(ctr).getLName());
        }

        String[] members = new String[dataMembers.size()];
        members = dataMembers.toArray(members);

        this.tvMembers = findViewById(R.id.tv_add_group_members);
        selectedMembers = new boolean[dataMembers.size()];

        String[] finalMembers = members;
        tvMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AddGroupActivity.this
                );
                //set title
                builder.setTitle("Select Members (Note: once clicked, cannot undo individually)");

                //set dialog non cancelable
                //builder.setCancelable(false);

                builder.setMultiChoiceItems(finalMembers, selectedMembers, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //check condition
                        if (isChecked){
                            //add position in memberList
                            memberList.add(which);
                            //sort memberList
                            Collections.sort(memberList);
                        }
                        else{
                            //remove position from memberList
                            memberList.remove(which);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        //use for loop
                        for (int j=0; j<memberList.size();j++){
                            //concat array values
                            stringBuilder.append(finalMembers[memberList.get(j)]);
                            //check condition
                            if (j != memberList.size()-1){
                                //add comma
                                stringBuilder.append(", ");
                            }
                        }

                        //set text on text view
                        tvMembers.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss dialog
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //use for loop
                        for (int j=0; j< selectedMembers.length; j++){
                            //remove all selection
                            selectedMembers[j] = false;
                            //clear grouplist
                            memberList.clear();
                            //clear textview value
                            tvMembers.setText("");
                        }
                    }
                });

                //show dialog
                builder.show();
            }
        });

    }

    private void setButtons(){
        tvMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddGroupActivity.this);

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

    //sorted in alphabetical order
    private ArrayList<UIContact> sortList(ArrayList<UIContact> list) {
        Collections.sort(list, new Comparator<UIContact>() {
            @Override
            public int compare(UIContact c1, UIContact c2) {
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

    private String[] getNames(List<Contact> cList){
        String[] strArray = new String[cList.size()];
        for(int i=0; i<cList.size();i++)
        {
            strArray[i] = cList.get(i).getFirstName() + " " + cList.get(i).getLastName();
        }
        return strArray;
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
        this.initComponents();
        setButtons();
    }

}













