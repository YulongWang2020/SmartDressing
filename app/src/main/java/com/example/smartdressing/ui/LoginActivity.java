package com.example.smartdressing.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartdressing.R;
import com.example.smartdressing.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mBtnLogin;

    String result = "";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews(){
        mEditTextUsername  = findViewById(R.id.editTextUsername);
        mEditTextUsername.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mEditTextPassword.setImeOptions(EditorInfo.IME_ACTION_GO);
        mEditTextPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);

        mBtnLogin = findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try{
                    commit();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    private void commit() throws IOException, JSONException{
        final String username = mEditTextUsername.getText().toString();
        final String password = mEditTextPassword.getText().toString();

        if(checkInput(username, password)){
            final Handler handler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what == 1){
                        ToastUtils.showShort(LoginActivity.this, result);

                        if(result.equals("true")){
                            ToastUtils.showShort(LoginActivity.this,"登录成功");

                            final Intent it = new Intent(LoginActivity.this, IfStartCamera.class);
                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(it);
                                }
                            };
                            timer.schedule(task, 1000);
                        }else{
                            ToastUtils.showShort(LoginActivity.this, "登录失败");
                        }
                    }
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Login(username, password);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Message m = new Message();
                    m.what = 1;
                    handler.sendMessage(m);
                }
            }).start();
        }
    }

    /**
     * 检查输入数据是否正确
     * @param username
     * @param password
     * @return
     */
    public boolean checkInput(String username, String password){
        if(username == null || username.trim().equals("")){
            ToastUtils.showShort(this, "请输出您的用户名");
        }else{
            if(password==null || password.trim().equals("")){
                ToastUtils.showShort(this, "密码不能为空");
            }else{
                return true;
            }
        }
        return false;
    }

    /**
     * 登录功能
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public boolean Login(String username, String password) throws IOException, JSONException {
        try {
            FormBody.Builder params = new FormBody.Builder();
            params.add("name", username);
            params.add("pwd", password);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://192.168.0.107:8080/user/login")
                    .post(params.build())
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.d("login_result", responseData);
            result = responseData;
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
