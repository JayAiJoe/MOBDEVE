package com.mobdeve.group11.assist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ContactGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//activity for instantly sending sms
public class QuickTextActivity extends AppCompatActivity {

    private AssistViewModel viewModel;

    private TextView tvGroups;
    private Button btnSend;
    private ImageView ivBack;
    private EditText etText;

    private final List<ContactGroup> selectedGroups = new ArrayList<ContactGroup>();
    private List<ContactGroup> groupList;
    private boolean[] checkedGroups = new boolean[0];
    private String currentMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_text);

        tvGroups = findViewById(R.id.tv_quick_groups);
        btnSend = findViewById(R.id.btn_quick_send);
        ivBack = findViewById(R.id.iv_toolbar_view_left);
        etText = findViewById(R.id.et_quick_notes);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        viewModel.getAllGroups().observe(this, groups -> {
            groupList = groups;
            checkedGroups = new boolean[groups.size()];
        });

        tvGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuickTextActivity.this);



                builder.setTitle("Select groups");

                builder.setMultiChoiceItems(AppUtils.getGroupNames(groupList), checkedGroups,
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
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedGroups.size() > 0) {
                    currentMessage = etText.getText().toString().trim();
                    if(currentMessage.length() > 0) {
                        if (ContextCompat.checkSelfPermission(QuickTextActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(QuickTextActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                        }
                        else {
                            for (int i = 0; i < selectedGroups.size(); i++) {
                                viewModel.getContactIdsInGroup(selectedGroups.get(i).getId()).observe(QuickTextActivity.this, contacts -> {
                                    for (int j = 0; j < contacts.size(); j++) {
                                        viewModel.getContactById(contacts.get(j)).observe(QuickTextActivity.this, contact -> {
                                            sendSMS(contact.getContactNumber(), currentMessage);
                                        });
                                    }
                                });
                            }
                            etText.setText("");
                            selectedGroups.clear();
                        }
                    }
                    else{
                        Toast.makeText(getBaseContext(), "No text entered", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getBaseContext(), "No groups selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    //directly send a message to the given phone number
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //when the SMS has been sent
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //when the SMS has been delivered
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

}