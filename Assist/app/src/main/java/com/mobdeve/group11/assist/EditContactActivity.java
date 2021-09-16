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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group11.assist.database.AssistViewModel;
import com.mobdeve.group11.assist.database.Contact;
import com.mobdeve.group11.assist.database.ThumbnailImage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EditContactActivity extends AppCompatActivity {

    private AssistViewModel viewModel;

    private ImageView ivCancel, ivDone, ivPic;
    private TextView tvPic, tvGroups, tvHead;
    private EditText etFName, etLName, etPNum, etGuardian;

    private Activity activity = EditContactActivity.this;

    private Contact contact = new Contact("","","","");
    private ThumbnailImage thumbnailImage;


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

        viewModel = new ViewModelProvider(this).get(AssistViewModel.class);
    }

    public void onResume() {
        super.onResume();
        this.initInfo();
        this.initComponents();
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

            String fName = etFName.getText().toString().trim();
            String lName = etLName.getText().toString().trim();
            String pNum = etPNum.getText().toString().trim();
            String guardian = etGuardian.getText().toString().trim();

            if (fName.length() > 0 && lName.length() > 0 && pNum.length() > 0){
                if(AppUtils.isValidPhoneNumber(pNum)){
                    contact.setFirstName(fName);
                    contact.setLastName(lName);
                    contact.setContactNumber(pNum);
                    contact.setGuardian(guardian);
                    viewModel.updateContact(contact);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "Invalid contact number", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast t = Toast.makeText(getApplicationContext(), "Missing fields", Toast.LENGTH_SHORT);
                t.show();
            }
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

        Integer cId = getIntent().getIntExtra(ContactInfo.ID.name(), 0);

        if(cId != null) {
            //photo
            viewModel.getContactById(cId).observe(this, contact ->{
                this.contact = contact;
                this.etFName.setText(contact.getFirstName());
                this.etLName.setText(contact.getLastName());
                this.etPNum.setText(contact.getContactNumber());
                this.etGuardian.setText(contact.getGuardian());

                viewModel.getThumbnailById(contact.getThumbnailId()).observe(this, thumbnailImage -> {
                    if(thumbnailImage != null){
                        this.ivPic.setImageBitmap(thumbnailImage.getImage());
                        this.thumbnailImage = thumbnailImage;
                        this.tvPic.setText("Change Photo");
                    }
                });
            });


            //groups
            this.tvHead.setText("Edit Contact");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    //pop-up for selecting profile picture
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

    //checking for permissions
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

    //actions after photo is selected
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
                thumbnail= AppUtils.getResizedBitmap(thumbnail, 400);
                ivPic.setImageBitmap(thumbnail);
                tvPic.setText("Change Photo");
                if(thumbnailImage != null){
                    thumbnailImage.setImage(thumbnail);
                    viewModel.updateThumbnail(thumbnailImage);
                }
                else{
                    contact.setThumbnailId((int) viewModel.addThumbnailGetId(new ThumbnailImage(thumbnail)));
                    viewModel.updateContact(contact);
                }
            }
        }
    }



}
