package com.example.asrafkaizen.materialdesignpractice.activity;

/**
 * Created by asrafkaizen on 5/12/2017.
 */

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
//REQUIRES IN APP GRADLE : compile 'com.android.support:design:25.3.1'
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


//REQUIRES IN APP GRADLE: compile 'com.github.bumptech.glide:glide:3.7.0'
import com.bumptech.glide.Glide;
import com.example.asrafkaizen.materialdesignpractice.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity
{


    private static final String TAG = "MainActivity";

    public static final int RequestPermissionCode = 1;
    public static final int PERMISSION_TRUE = 0;
    final int TAKE_PICTURE = 1;
    final int ACTIVITY_SELECT_IMAGE = 2;

    Button cameraBtn,closeBtn;
    ImageView img;
    Uri uri;

    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_camera);

        img=(ImageView)findViewById(R.id.imageView1);
        closeBtn=(Button)findViewById(R.id.cancelButton);
        cameraBtn=(Button)findViewById(R.id.cameraButton);

        cameraBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean hasPermission = checkRuntimePermission();
                if ( hasPermission ) { //check for permission before proceeding
                    selectImage();
                }
            }
        });

        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iclose = new Intent (getApplicationContext(), MainActivity.class);
                startActivity (iclose);
            }
        });
    }

    public void toPermissions (View view){
        Intent intentPermission = new Intent();
        intentPermission.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri PermissionUri = Uri.fromParts("package", getPackageName(), null);
        intentPermission.setData(PermissionUri);
        startActivity(intentPermission);
    }

    public void selectImage()
    {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if(options[which].equals("Take Photo"))
                {
                    Intent snapIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    uri = getOutputMediaFileUri(0); //0 for Image, 1 for Video, others for Misc. Refer getDirectory(int)
                    snapIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(snapIntent, TAKE_PICTURE);
                }
                else if(options[which].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTIVITY_SELECT_IMAGE);
                }
                else if(options[which].equals("Cancel"))
                {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
    public void onActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode, resultcode, data);
        if(resultcode==RESULT_OK) {
            if (requestcode == TAKE_PICTURE) {
                Uri snapUri = uri;
                Glide.with(this).load(snapUri).into(img);
            } else if (requestcode == ACTIVITY_SELECT_IMAGE) {
                Uri selectedImageUri = data.getData();
                Glide.with(this).load(selectedImageUri).into(img);
            }
        }
    }

    //Set lokasi dan nama file untuk file hasil dari camera(foto dan video)
    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaFile;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        if (type == 0) {
            mediaFile = new File(getDirectory(type).getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == 1) {
            mediaFile = new File(getDirectory(type).getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            Log.e(TAG, "getOutputMedia returns null at line 137");
            return null;
        }
        return mediaFile;
    }

    //Mendapatkan directory
    private static File mImageDir, mVideoDir, mMiscDir;

    private static File getDirectory(int type){
        Log.i("dir", checkDirectory() + "");
        if(checkDirectory()){
            if(type == 0){
                return mImageDir;
            }else if(type == 1){
                return mVideoDir;
            }else{
                return mMiscDir;
            }
        }else{
            return null;
        }

    }

    //Cek directory local distorage, jika belum ada, maka akan dibuat
    private static boolean checkDirectory(){
        File baseDir = new File(Environment.getExternalStorageDirectory(), R.string.app_name+"-media");
        mImageDir = new File(baseDir.getPath() + File.separator + "Images");
        mVideoDir = new File(baseDir.getPath() + File.separator + "Videos");
        mMiscDir = new File(baseDir.getPath() + File.separator + "Misc");
        if (!baseDir.exists()) {
            if (!baseDir.mkdirs()) {
                return false;
            }else{
                if (!mImageDir.exists()) {
                    if (!mImageDir.mkdirs()) {
                        return false;
                    }
                }
                if (!mVideoDir.exists()) {
                    if (!mVideoDir.mkdirs()) {
                        return false;
                    }
                }
                if (!mMiscDir.exists()) {
                    if (!mMiscDir.mkdirs()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkRuntimePermission() {

        boolean granted = false;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }

        int permissionCheckSt1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheckSt2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);

        if ((permissionCheckSt1 + permissionCheckSt2 + permissionCheckCamera == PERMISSION_TRUE)) {
            granted = true;
            Toast.makeText(getApplicationContext(), "Now you can use this function", Toast.LENGTH_SHORT).show();
        }

        return granted;
    }
}
