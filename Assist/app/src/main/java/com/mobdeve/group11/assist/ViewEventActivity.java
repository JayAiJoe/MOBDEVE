package com.mobdeve.group11.assist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import com.mobdeve.group11.assist.database.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewEventActivity extends AppCompatActivity {

    private AssistViewModel viewModel;

    private ImageView ivBackDay, ivEdit, ivEmpty;
    private TextView tvName, tvDate, tvTime, tvTemplate, tvRemind, tvGroups, tvHead, tvEmpty, tvGroupsTitle;
    private Activity activity = ViewEventActivity.this;
    private  Button btnDelete;
    private ListView lvGroups;

    private List<ContactGroup> groupList = new ArrayList<ContactGroup>();
    private List<Integer> ids = new ArrayList<Integer>();
    private ArrayAdapter<String> adapter;
    private Event event;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);

        initInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initInfo(){
        this.tvName = findViewById(R.id.tv_view_event_name);
        this.tvDate = findViewById(R.id.tv_view_date);
        this.tvTime = findViewById(R.id.tv_view_time);
        this.tvTemplate = findViewById(R.id.tv_view_template);
        this.tvRemind = findViewById(R.id.tv_view_reminder);
        this.tvHead = findViewById(R.id.tv_toolbar_view_title);
        this.lvGroups = findViewById(R.id.lv_event_view);

        this.tvGroupsTitle = findViewById(R.id.tv_groups_event);
        this.ivEmpty = findViewById(R.id.iv_view_event_empty);
        this.tvEmpty = findViewById(R.id.tv_view_event_empty);

        Intent intent = getIntent();
        Integer eId = intent.getIntExtra(GroupInfo.ID.name(), 1);

        viewModel.getEventById(eId).observe(this, curr_event -> {
            this.event = curr_event;
            if(event != null){
                this.tvName.setText(event.getTitle());
                this.tvDate.setText(event.getDate().getMonth() + " " + event.getDate().getDayOfMonth() + ", " + event.getDate().getYear());
                this.tvTime.setText(event.getTimeStart().toString()+" - "+event.getTimeEnd().toString());
                this.tvTemplate.setText("None");

                viewModel.getTemplateById(event.getTemplateId()).observe(ViewEventActivity.this, template -> {
                    if(template != null)
                        tvTemplate.setText(template.getTitle());
                });


                this.tvRemind.setText(AppUtils.getReminderText(event.getReminder()));

                this.tvHead.setText("Event");
            }
        });

        viewModel.getGroupIdsInEvent(eId).observe(this, ids -> {
            this.ids = ids;
            viewModel.getManyCGroupsById(ids).observe(this, groups -> {
                this.groupList = groups;
                if (this.groupList.size() == 0) {
                    ivEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvGroupsTitle.setVisibility(View.GONE);
                }
                else {
                    adapter = new ArrayAdapter<String>(this, R.layout.listview_item, new ArrayList<String>(Arrays.asList(AppUtils.getGroupNames(groupList))));
                    lvGroups.setAdapter(adapter);
                    ivEmpty.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.GONE);
                    tvGroupsTitle.setVisibility(View.VISIBLE);
                }

            });

        });


    }

    private void initComponents(){
        ivBackDay = findViewById(R.id.iv_toolbar_view_left);
        ivEdit = findViewById(R.id.iv_toolbar_view_right);
        btnDelete = findViewById(R.id.btn_view_event_delete);

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditEventActivity.class);

                intent.putExtra(EventInfo.ID.name(), event.getId());
                activity.startActivityForResult(intent, 1);
            }
        });

        this.ivBackDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        this.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete alarm for old message

                for (int i = 0; i < groupList.size(); i++) {
                    viewModel.getContactIdsInGroup(groupList.get(i).getId()).observe(ViewEventActivity.this, contacts -> {
                        for (int j = 0; j < contacts.size(); j++) {
                            int aid = j;
                            viewModel.getContactById(contacts.get(j)).observe(ViewEventActivity.this, contact -> {
                                deleteAlarm(event.getId()*100+aid, event.getTitle());
                            });
                        }
                    });
                }

                viewModel.deleteEvent(event);
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }

    //delete associated alarm when an event is deleted
    public void deleteAlarm(int id, String name){
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent =  PendingIntent.getBroadcast(this.getApplicationContext(), id, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pIntent);
        Toast.makeText(getApplication(), "Alarm for event " + name + " removed", Toast.LENGTH_SHORT).show();
    }

}