package com.example.user1801.onlinemotel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanAllRoomList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyRoom extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    TextView textRoomName,textAddress,textPeople,textMoney,textTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        String roomName = getIntent().getStringExtra("ROOMNAME");
        String roomAddress= getIntent().getStringExtra("ROOMADDRESS");
        String roomPeople= getIntent().getStringExtra("ROOMPEOPLE");
        String roomMoney= getIntent().getStringExtra("ROOMMONEY");
        if (TextUtils.isEmpty(roomName) || TextUtils.isEmpty(roomAddress) || TextUtils.isEmpty(roomPeople) || TextUtils.isEmpty(roomMoney)) {
            this.finish();
            return;
        }
        textRoomName = findViewById(R.id.textMyRoomName);
        textAddress = findViewById(R.id.textMyRoomAddress);
        textPeople = findViewById(R.id.textMyRoomPeople);
        textMoney = findViewById(R.id.textMyRoomMoney);
        textTime = findViewById(R.id.textMyRoomTime);

        textRoomName.setText(roomName);
        textAddress.setText(roomAddress);
        textPeople.setText(roomPeople);
        textMoney.setText(roomMoney);

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat timeYear = new SimpleDateFormat("yyyy/MM/dd");

        SimpleDateFormat timeWeek = new SimpleDateFormat("E");//星期
        SimpleDateFormat timePart = new SimpleDateFormat("a");//時段
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,+1);
        Date date1= calendar.getTime();
        String time = timeFormat.format(date)+"："+timeWeek.format(date) +"："+ timePart.format(date)+"\n"+ timeFormat.format(date1);
        textTime.setText(time);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                String date = i + "年" + (i1 + 1) + "月" + i2 + "日";
//                String date2 = i + "/" + (i1 + 1) + "/" + i2;

                Log.i("TEST", "date: [" + date +"]");
            }
        });

    }

}
