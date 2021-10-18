package com.example.project1081728;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Ticket extends AppCompatActivity {

    private SQLiteDatabase db = null;
    /* 建立資料表的欄位 */
    private final static String CREATE_TABLE = "CREATE TABLE buyticket(_id INTEGER PRIMARY KEY,ticket TEXT,price INTEGER)";

    private Spinner spnticket;
    private ListView lstcar;
    private Button btnadd,btnbuy;
    private TextView txtmoney;

    //票券名稱
    String[] Tickets= new String[] {"印象美國","浪漫法國","樂遊新加坡","戀戀日本","暢遊澳洲","秘境探訪"};
    //票券價格
    int[] Prices={90900,88800,39990,24500,57900,17900};

    //存取選購的票券名稱
    ArrayList<String> ticname = new ArrayList<String>();
    //存取選購的票券價格
    ArrayList<String> ticprice = new ArrayList<String>();

    String ticketname="",str="",AllTicket="";
    int price=0,add=0,s=0,money=0,count=0;
    long myid;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        spnticket=(Spinner)findViewById(R.id.spnticket);
        btnadd=(Button) findViewById(R.id.btnadd);
        btnbuy=(Button)findViewById(R.id.btnbuy);
        lstcar=(ListView)findViewById(R.id.lstcar);
        txtmoney=(TextView) findViewById(R.id.txtmoney);

        spnticket.setOnItemSelectedListener(spnticketListener);
        btnadd.setOnClickListener(btnaddListener);
        btnbuy.setOnClickListener(btnbuyListener);
        lstcar.setOnItemClickListener(lstcarListener);

        // spinner設定
        ArrayAdapter<String> adapterspncountry=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,Tickets);
        adapterspncountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnticket.setAdapter(adapterspncountry);


        // 建立資料庫，若資料庫已經存在則將之開啟
        db = openOrCreateDatabase("db1.db", MODE_PRIVATE, null);
        try{
            db.execSQL(CREATE_TABLE); // 建立資料表
        }catch (Exception e){
            db.execSQL("DROP TABLE buyticket");
            db.execSQL(CREATE_TABLE);
        }
        UpdateAdapter(); // 載入資料表至 ListView 中
    }

    //選擇票券時紀錄選取項目
    private Spinner.OnItemSelectedListener spnticketListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String sel=parent.getSelectedItem().toString();
            switch (sel){
                case "印象美國":{
                    add=0;
                    break;
                }
                case "浪漫法國":{
                    add=1;
                    break;
                }
                case "樂遊新加坡":{
                    add=2;
                    break;
                }
                case "戀戀日本":{
                    add=3;
                    break;
                }
                case "暢遊澳洲":{
                    add=4;
                    break;
                }
                case "秘境探訪":{
                    add=5;
                    break;
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };

    //加入購物車
    private Button.OnClickListener btnaddListener=new Button.OnClickListener(){
        public void onClick(View v){
            ticketname=Tickets[add];
            price=Prices[add];
            //總票價增加
            money+=Prices[add];
            //將選取項目加至票券名稱、票券價格
            ticname.add(Tickets[add]);
            ticprice.add(String.valueOf(Prices[add]));

            //將選取項目加進資料庫
            str="INSERT INTO buyticket (ticket,price) values ('"+ ticketname + "'," + price + ")";
            db.execSQL(str); // 執行SQL
            Toast toast=Toast.makeText(Ticket.this,"已成功加至購物車！", Toast.LENGTH_SHORT);
            toast.show();
            //更新購物車
            UpdateAdapter();
            txtmoney.setText("總票價："+money+" 元");
            //數量+1
            count++;
        }
    };

    //點選ListView項目時詢問是否移除購物車
    private ListView.OnItemClickListener lstcarListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Ticket.this);
            builder.setTitle("移除購物車");
            builder.setMessage("確定要移除購物車嗎？");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast toast=Toast.makeText(Ticket.this,"取消移除購物車", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //使用Cursor獲取目前所點取項目資料
                    Cursor c = get(id);
                    myid = id;
                    money-=c.getInt(2);
                    ticname.remove(c.getInt(0)-1);
                    ticprice.remove(c.getInt(0)-1);

                    //將選取項目從資料庫移除
                    db.delete("buyticket","_id="+c.getInt(0),null);
                    Toast toast=Toast.makeText(Ticket.this,"已移除購物車", Toast.LENGTH_SHORT);
                    toast.show();
                    //更新票價
                    txtmoney.setText("總票價："+money+" 元");
                    //更新購物車
                    UpdateAdapter();
                    //數量-1
                    count--;
                }
            });
            builder.show();
        }
    };

    public Cursor get(long rowId) throws SQLException { // 查詢指定 ID 的資料{
        Cursor cursor= db.rawQuery("SELECT _id, ticket, price FROM buyticket WHERE _id="+rowId,null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();
        else
            Toast.makeText(getApplicationContext(), "發生錯誤!", Toast.LENGTH_SHORT).show();
        return cursor; // 傳回_id、ticket、price欄位
    }

    //更新購物車
    public void UpdateAdapter(){
        Cursor cursor = db.rawQuery("SELECT * FROM buyticket", null);
        if (cursor != null && cursor.getCount() >= 0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.mylayout, // 自訂的 mylayout.xml
                    cursor, // 資料庫的 Cursors 物件
                    new String[] {"_id","ticket", "price" }, // _id、ticket、price 欄位
                    new int[] { R.id.txtId,R.id.txtName,R.id.txtPrice}, // 與 _id、ticket、price 對應的元件
                    0); // adapter 行為最佳化
            lstcar.setAdapter(adapter); // 將adapter增加到lstcar中
        }
    }

    //前往購買
    public Button.OnClickListener btnbuyListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(Ticket.this)
                .setTitle("前往購買")
                .setMessage("確定要前往購買嗎？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast=Toast.makeText(Ticket.this,"取消購買", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent();
                        intent.setClass(Ticket.this,Shopping.class);

        //                        for(int i = 0; i < count; i++){
        ////                            Cursor c = get(Long.valueOf(i));
        ////                            AllTicket+="\n"+c.getString(1)+"*1  $"+
        ////                                    c.getInt(2)+"元";
        ////                        }

                        for(int i = 0; i<ticname.size(); i++){
                            AllTicket+="\n"+ticname.get(i)+"*1  $"+ticprice.get(i)+"元";
                        }
                        //將所選擇之票券資料加入bundle傳至Shopping
                        Bundle bundle = new Bundle();
                        bundle.putString("AllTicket",AllTicket);
                        bundle.putString("Price",money+"");
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                })
                .show();
        }
    };

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
                intent.setClass(Ticket.this,Home.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Traval:{
                //旅遊景點
                Intent intent = new Intent();
                intent.setClass(Ticket.this,Travel.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Map:{
                //景點地圖
                Intent intent = new Intent();
                intent.setClass(Ticket.this, GoogleMaps.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Test:{
                //景點知多少
                Intent intent = new Intent();
                intent.setClass(Ticket.this, Test.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Ticket:{
                //票券訂購
                Intent intent = new Intent();
                intent.setClass(Ticket.this, Ticket.class);
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
