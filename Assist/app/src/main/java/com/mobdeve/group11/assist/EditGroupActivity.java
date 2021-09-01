package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
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

    private String Document_img1="";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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
        verifyStoragePermissions(activity);
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

        this.tvPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
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

    private void selectImage(){
        final CharSequence[] options = { "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data.getData()!=null) {
            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                Log.w("path of image from gallery......******************.........", picturePath+"");
                ivPic.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
