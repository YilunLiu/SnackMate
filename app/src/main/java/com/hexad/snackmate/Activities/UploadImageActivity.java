package com.hexad.snackmate.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hexad.snackmate.R;
import com.hexad.snackmate.Utils.Utils;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by yilunliu on 11/15/15.
 */
public class UploadImageActivity extends Activity {


    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL = 1;

    private Button chooseButton;
    private Button confirmButton;
    private ImageView previewImageView;
    private String picturePath = null;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL);

        }


        setContentView(R.layout.activity_upload_image);
        setTitle("Upload Profile Image");

        chooseButton = (Button) findViewById(R.id.choose_image_button);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here, thisActivity is the current activity
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        confirmButton = (Button) findViewById(R.id.confirm_upload_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null){
                    Toast toast = Toast.makeText(getApplicationContext(), "No Image is Chosen", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    byte[] image = stream.toByteArray();
                    ParseFile imageFile = new ParseFile("imagePic",image);
                    imageFile.saveInBackground();
                    ParseUser.getCurrentUser().put("profilePicture", imageFile);
                    ParseUser.getCurrentUser().saveInBackground();

                    Intent resultInent = new Intent();
                    resultInent.putExtra("picturePath",picturePath);
                    setResult(RESULT_OK, resultInent);
                    finish();


                }
            }

        });


        previewImageView = (ImageView) findViewById(R.id.profile_image_preview);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST_READ_EXTERNAL:{
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = BitmapFactory.decodeFile(picturePath);
            bitmap = Utils.getCircleBitmap(bitmap);
            previewImageView.setImageBitmap(bitmap);
        }
    }
}
