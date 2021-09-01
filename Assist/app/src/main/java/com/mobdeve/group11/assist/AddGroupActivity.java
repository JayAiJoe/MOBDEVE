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
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
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
        verifyStoragePermissions(AddGroupActivity.this);
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

        this.tvPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
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

    private void selectImage(){
        final CharSequence[] options = { "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddGroupActivity.this);
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













