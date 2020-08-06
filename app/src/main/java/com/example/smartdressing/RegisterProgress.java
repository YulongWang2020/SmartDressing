package com.example.smartdressing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterProgress extends AppCompatActivity {
    private Button mBtnRegisterNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_progress);

        mBtnRegisterNext = findViewById(R.id.register_next);
        mBtnRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转是否拍照
                Intent intent = new Intent(RegisterProgress.this, IfStartCamera.class);
                startActivity(intent);
            }

        });

    }
}