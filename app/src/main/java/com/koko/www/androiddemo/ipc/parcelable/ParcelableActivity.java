package com.koko.www.androiddemo.ipc.parcelable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.koko.www.androiddemo.R;

import java.util.Calendar;

/**
 * 测试Parcelable携带自定义数据
 */
public class ParcelableActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable);
        textView = findViewById(R.id.textPerson);
        //接受
        Person person = getIntent().getParcelableExtra("person_data");
        textView.setText("我是" + person.getName() + "今年" + person.getAge() + "是gay吗？" + person.getGay() + "我的爱好:" +
                person.getHobby().getHobbyName() + "," +
                person.getHobbies().get(0).getHobbyName() + "," + person.getHobbies().get(1).getHobbyName() +
                "今天是：" + person.getCalendar().get(Calendar.YEAR) + "年" +
                (person.getCalendar().get(Calendar.MONTH) + 1) + "月" + person.getCalendar().get(Calendar.DAY_OF_MONTH) + "日");
    }
}
