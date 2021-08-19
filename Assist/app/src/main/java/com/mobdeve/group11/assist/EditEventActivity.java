package com.mobdeve.group11.assist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    Button btnEditGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        btnEditGroups = findViewById(R.id.btn_edit_groups);
        btnEditGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditEventActivity.this);

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
