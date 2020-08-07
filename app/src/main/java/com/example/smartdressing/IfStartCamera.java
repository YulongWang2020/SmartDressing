package com.example.smartdressing;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class IfStartCamera extends AppCompatActivity {
    private Button mBtnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if_start_camera);
        mBtnCamera = findViewById(R.id.ifstartcamera_next);
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IfStartCamera.this, OpenCV.class);
                startActivity(intent);
            }
        });
    }
}