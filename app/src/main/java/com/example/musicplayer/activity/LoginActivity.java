package com.example.musicplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;

/*
 * 函数名：LoginActivity
 * 参数：无
 * 作用： 登录界面
 * 接口:View.OnClickListener
 * 继承:AppCompatActivity
 * 返回值：无
 * */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTxtName;
    private EditText edtTxtPsw;
    private ImageView ivLogin;
    private String strNewName;
    private String strPswd;
    private TextView tvForgetPsw;
    private TextView tvRegister;
    private CheckBox chkAgreement;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        ivLogin.setOnClickListener(this);
        //监听跳转到注册界面
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //监听跳转到忘记密码界面
        tvForgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view_forgetpsw) {
                Intent intent = new Intent(LoginActivity.this, ForgetPswdActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * 函数名：onClick
     * 参数：View
     * 作用： 点击监听
     * 返回值：无
     * */
    @Override
    public void onClick(View view_login) {
        strNewName = edtTxtName.getText().toString().trim();  //获取输入值
        strPswd = edtTxtPsw.getText().toString().trim();
        discernInfo();
    }

    /*
     * 函数名：discernInfo
     * 参数：无
     * 作用： 判断账号密码，判断是否已经同意协议
     * 返回值：无
     * */
    private void discernInfo() {
        if (!chkAgreement.isChecked()) {
            Toast.makeText(getApplicationContext(), "请先同意协议", Toast.LENGTH_SHORT).show();
        } else {
            if (strNewName.equals("1") && strPswd.equals("1")) {
                saveSharedPreferences(this,"1");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * 函数名：initViews
     * 参数：无
     * 作用： 实例化对象
     * 返回值：无
     * */
    private void initViews() {
        edtTxtName = findViewById(R.id.edtTxt_name);
        edtTxtPsw = findViewById(R.id.edtTxt_pswd);
        tvForgetPsw = findViewById(R.id.tv_forget);
        tvRegister = findViewById(R.id.tv_regiestr);
        ivLogin = findViewById(R.id.iv_login);
        chkAgreement = findViewById(R.id.chk_agreement);
    }
    public void saveSharedPreferences(Context context, String save) {
        sp = context.getSharedPreferences("prompt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Login", save);
        editor.commit();// 保存新数据
    }


}
