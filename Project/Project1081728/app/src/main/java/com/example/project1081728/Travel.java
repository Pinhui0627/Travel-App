package com.example.project1081728;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Travel extends AppCompatActivity {

    private Spinner spncountry;
    private Button btnsearch,btnPrev,btnNext,btnAdd;
    private ImageView imgPhoto;
    private TextView txtResult;

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

    int choose=0;
    int p=0;
    int count=imgId_ame.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spncountry=(Spinner)findViewById(R.id.spncountry);
        btnsearch=(Button) findViewById(R.id.btnsearch);
        btnPrev=(Button)findViewById(R.id.btnPrev);
        btnNext=(Button)findViewById(R.id.btnNext);
        imgPhoto=(ImageView) findViewById(R.id.imgPhoto);
        txtResult=(TextView) findViewById(R.id.txtResult);


        spncountry.setOnItemSelectedListener(spncountryListener);
        btnsearch.setOnClickListener(btnsearchListener);
        btnPrev.setOnClickListener(btnPrevListener);
        btnNext.setOnClickListener(btnNextListener);

        // 設定國家清單
        ArrayAdapter<String> adapterspncountry=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,countrys);
        adapterspncountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spncountry.setAdapter(adapterspncountry);
    }


    //紀錄選擇的國家
    private Spinner.OnItemSelectedListener spncountryListener = new Spinner.OnItemSelectedListener() {
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

    //查詢
    private Button.OnClickListener btnsearchListener=new Button.OnClickListener(){
        public void onClick(View v){
            p=0;
            setup(choose);
        }
    };

    // 上一張
    private Button.OnClickListener btnPrevListener=new Button.OnClickListener(){
        public void onClick(View v){
            p--;
            if (p<0)
                p=count-1;
            setup(choose);
        }
    };

    // 下一張
    private Button.OnClickListener btnNextListener=new Button.OnClickListener(){
        public void onClick(View v){
            p++;
            if (p==count)
                p=0;
            setup(choose);
        }
    };

    //根據選擇項目設定圖片
    public void setup(int Choose){
        switch (Choose){
            case 0:{
                imgPhoto.setImageResource(imgId_ame[p]);
                txtResult.setText(placename_ame[p]);
                break;
            }
            case 1:{
                imgPhoto.setImageResource(imgId_aus[p]);
                txtResult.setText(placename_aus[p]);
                break;
            }
            case 2:{
                imgPhoto.setImageResource(imgId_fre[p]);
                txtResult.setText(placename_fre[p]);
                break;
            }
            case 3:{
                imgPhoto.setImageResource(imgId_jap[p]);
                txtResult.setText(placename_jap[p]);
                break;
            }
            case 4:{
                imgPhoto.setImageResource(imgId_sin[p]);
                txtResult.setText(placename_sin[p]);
                break;
            }
            case 5:{
                imgPhoto.setImageResource(imgId_tai[p]);
                txtResult.setText(placename_tai[p]);
                break;
            }
        }
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
                intent.setClass(Travel.this,Home.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Traval:{
                //旅遊景點
                Intent intent = new Intent();
                intent.setClass(Travel.this,Travel.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Map:{
                //景點地圖
                Intent intent = new Intent();
                intent.setClass(Travel.this, GoogleMaps.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Test:{
                //景點知多少
                Intent intent = new Intent();
                intent.setClass(Travel.this, Test.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Ticket:{
                //票券訂購
                Intent intent = new Intent();
                intent.setClass(Travel.this, Ticket.class);
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
