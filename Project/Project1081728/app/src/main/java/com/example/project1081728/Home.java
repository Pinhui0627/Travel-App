package com.example.project1081728;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class Home extends AppCompatActivity {
    private ImageView imgFront, imgStop, imgPlay, imgPause, imgNext, imgEnd;
    private ListView lstVideo;
    private SurfaceView sufVideo;
    private final String sdPATH= "sdcard/";
    //影片名稱
    String[] videoname=new String[] {"Journey", "Cherish", "Sunshine", "Adventure"};
    //影片檔案
    String[] videofile=new String[] {"video1.mp4", "video2.mp4","video3.mp4","video4.mp4"};
    private int cListItem=0; //目前播放影片
    private Boolean falgPause=false; //暫停、播放旗標
    private MediaPlayer mediaplayer;
    private SurfaceHolder sufHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgFront=(ImageView)findViewById(R.id.imgFront);
        imgStop=(ImageView)findViewById(R.id.imgStop);
        imgPlay=(ImageView)findViewById(R.id.imgPlay);
        imgPause=(ImageView)findViewById(R.id.imgPause);
        imgNext=(ImageView)findViewById(R.id.imgNext);
        imgEnd=(ImageView)findViewById(R.id.imgEnd);
        lstVideo=(ListView)findViewById(R.id.lstVideo);

        sufVideo=(SurfaceView)findViewById(R.id.sufVideo);
        imgFront.setOnClickListener(listener);
        imgStop.setOnClickListener(listener);
        imgPlay.setOnClickListener(listener);
        imgPause.setOnClickListener(listener);
        imgNext.setOnClickListener(listener);
        imgEnd.setOnClickListener(listener);
        lstVideo.setOnItemClickListener(lstListener);

        //設定影片清單
        ArrayAdapter<String> adaSong=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videoname);
        lstVideo.setAdapter(adaSong);
        mediaplayer=new MediaPlayer();
        //建立 Surface 相關物件
        sufHolder=sufVideo.getHolder();
    }

    //設定影片控制項
    private ImageView.OnClickListener listener=new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.imgFront:  //上一片
                    frontVideo();
                    break;
                case R.id.imgStop:  //停止
                    if (mediaplayer.isPlaying()) { // 是否正在播放
                        mediaplayer.reset(); //釋放資源
                    }
                    break;
                case R.id.imgPlay:  //播放
                    if(falgPause) {  //如果是暫停狀態就繼續播放
                        mediaplayer.start();
                        falgPause=false;
                    } else {  //非暫停則重新播放
                        playVideo(sdPATH + videofile[cListItem]);
                    }
                    break;
                case R.id.imgPause:  //暫停
                    mediaplayer.pause();
                    falgPause=true;
                    break;
                case R.id.imgNext:  //下一片
                    nextVideo();
                    break;
                case R.id.imgEnd:  //結束
                    mediaplayer.release();
                    finish();
                    break;
            }
        }
    };

    //點選ListView項目時播放該影片
    private ListView.OnItemClickListener lstListener=new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            cListItem = position; //取得點選位置
            playVideo(sdPATH + videofile[cListItem]); //播放
        }
    };

    //播放影片
    private void playVideo(String path) {
        mediaplayer.reset();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaplayer.setDisplay(sufHolder);
        try
        {
            mediaplayer.setDataSource(path); //播放影片路徑
            mediaplayer.prepare();
            mediaplayer.start(); //開始播放
            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer arg0) {
                    nextVideo(); //播放完後播下一片
                }
            });
        } catch (IOException e) {}
        falgPause=false;
    }

    //下一片
    private void nextVideo() {
        cListItem++;
        if (cListItem >= lstVideo.getCount()) //若到最後就移到第一片
            cListItem = 0;
        playVideo(sdPATH + videofile[cListItem]);
    }

    //上一片/
    private void frontVideo() {
        cListItem--;
        if (cListItem < 0)
            cListItem = lstVideo.getCount()-1; //若到第一片就移到最後
        playVideo(sdPATH + videofile[cListItem]);
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
                intent.setClass(Home.this,Home.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Traval:{
                //旅遊景點
                Intent intent = new Intent();
                intent.setClass(Home.this,Travel.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Map:{
                //景點地圖
                Intent intent = new Intent();
                intent.setClass(Home.this, GoogleMaps.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Test:{
                //景點知多少
                Intent intent = new Intent();
                intent.setClass(Home.this, Test.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Ticket:{
                //票券訂購
                Intent intent = new Intent();
                intent.setClass(Home.this, Ticket.class);
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
