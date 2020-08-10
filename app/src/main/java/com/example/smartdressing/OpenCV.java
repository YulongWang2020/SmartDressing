package com.example.smartdressing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;


import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.*;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class OpenCV extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;
    File cascFile;
    CascadeClassifier fullbody;
    private Mat mRgba,mGray;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_c_v);

        cameraBridgeViewBase = (JavaCameraView)findViewById(R.id.OpenCVCamera);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
        cameraBridgeViewBase.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);

        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) throws IOException {
                super.onManagerConnected(status);

                switch(status){

                    case BaseLoaderCallback.SUCCESS: {
                        InputStream is = getResources().openRawResource(R.raw.haarcascade_lowerbody);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        cascFile = new File(cascadeDir, "haarcascade_lowerbody.xml");

                        FileOutputStream fos = new FileOutputStream(cascFile);
                        byte[] buffer = new byte[4096];

                        int bytesRead;

                        while ((bytesRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        fos.close();
                        fullbody = new CascadeClassifier(cascFile.getAbsolutePath());

                        if (fullbody.empty()) {
                            fullbody = null;
                        } else
                            cascadeDir.delete();

                        cameraBridgeViewBase.enableView();
                    }
                    break;
                    default: {
                        super.onManagerConnected(status);

                    }
                    break;
                }
            }
        };
    }






    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat frame = inputFrame.rgba();
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        // body detector

        MatOfRect bodydetection = new MatOfRect();
        fullbody.detectMultiScale(mRgba,bodydetection);
        System.out.println("-------------------------------------------------------------");
        System.out.println(bodydetection.toArray());
        System.out.println("-------------------------------------------------------------");
        for(Rect rect: bodydetection.toArray()){
            Imgproc.rectangle(mRgba,new Point(rect.x,rect.y),
                    new Point(rect.x +rect.width,rect.y+rect.height),
                    new Scalar(255,0,0));
        }
        Core.flip(mRgba,mRgba,1);
        return mRgba;
    }




    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mGray = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"启动相机失败", Toast.LENGTH_SHORT).show();
        }

        else
        {
            try {
                baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase!=null){

            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }


}