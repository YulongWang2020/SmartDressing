package com.example.smartdressing.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartdressing.R;
import com.example.smartdressing.utils.RegexUtils;
import com.example.smartdressing.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterProgressActivity extends AppCompatActivity {
    //界面控件
    private Button mBtnRegisterNext;
    private EditText mEditTextTextEmailAddress;
    private EditText mEditTextTextPassword1;
    private EditText mEditTextTextPassword2;
    private EditText mEditTextTextUsername;

    String result = "";

    /**
    临时代码

    */

    private Button mbtntestButton;

    ////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_progress);
        /////////////
        mbtntestButton = findViewById(R.id.testbutton);
        mbtntestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterProgressActivity.this, Camera.class);
                startActivity(intent);
            }
        });
        /////////////

        initViews();
    }

    /**
     * 注册页面的总体功能
     */
    private void initViews(){
        mEditTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        mEditTextTextEmailAddress.setImeOptions(EditorInfo.IME_ACTION_NEXT); //下一步

        mEditTextTextUsername = findViewById(R.id.editTextTextUsername);
        mEditTextTextUsername.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        mEditTextTextPassword1 = findViewById(R.id.editTextTextPassword);
        mEditTextTextPassword1.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        mBtnRegisterNext = findViewById(R.id.register_next);

        mEditTextTextPassword2 = findViewById(R.id.editTextTextPassword2);
        mEditTextTextPassword2.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditTextTextPassword2.setImeOptions(EditorInfo.IME_ACTION_GO);
        //手机键盘事件监听
//        mEditTextTextPassword2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO){
//                    try {
//                        commit();
//                    }catch (IOException | JSONException e1){
//                        e1.printStackTrace();
//                    }
//                }
//                return false;
//            }
//        });

        mBtnRegisterNext.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 提交表单数据
     * @throws IOException
     * @throws JSONException
     */
    private void commit() throws IOException, JSONException{
        final String email = mEditTextTextEmailAddress.getText().toString().trim();
        final String password1 = mEditTextTextPassword1.getText().toString().trim();
        final String password2 = mEditTextTextPassword2.getText().toString().trim();
        final String username = mEditTextTextUsername.getText().toString().trim();
        Log.d("email", email);

        if(checkInput(email, password1, password2,username)){
            final Handler handler= new Handler(){
                @Override
              public void handleMessage(Message msg){
                  if(msg.what == 1){
                        ToastUtils.showShort(RegisterProgressActivity.this, result);

                        //根据注册返回结果来决定是否跳转
                        if(result.equals("1")){
                            ToastUtils.showShort(RegisterProgressActivity.this, "注册成功");

                            final Intent it = new Intent(RegisterProgressActivity.this, LoginActivity.class);
                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(it); //执行
                                }
                            };
                            timer.schedule(task, 1000); //1秒后
                        }else{
                            ToastUtils.showShort(RegisterProgressActivity.this, "注册失败");
                        }
                  }
              }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Register(email, password1, username);
                    }catch (IOException|JSONException e){
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
     * 核查邮箱、密码是否输入正确
     * @param email
     * @param password1
     * @param password2
     * @return
     */
    private boolean checkInput(String email, String password1, String password2, String username){
        Log.d("password", password1);
        if(TextUtils.isEmpty(email)){
            ToastUtils.showShort(this, "邮箱不能为空");
        }else{
            if(!RegexUtils.checkEmail(email)){
                ToastUtils.showShort(this, "邮箱格式不正确");
            }else if(TextUtils.isEmpty(username)){
                ToastUtils.showShort(this, "用户名不能为空");
            }else if (password1 == null || password1.trim().equals("")){
                ToastUtils.showLong(this, "密码不能为空");
            }else if(password1.length()<6||password1.length()>18 || TextUtils.isEmpty(password1)){
                ToastUtils.showShort(this, "请输出6-18位密码");
            }else if (!password1.equals(password2)){
                ToastUtils.showShort(this, "两次密码输入不相符");
            }else{
                return true;
            }
        }
        return false;
    }


    /**
     * 注册函数，与后台进行交互的功能
     * @param email
     * @param password
     * @param username
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public boolean Register(String email, String password, String username) throws IOException, JSONException{
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", username);
            jsonObject.put("email", email);
            jsonObject.put("pwd", password);
//            jsonObject.toString();
            Log.d("jsonObject", jsonObject.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://192.168.0.107:8080/user/create")
                    .post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString()))
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.d("responseData", responseData);
            result = responseData;

            return true;
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return false;
    }
}