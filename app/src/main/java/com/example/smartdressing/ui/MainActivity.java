package com.example.smartdressing.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartdressing.R;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLoginButton;
    private Button mBtnRegisterButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLoginButton = findViewById(R.id.login_button);
        mBtnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到登录界面
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });

        mBtnRegisterButton = findViewById(R.id.register_button);
        mBtnRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到注册界面
                Intent intent = new Intent(MainActivity.this, RegisterProgressActivity.class);
                startActivity(intent);
            }

        });

    }
}