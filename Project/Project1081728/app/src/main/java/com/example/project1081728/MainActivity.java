package com.example.project1081728;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText edtID, edtPW;
    private Button btnLogin, btnReset;
    private String[] login;
    private File filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtID = (EditText)findViewById(R.id.edtID);
        edtPW = (EditText)findViewById(R.id.edtPW);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnReset = (Button)findViewById(R.id.btnReset);
        btnLogin.setOnClickListener(listener);
        btnReset.setOnClickListener(listener);

        readFile();
        requestPermissions();   //檢查驗證


    }

    //檢查授權
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT>=23) {//Android 6.0 以上
            //判斷是否已取得授權
            int hasPermission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            int hasPermission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if(hasPermission1 != PackageManager.PERMISSION_GRANTED && hasPermission2 != PackageManager.PERMISSION_GRANTED){ //皆未取得授權
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else if(hasPermission1 != PackageManager.PERMISSION_GRANTED && hasPermission2 == PackageManager.PERMISSION_GRANTED ){ //撥打電話未取得授權
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
            }else if(hasPermission1 == PackageManager.PERMISSION_GRANTED && hasPermission2 != PackageManager.PERMISSION_GRANTED){ //存取SD卡資料未取得授權
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            else{
                return;
            }
        }
         //已取得驗證
        //允許執行程式
    }


    //requestPermissions 觸發的事件
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perssions,int[] grantResults){

        if (requestCode == 1){
            if(perssions.length == 2){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED&&grantResults[1]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"未取得授權!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else{
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"未取得授權!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode,perssions,grantResults);
        }
    }

    //讀取檔案
    private void readFile() {
        filename = new File("sdcard/login.txt");  //SD卡根目錄密碼檔login
        try {
            FileInputStream fin = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line = "", wholedata = "";
            int i = 0;
            while ((line = reader.readLine()) != null) {
                wholedata = wholedata + line + "\n";
                i++;
            }
            login = wholedata.split("\n");
            reader.close();
            fin.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error!", Toast.LENGTH_LONG) .show();
            e.printStackTrace();
        }
    }

    //登入按鈕
    private Button.OnClickListener listener=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnLogin:  //登入
                    //檢查帳號及密碼是否都有輸入
                    if(edtID.getText().toString().equals("") || edtPW.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "帳號及密碼都必須輸入！", Toast.LENGTH_LONG) .show();
                        break;
                    }
                    Boolean flag=false;
                    for(int i=0;i<login.length;i+=2){
                        if(edtID.getText().toString().equals(login[i])){ //帳號存在於login
                            flag=true;
                            if(edtPW.getText().toString().equals(login[i+1])){ //密碼正確
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("登入")
                                        .setMessage("登入成功！\n歡迎使用TraVeler！")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                Intent intent = new Intent();
                                                intent.setClass(MainActivity.this,Home.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            } else {
                                Toast.makeText(getApplicationContext(), "密碼不正確！", Toast.LENGTH_LONG) .show();
                                edtPW.setText("");
                                break;
                            }
                        }
                    }
                    if(!flag) {
                        Toast.makeText(getApplicationContext(), "帳號不正確！", Toast.LENGTH_LONG) .show();
                        edtID.setText("");
                        edtPW.setText("");
                    }
                    break;
                case R.id.btnReset:  //重新輸入
                    edtID.setText("");
                    edtPW.setText("");
                    break;
            }
        }
    };

}
