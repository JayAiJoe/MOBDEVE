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

//implements AdapterView.OnItemSelectedListener
public class AddContactActivity extends AppCompatActivity  {

    /* todos
    add pic
     */

    private ImageView ivPic, ivCancel, ivDone;
    private TextView tvPic, tvGroups;
    private EditText etFName, etLName, etPNum, etGuardian;

    private boolean[] selectedGroups;
    private ArrayList<Integer> groupList = new ArrayList<>();
    private DataHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    private void initComponents(){
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.ivPic = findViewById(R.id.iv_add_contact_picture);
        this.tvPic = findViewById(R.id.tv_add_contact_add_pic);
        this.etFName = findViewById(R.id.et_add_contact_fname);
        this.etLName = findViewById(R.id.et_add_contact_lname);
        this.etPNum = findViewById(R.id.et_add_contact_number);
        this.etGuardian = findViewById(R.id.et_add_contact_guardian);

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
                //photo
                String fName = etFName.getText().toString();
                String lName = etLName.getText().toString();
                String pNum = etPNum.getText().toString();
                String guardian = etGuardian.getText().toString();
                ArrayList<String> sGroups = new ArrayList<String>(Arrays.asList(tvGroups.getText().toString().split(",")));

                if (fName.length() > 0 && lName.length() > 0 && pNum.length() > 0){

                    Intent intent = new Intent();
                    //add photo to database
                    intent.putExtra(ContactInfo.FIRST_NAME.name(), fName);
                    intent.putExtra(ContactInfo.LAST_NAME.name(), lName);
                    intent.putExtra(ContactInfo.PHONE_NUMBER.name(), pNum);
                    intent.putExtra(ContactInfo.GUARDIAN.name(), guardian);
                    //add as member to the groups in database

                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(),
                            "You have not yet entered in all of the required fields!",
                            Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

    }

    private void initGroupsDropDown(){
        this.helper = new DataHelper ();
        ArrayList <Group> data = helper.initializeGroups();
        data = this.sortList(data);
        ArrayList<String> dataGroups = new ArrayList<>();
        for(int ctr=0; ctr < data.size(); ctr++){
            dataGroups.add(ctr, data.get(ctr).getName());
        }

        String[] groups = new String[dataGroups.size()];
        groups = dataGroups.toArray(groups);

        this.tvGroups = findViewById(R.id.tv_add_contact_groups);
        selectedGroups = new boolean[dataGroups.size()];

        String[] finalGroups = groups;
        tvGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AddContactActivity.this
                );
                //set title
                builder.setTitle("Select Groups (Note: once clicked, cannot undo individually)");

                //set dialog non cancelable
                //builder.setCancelable(false);

                builder.setMultiChoiceItems(finalGroups, selectedGroups, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //check condition
                        if (isChecked){
                            //add position in groupList
                            groupList.add(which);
                            //sort groupList
                            Collections.sort(groupList);
                        }
                        else{
                            //remove position from grouplist
                            groupList.remove(which);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        //use for loop
                        for (int j=0; j<groupList.size();j++){
                            //concat array values
                            stringBuilder.append(finalGroups[groupList.get(j)]);
                            //check condition
                            if (j != groupList.size()-1){
                                //add comma
                                stringBuilder.append(", ");
                            }
                        }

                        //set text on text view
                        tvGroups.setText(stringBuilder.toString());
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
                        for (int j=0; j< selectedGroups.length; j++){
                            //remove all selection
                            selectedGroups[j] = false;
                            //clear grouplist
                            groupList.clear();
                            //clear textview value
                            tvGroups.setText("");
                        }
                    }
                });

                //show dialog
                builder.show();
            }
        });
    }

    //sort alphabetically
    private ArrayList<Group> sortList(ArrayList<Group> list) {
        Collections.sort(list, new Comparator<Group>() {
            @Override
            public int compare(Group c1, Group c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return list;
    }

    public void onResume() {
        super.onResume();
        this.initComponents();
        this.initGroupsDropDown();
    }

}