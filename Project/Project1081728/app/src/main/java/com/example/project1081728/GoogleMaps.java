package com.example.project1081728;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; //宣告 google map 物件
    float zoom;
    Spinner spnGeoPoint,spnMapType;
    String[] Names= new String[] {"法國羅浮宮","法國艾菲爾鐵塔","美國大峽谷","美國自由女神像",
                                  "新加坡魚尾獅公園","新加坡濱海灣花園","日本富士山","日本大阪城",
                                  "澳洲雪梨歌劇院","澳洲烏盧魯","台灣清境農場","台灣太魯閣國家公園"};
    String[] Introduction = new String[]{"正式名稱為羅浮博物館，位於法國巴黎市中心的塞納河邊，原是法國的王宮","位於法國巴黎第七區、塞納河畔戰神廣場的鐵製鏤空塔，世界著名建築",
                                      "美國西南部的國家公園，在1979年被列為世界自然遺產","位於美國紐約紐約港自由島上的巨型古典主義塑像",
                                      "新加坡面積最小的公園，位於富爾頓酒店旁","位於新加坡濱海灣中央的公園，於2012年落成，佔地101公頃，全土興建於填海土地上",
                                      "日本一座橫跨靜岡縣和山梨縣的活火山，是日本三名山之一","日本大阪市中央區（古屬攝津國東成郡）的大阪城公園內，為大阪名勝之一",
                                      "位於澳洲雪梨，是20世紀最具特色的建築之一，也是世界著名的表演藝術中心","卡達族塔國家公園是位於澳大利亞北領地的南部的一個國家公園，被列為世界自然遺產",
                                      "座落於中橫公路台14甲線霧社北端8公里處，空氣清新、具北歐風光","特色為峽谷和斷崖，崖壁峭立，景緻清幽，「太魯閣幽峽」因而名列台灣八景之一。"};
    String[] MapTypes= new String[] {"一般地圖","混合地圖","衛星地圖","地形圖"};
    LatLng[] aryLatLng= new LatLng[]{ new LatLng(48.86127458778759, 2.337472338634164),new LatLng(48.858525371408135, 2.2944812974398907),
            new LatLng(36.137755083374394, -112.10243523881745), new LatLng(40.68943648894023, -74.0444789453846),
            new LatLng(1.2869990261872624, 103.85453396149367), new LatLng(1.281772095956463, 103.86350590779078),
            new LatLng(35.362093664067906, 138.7274490236201), new LatLng(34.687183306333814, 135.52601986973156),
            new LatLng(-33.85658836974623, 151.2153181545874), new LatLng(-25.343485045993482, 131.03663081194998),
            new LatLng(24.057771544055676, 121.16248444193226), new LatLng(24.193997327216124, 121.49067767080591)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        spnGeoPoint=(Spinner) findViewById(R.id.spnGeoPoint);
        spnMapType=(Spinner) findViewById(R.id.spnMapType);

        // 建立 ArrayAdapter
        ArrayAdapter<String> adapterspnGeoPoint=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,Names);
        ArrayAdapter<String> adapterspnMapType=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,MapTypes);

        // 設定 Spinner 顯示的格式
        adapterspnGeoPoint.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterspnMapType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定 Spinner 的資料來源
        spnGeoPoint.setAdapter(adapterspnGeoPoint);
        spnMapType.setAdapter(adapterspnMapType);

        // 設定 spnGeoPoint 元件 ItemSelected 事件的 listener
        spnGeoPoint.setOnItemSelectedListener(spnGeoPointListener);
        spnMapType.setOnItemSelectedListener(spnMapTypeListener);

        // 利用 getSupportFragmentManager() 方法取得管理器
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        // 以非同步方式取得 GoogleMap 物件
        mapFragment.getMapAsync(this);
    }


    public void onMapReady(GoogleMap googleMap) {
        // 取得 GoogleMap 物件
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);  // 一般地圖

        mMap.getUiSettings().setCompassEnabled(true);     // 顯示指南針
        mMap.getUiSettings().setZoomControlsEnabled(true);// 顯示縮放圖示

        mMap.setOnMarkerClickListener(mMapListener);
        zoom=18; //設定放大倍率
    }

    private GoogleMap.OnMarkerClickListener mMapListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.showInfoWindow();
            Toast.makeText(getApplication(),marker.getTitle()+"\n"+marker.getSnippet()
                          ,Toast.LENGTH_LONG).show();
            return true;
        }
    };
    //  選擇景點
    private Spinner.OnItemSelectedListener spnGeoPointListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    // 建立觀看地圖的視點位罝
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(aryLatLng[position]) // 地圖的中心點
                            .zoom(zoom)                  // 放大倍率 178
                            .bearing(0)                  // 指南針旋轉 0 度
                            .tilt(0)                     // 俯視的角度
                            .build();                    // 建立 CameraPosition 物件

                    MarkerOptions markerOpt = new MarkerOptions();
                    markerOpt.position(aryLatLng[position]);
                    markerOpt.title(Names[position]);
                    markerOpt.snippet(Introduction[position]);
                    markerOpt.draggable(false);
                    markerOpt.visible(true);
                    markerOpt.anchor(0.5f,0.5f);
                    markerOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                    mMap.addMarker(markerOpt);
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };

    //  地圖樣式
    private Spinner.OnItemSelectedListener spnMapTypeListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    String sel=parent.getSelectedItem().toString();
                    switch (sel){
                        case "一般地圖":
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            break;
                        case "混合地圖":
                            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            break;
                        case "衛星地圖":
                            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            break;
                        case "地形圖":
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
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
                intent.setClass(GoogleMaps.this,Home.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Traval:{
                //旅遊景點
                Intent intent = new Intent();
                intent.setClass(GoogleMaps.this,Travel.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Map:{
                //景點地圖
                Intent intent = new Intent();
                intent.setClass(GoogleMaps.this, GoogleMaps.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Test:{
                //景點知多少
                Intent intent = new Intent();
                intent.setClass(GoogleMaps.this, Test.class);
                startActivity(intent);
                break;
            }

            case R.id.action_Ticket:{
                //票券訂購
                Intent intent = new Intent();
                intent.setClass(GoogleMaps.this, Ticket.class);
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
