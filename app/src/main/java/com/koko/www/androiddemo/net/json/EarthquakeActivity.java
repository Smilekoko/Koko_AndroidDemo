package com.koko.www.androiddemo.net.json;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.koko.www.androiddemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 关于JSON的处理
 */
public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();


        ListView earthquakeList = findViewById(R.id.list);
        final EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);
        earthquakeList.setAdapter(earthquakeAdapter);

        //设置Item监听器
        earthquakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = earthquakeAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });
//        jsonTraversal();
    }

    /**
     * 如何用安装原生的类遍历JSON字符并抽取数据
     */
    void jsonTraversal() {
        //模拟json
        String candyJson = "{" +
                "\"candies\":" +
                "[{" +
                "\"name\":" + "\"Jelly Beans\"" +
                "," +
                "\"count\":" + "10" +
                "}]" +
                "}";
        System.out.println(candyJson);
        JSONObject root = null;
        try {
            root = new JSONObject(candyJson);
            JSONArray candiesArray = root.getJSONArray("candies");

            JSONObject firstCandy = candiesArray.getJSONObject(0);
            String name = firstCandy.getString("name");
            int count = firstCandy.getInt("count");
            System.out.println("----CandyName:" + name + "," + "count" + count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
