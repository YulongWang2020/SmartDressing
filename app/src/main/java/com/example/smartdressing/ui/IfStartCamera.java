package com.example.smartdressing.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartdressing.R;

public class IfStartCamera extends AppCompatActivity {
    private Button mBtnCamera;
    private Button mBtnJump;

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


        mBtnJump = findViewById(R.id.ifstartcamera_jump);
        mBtnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO

                Intent intent = new Intent(IfStartCamera.this, OpenCV.class);
                startActivity(intent);
            }
        });
    }
}