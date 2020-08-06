package com.example.smartdressing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IfStartCamera extends AppCompatActivity {
    private Button mBtnCamera;
//    private ImageView imageview;
//    String currentImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if_start_camera);
        mBtnCamera = findViewById(R.id.ifstartcamera_next);
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IfStartCamera.this, Camera.class);
                startActivity(intent);
            }
        });
    }
//    public static Camera openCamera(int cameraId) {
//        try{
//            return Camera.open(cameraId);
//        }catch(Exception e) {
//            return null;
//        }
//    }

//    try {
//        Camera camera = Camera.open(mCamerId);
//    }catch (Exception e){
//        LogUtil.i("摄像头被占用");
//        e.printStackTrace();
//    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bitmap bitmap = BitmapFactory.decodeFile(currentImagePath);
//
////            Bundle extras = data.getExtras();
////            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageview.setImageBitmap(bitmap);
//        }
//    }


//    static final int REQUEST_IMAGE_CAPTURE = 1;

//    private void captureImage() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//
//            File imageFile = null;
//            imageFile = getImageFile();
//            if(imageFile != null){
//                String pathToFile = imageFile.getAbsolutePath();
//                Uri imageUri = FileProvider.getUriForFile(this,"com.SmartDressing.FileProvider",imageFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
//            }
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    private File getImageFile(){
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageName = "jpg_" + timeStamp;
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File imageFile = null;
//        try {
//            imageFile = File.createTempFile(imageName, ".jpg", storageDir);
//        }
//        catch( IOException ie){
//            ie.printStackTrace();
//        }
//        currentImagePath = imageFile.getAbsolutePath();
//        return imageFile;
//    }


}