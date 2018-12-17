package com.example.user1801.onlinemotel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanAllRoomList;

import java.text.ParseException;
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
                    new AlertDialog.Builder(MyRoom.this).setMessage(R.string.title_home).show();
                    return true;
                case R.id.navigation_dashboard:
                    new AlertDialog.Builder(MyRoom.this).setMessage(R.string.title_door_key).show();
                    return true;
//                case R.id.navigation_notifications:
//                    new AlertDialog.Builder(MyRoom.this).setMessage(R.string.title_notifications).show();
//                    return true;
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
        String roomName = getIntent().getStringExtra("ROOM_NAME");
        String roomAddress= getIntent().getStringExtra("ROOM_ADDRESS");
        String roomPeople= getIntent().getStringExtra("ROOM_PEOPLE");
        String roomMoney= getIntent().getStringExtra("ROOM_MONEY");
        String roomCheckIn= getIntent().getStringExtra("ROOM_CHECK_IN");
        String roomCheckOut= getIntent().getStringExtra("ROOM_CHECK_OUT");
        if (TextUtils.isEmpty(roomName) || TextUtils.isEmpty(roomAddress) || TextUtils.isEmpty(roomPeople) || TextUtils.isEmpty(roomMoney) || TextUtils.isEmpty(roomCheckIn) || TextUtils.isEmpty(roomCheckOut)) {
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
        textTime.setText(roomCheckIn +" ~ "+ roomCheckOut);
    }
}
