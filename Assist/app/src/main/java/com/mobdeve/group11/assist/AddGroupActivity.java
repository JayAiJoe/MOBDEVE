package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class AddGroupActivity extends AppCompatActivity {
    private ImageView ivPic, ivCancel, ivDone;
    private TextView tvPic, tvMembers;
    private EditText etName;

    private boolean[] selectedMembers;
    private ArrayList<Integer> memberList = new ArrayList<>();
    private DataHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    private void initComponents() {
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.ivPic = findViewById(R.id.iv_add_group_picture);
        this.tvPic = findViewById(R.id.tv_add_group_add_pic);
        this.etName = findViewById(R.id.et_add_group_gname);

        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewContactListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        this.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                ArrayList<String> sMembers = new ArrayList<String>(Arrays.asList(tvMembers.getText().toString().split(",")));

                if (name.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(GroupInfo.NAME.name(), name);

                    //add members

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "You have not yet entered in all of the required fields!",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    private void initGroupsDropDown() {
        this.helper = new DataHelper();
        ArrayList<Contact> data = helper.initializeContacts();
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

    public void onResume() {
        super.onResume();
        this.initComponents();
    }


}













