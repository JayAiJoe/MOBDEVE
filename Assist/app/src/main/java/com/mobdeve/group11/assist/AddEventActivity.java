package com.mobdeve.group11.assist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    Button btnAddGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        btnAddGroups = findViewById(R.id.btn_add_groups);
        btnAddGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);

                //sample choices
                String[] groupsArray = getResources().getStringArray(R.array.sample_groups);
                boolean[] checkedGroups = {true, true};

                final List<String> groupList = Arrays.asList(groupsArray);
                final List<String> selectedGroups = new ArrayList<String>();

                builder.setTitle("Select groups");

                builder.setMultiChoiceItems(groupsArray, checkedGroups,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedGroups.add(groupList.get(which));
                                } else {
                                    selectedGroups.remove(groupList.get(which));
                                }
                            }
                        });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click positive button
                            }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }
}
