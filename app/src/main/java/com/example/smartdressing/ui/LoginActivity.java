package com.example.smartdressing.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartdressing.R;
import com.example.smartdressing.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mBtnLogin;

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

        mEditTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO){
                    try {
                        commit();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }


    private void commit() throws IOException, JSONException{
        final String username = mEditTextUsername.getText().toString();
        final String password = mEditTextPassword.getText().toString();

        if(checkInput(username, password)){
            mBtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Login(username, password);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
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

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://192.168.0.103:8080/user/get_by_name")
                    .post(params.build())
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONArray jsonArray = new JSONArray(responseData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Log.d("pwd", jsonObject.getString("pwd"));

        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return false;
    }

}
