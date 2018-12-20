package com.example.user1801.onlinemotel;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user1801.onlinemotel.bluetoothChaos.BluetoothTest;
import com.example.user1801.onlinemotel.chaosThing.ChaosMath;
import com.example.user1801.onlinemotel.chaosThing.ChaosWithBluetooth;

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
                    netWorkCheck();
                    chaosWithBluetooth.unlock(textRoomName.getText().toString());
//                    textTime.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(bluetooth.isConnected()){
//                                bluetooth.send(12.5f);
//                                bluetooth.send(12.5f);
//                            }else {
//                                if(bluetooth.connect(MyRoom.this,"98:D3:31:FB:19:CE")){
//                                    bluetooth.send(12.5f);
//                                    bluetooth.send(12.5f);
//                                }
//                            }
//                        }
//                    });
                    return true;
//                case R.id.navigation_notifications:
//                    new AlertDialog.Builder(MyRoom.this).setMessage(R.string.title_notifications).show();
//                    return true;
            }
            return false;
        }
    };

    TextView textRoomName,textAddress,textPeople,textMoney,textTime;

    private ChaosWithBluetooth chaosWithBluetooth;

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
        chaosWithBluetooth = new ChaosWithBluetooth(this);
        chaosWithBluetooth.getInstance();
    }

    public void netWorkCheck() {
        //先取得此CONNECTIVITY_SERVICE
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //取得網路相關資訊
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        //判斷是否有網路
        if (networkInfo == null || !networkInfo.isConnected()) {
            Log.i("INFO", "沒網路");
            new android.app.AlertDialog.Builder(this)
                    .setTitle("無法連線")
                    .setMessage("請確認裝置是否連上網路並可正常使用")
                    .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            netWorkCheck();
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new android.app.AlertDialog.Builder(MyRoom.this)
                                    .setMessage("無法連線將無法操作需網路驗證的裝置\n")
                                    .setPositiveButton("沒關係", null)
                                    .show();
                        }
                    }).show();
        }
    }
}
