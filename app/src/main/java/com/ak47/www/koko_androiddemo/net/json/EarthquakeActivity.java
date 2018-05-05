package com.ak47.www.koko_androiddemo.net.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ak47.www.koko_androiddemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * this activity is used for practing json Resolving
 */
public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();


        ListView earthquakeList = (ListView) findViewById(R.id.list);
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);
        earthquakeList.setAdapter(earthquakeAdapter);

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
