package com.ak47.www.koko_androiddemo.sdk.baidumap;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ak47.www.koko_androiddemo.R;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;

import cz.msebera.android.httpclient.extras.PRNGFixes;

public class BaiduMapActivity extends AppCompatActivity {
    private Button button1, button2;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MyLocationData locationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        mMapView = (MapView) findViewById(R.id.bmapView);
        button1 = (Button) findViewById(R.id.changeType);
        button2 = (Button) findViewById(R.id.mylocation);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这需要定位的sdk
            }
        });
    }
}
