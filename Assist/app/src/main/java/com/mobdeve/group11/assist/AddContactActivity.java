package com.mobdeve.group11.assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AddContactActivity extends AppCompatActivity  {

    private ImageView ivPic, ivCancel, ivDone;
    private TextView tvPic, tvGroups, tvHead;
    private EditText etFName, etLName, etPNum, etGuardian;

    /*private boolean[] selectedGroups;
    private ArrayList<Integer> groupList = new ArrayList<>();
    private DataHelper helper;*/

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
        setContentView(R.layout.activity_add_contact);
    }

    private void initComponents(){
        verifyStoragePermissions(AddContactActivity.this);
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);
        this.ivPic = findViewById(R.id.iv_add_contact_picture);
        this.tvPic = findViewById(R.id.tv_add_contact_add_pic);
        this.etFName = findViewById(R.id.et_add_contact_fname);
        this.etLName = findViewById(R.id.et_add_contact_lname);
        this.etPNum = findViewById(R.id.et_add_contact_number);
        this.etGuardian = findViewById(R.id.et_add_contact_guardian);

        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);
        this.tvHead.setText("Add Contact");

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

            String fName = etFName.getText().toString();
            String lName = etLName.getText().toString();
            String pNum = etPNum.getText().toString();
            String guardian = etGuardian.getText().toString();

            if (fName.length() > 0 && lName.length() > 0 && pNum.length() > 0){

                intent.putExtra(ContactInfo.FIRST_NAME.name(), fName);
                intent.putExtra(ContactInfo.LAST_NAME.name(), lName);
                intent.putExtra(ContactInfo.PHONE_NUMBER.name(), pNum);
                intent.putExtra(ContactInfo.GUARDIAN.name(), guardian);
                setResult(Activity.RESULT_OK,intent);
            }
            else{
                setResult(RESULT_CANCELED, intent);
            }
            finish();
        });

        this.tvPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    /*private void initGroupsDropDown(){
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

                            /*selectedGroups[which] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(which, false);
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
    }*/

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
        //this.initGroupsDropDown();
    }

    private void selectImage(){
        final CharSequence[] options = { "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
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