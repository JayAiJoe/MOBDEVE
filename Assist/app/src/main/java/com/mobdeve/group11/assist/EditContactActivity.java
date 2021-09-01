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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EditContactActivity extends AppCompatActivity {

    private ImageView ivCancel, ivDone, ivPic;
    private TextView tvPic, tvGroups, tvHead;
    private EditText etFName, etLName, etPNum, etGuardian;

    private Activity activity = EditContactActivity.this;

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
        setContentView(R.layout.activity_edit_contact);
    }

    private void initComponents(){
        verifyStoragePermissions(activity);
        this.ivCancel = findViewById(R.id.iv_toolbar_edit_left);
        this.ivDone = findViewById(R.id.iv_toolbar_edit_right);

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
                setResult(Activity.RESULT_OK, intent);
            }
            else{
                Toast t = Toast.makeText(getApplicationContext(),
                        "You have not yet entered in all of the required fields!",
                        Toast.LENGTH_LONG);
                t.show();
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

    private void initInfo(){
        this.ivPic = findViewById(R.id.iv_edit_contact_picture);
        this.tvPic = findViewById(R.id.tv_edit_contact_add_pic);
        this.etFName = findViewById(R.id.et_edit_contact_fname);
        this.etLName = findViewById(R.id.et_edit_contact_lname);
        this.etPNum = findViewById(R.id.et_edit_contact_number);
        this.etGuardian = findViewById(R.id.et_edit_contact_guardian);
        this.tvHead = findViewById(R.id.tv_toolbar_edit_title);

        if(getIntent().hasExtra(ContactInfo.FIRST_NAME.name()) && getIntent().hasExtra(ContactInfo.LAST_NAME.name()) &&
                getIntent().hasExtra(ContactInfo.PHONE_NUMBER.name())) {
            //photo
            this.etFName.setText(getIntent().getStringExtra(ContactInfo.FIRST_NAME.name()));
            this.etLName.setText(getIntent().getStringExtra(ContactInfo.LAST_NAME.name()));
            this.etPNum.setText(getIntent().getStringExtra(ContactInfo.PHONE_NUMBER.name()));
            this.etGuardian.setText(getIntent().getStringExtra(ContactInfo.GUARDIAN.name()));
            //groups
            this.tvHead.setText("Edit Contact");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
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
