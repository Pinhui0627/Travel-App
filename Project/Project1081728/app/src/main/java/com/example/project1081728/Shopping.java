package com.example.project1081728;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Shopping extends AppCompatActivity {
    private TextView txtnames,txtprices;
    private EditText edtReciver,edtPhone,edtBirthday,edtMail;
    private Button btnDone;
    private RadioGroup rdg;
    private RadioButton rb1,rb2,rb3;

    String gender="",Detail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        txtnames=(TextView)findViewById(R.id.txtnames);
        txtprices=(TextView)findViewById(R.id.txtprices);
        edtReciver=(EditText) findViewById(R.id.edtReciver);
        edtPhone=(EditText) findViewById(R.id.edtPhone);
        edtBirthday=(EditText) findViewById(R.id.edtBirthday);
        edtMail=(EditText) findViewById(R.id.edtMail);
        btnDone=(Button) findViewById(R.id.btnDone);
        rdg=(RadioGroup)findViewById(R.id.rdg);
        rb1=(RadioButton)findViewById(R.id.rb1);
        rb2=(RadioButton)findViewById(R.id.rb2);
        rb3=(RadioButton)findViewById(R.id.rb3);

        rdg.setOnCheckedChangeListener(rdgListener);
        btnDone.setOnClickListener(btnDoneListener);

        //取得bundle
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        txtnames.setText("選購票券："+bundle.getString("AllTicket"));
        txtprices.setText("總票價："+bundle.getString("Price")+"元");

    }

    //選擇性別
    private RadioGroup.OnCheckedChangeListener rdgListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.rb1)
                gender="男";
            else if(checkedId==R.id.rb2)
                gender="女";
            else if(checkedId==R.id.rb3)
                gender="其他";
        }
    };

    //確認訂購
    private Button.OnClickListener btnDoneListener= new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Detail = "姓名："+ edtReciver.getText().toString() + "\n" +
                    "電話："+ edtPhone.getText().toString() + "\n" +
                    "性別："+ gender + "\n" +
                    "生日："+ edtBirthday.getText().toString() + "\n" +
                    "信箱："+ edtMail.getText().toString() + "\n" +
                    txtnames.getText()+"\n"+txtprices.getText();

            new AlertDialog.Builder(Shopping.this)
                .setTitle("訂單確認")
                .setMessage("請確認訂單資料\n"+Detail)
                .setNegativeButton("返回資料", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("確認訂購", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(Shopping.this,"已完成訂購",Toast.LENGTH_LONG);
                        toast.show();
                        finish();//返回票券訂購
                    }
                })
                .show();
        }
    };

}
