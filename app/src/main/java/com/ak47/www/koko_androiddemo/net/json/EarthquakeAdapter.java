package com.ak47.www.koko_androiddemo.net.json;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ak47.www.koko_androiddemo.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 1796 on 2018/5/4.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public EarthquakeAdapter(@NonNull Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }


        Earthquake currentEarthquake = getItem(position);

        //十进制格式处理类 处理地震强度
        DecimalFormat formatter = new DecimalFormat("0.00");
        String formattedMagnitude = formatter.format(currentEarthquake.getmMagnitude());

        TextView magnitude = listItemView.findViewById(R.id.magnitude);
        //获取地震强度背景图片索引
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        //获取等级颜色
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        magnitude.setText(formattedMagnitude);

        //拆分地震详细位置
        String originalLocation = currentEarthquake.getmLocation();
        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryLocationView = listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);
        TextView locationOffsetView = listItemView.findViewById(R.id.location_offest);
        locationOffsetView.setText(locationOffset);

        //转换地震时间格式
        Date dateObject = new Date(currentEarthquake.getmTimeInMilliseconds());
        String formattedDate = FormatterDate(dateObject);
        String formattedTime = FormatterTime(dateObject);

        TextView dateView = listItemView.findViewById(R.id.date);
        dateView.setText(formattedDate);

        TextView timeView = listItemView.findViewById(R.id.time);
        timeView.setText(formattedTime);

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    public String FormatterDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormatter.format(dateObject);
    }

    public String FormatterTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
