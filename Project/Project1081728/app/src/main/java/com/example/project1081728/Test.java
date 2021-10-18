package com.example.project1081728;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class Test extends AppCompatActivity {

    private Spinner spnChoose;
    private Button btnGo,btnStart;
    private ImageView imgPlace;
    private EditText edtAnswer;

    int r=0,choose,ans,err=0;
    String answer="",useranswer="";

    int i=R.drawable.guess;
    //圖片檔案
    int[] imgId_ame ={R.drawable.ame_di1,R.drawable.ame_ku2,R.drawable.ame_pu2,R.drawable.ame_zz1};
    int[] imgId_aus ={R.drawable.aus_bao1,R.drawable.aus_bri1,R.drawable.aus_lu1,R.drawable.aus_sing1};
    int[] imgId_fre ={R.drawable.fre_aa1,R.drawable.fre_rr1,R.drawable.fre_we1,R.drawable.fre_xx1};
    int[] imgId_jap ={R.drawable.jap_da1,R.drawable.jap_fu1,R.drawable.jap_qq1,R.drawable.jap_tt1};
    int[] imgId_sin ={R.drawable.sin_ani2,R.drawable.sin_fish1,R.drawable.sin_flo1,R.drawable.sin_sin};
    int[] imgId_tai ={R.drawable.tai_red1,R.drawable.tai_star,R.drawable.tai_tai1,R.drawable.farm2};

    //圖片名稱
    String[] countrys= new String[] {"美國","澳洲","法國","日本","新加坡","台灣"};
    String[] placename_ame ={"時代廣場","大峽谷國家公園","尼加拉瀑布","自由女神像"};
    String[] placename_aus ={"大堡礁","雪梨港灣大橋","烏盧魯","雪梨歌劇院"};
    String[] placename_fre ={"艾菲爾鐵塔","羅浮宮","蔚藍海岸","香榭麗舍大街"};
    String[] placename_jap ={"大阪城","富士山","清水寺","大通公園"};
    String[] placename_sin ={"夜間野生動物園","魚尾獅公園","濱海灣花園","新加坡環球影城"};
    String[] placename_tai ={"赤崁樓","七星潭","太魯閣國家公園","清境農場"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spnChoose=(Spinner)findViewById(R.id.spnChoose);
        btnGo=(Button) findViewById(R.id.btnGo);
        btnStart=(Button)findViewById(R.id.btnStart);
        edtAnswer=(EditText) findViewById(R.id.edtAnswer);
        imgPlace=(ImageView)findViewById(R.id.imgplace);

        spnChoose.setOnItemSelectedListener(spnChooseListener);
        btnGo.setOnClickListener(btnGoListener);
        btnStart.setOnClickListener(btnStartListener);

        // 設定國家清單
        ArrayAdapter<String> adapterspncountry=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,countrys);
        adapterspncountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnChoose.setAdapter(adapterspncountry);
        imgPlace.setImageResource(i);

    }

    //紀錄選擇的國家
    private Spinner.OnItemSelectedListener spnChooseListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String sel=parent.getSelectedItem().toString();
            switch (sel){
                case "美國":{
                    choose=0;
                    break;
                }
                case "澳洲":{
                    choose=1;
                    break;
                }
                case "法國":{
                    choose=2;
                    break;
                }
                case "日本":{
                    choose=3;
                    break;
                }
                case "新加坡":{
                    choose=4;
                    break;
                }
                case "台灣":{
                    choose=5;
                    break;
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };

    private Button.OnClickListener btnStartListener =new Button.OnClickListener(){
        public void onClick(View v){
            ans=getRadom(0,3);
            switch (choose){
                case 0:{
                    imgPlace.setImageResource(imgId_ame[ans]);
                    answer=placename_ame[ans];
                    break;
                }
                case 1:{
                    imgPlace.setImageResource(imgId_aus[ans]);
                    answer=placename_aus[ans];
                    break;
                }
                case 2:{
                    imgPlace.setImageResource(imgId_fre[ans]);
                    answer=placename_fre[ans];
                    break;
                }
                case 3:{
                    imgPlace.setImageResource(imgId_jap[ans]);
                    answer=placename_jap[ans];
                    break;
                }
                case 4:{
                    imgPlace.setImageResource(imgId_sin[ans]);
                    answer=placename_sin[ans];
                    break;
                }
                case 5:{
                    imgPlace.setImageResource(imgId_tai[ans]);
                    answer=placename_tai[ans];
                    break;
                }
            }
        }
    };

    private Button.OnClickListener btnGoListener=new Button.OnClickListener(){
        public void onClick(View v){
            useranswer = edtAnswer.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(Test.this);
            builder.setTitle("確認答案");
            builder.setMessage("確定答案是 " + useranswer +" 嗎? ");
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(useranswer.equals(answer))
                    {
                        Toast toast=Toast.makeText(Test.this,"恭喜答對！", Toast.LENGTH_LONG);
                        toast.show();
                        edtAnswer.setText("");
                        imgPlace.setImageResource(i);
                    }
                    else{
                        err++;
                        if(err==3){
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Test.this);
                            builder2.setTitle("答案錯誤");
                            builder2.setMessage("答案為"+answer+"\n"+"答錯3次了，是否前往旅遊景點複習呢?");
                            builder2.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    imgPlace.setImageResource(i);
                                }
                            });
                            builder2.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent= new Intent();
                                    intent.setClass(Test.this,Travel.class);
                                    startActivity(intent);
                                }
                            });
                            builder2.show();
                        }else{
                            Toast toast=Toast.makeText(Test.this,"再試一次！", Toast.LENGTH_LONG);
                            toast.show();
                            edtAnswer.setText("");
                        }

                    }
                }
            });
            builder.show();

        }
    };

    private int getRadom(int min,int max){
        long seed = System.currentTimeMillis();
        Random r = new Random();
        r.setSeed(seed);
        return (min + r.nextInt(max-min+1));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_Home: {
                //首頁
                Intent intent = new Intent();
                intent.setClass(Test.this,Home.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Traval:{
                //旅遊景點
                Intent intent = new Intent();
                intent.setClass(Test.this,Travel.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Map:{
                //景點地圖
                Intent intent = new Intent();
                intent.setClass(Test.this, GoogleMaps.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Test:{
                //景點知多少
                Intent intent = new Intent();
                intent.setClass(Test.this, Test.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Ticket:{
                //票券訂購
                Intent intent = new Intent();
                intent.setClass(Test.this, Ticket.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Dial:{
                //呼叫撥號按鈕
                Uri uri = Uri.parse("tel:0212345678");
                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
