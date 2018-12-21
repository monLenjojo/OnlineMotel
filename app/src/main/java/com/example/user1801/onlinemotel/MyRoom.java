package com.example.user1801.onlinemotel;

import android.bluetooth.BluetoothManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user1801.onlinemotel.bluetoothChaos.BluetoothTest;
import com.example.user1801.onlinemotel.chaosThing.ChaosMath;
import com.example.user1801.onlinemotel.chaosThing.ChaosWithBluetooth;
import com.example.user1801.onlinemotel.firebaseThing.ShareRoomPermission;
import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanMyRoom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyRoom extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    chaosWithBluetooth.setSocketNull();
//                    chaosWithBluetooth.reSetBluetooth();
                    MyRoom.this.finish();
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
                case R.id.navigation_notifications:
                    new AlertDialog.Builder(MyRoom.this).setMessage(R.string.title_notifications).show();
                    buttonOnClick();
                    return true;
            }
            return false;
        }
    };

    TextView textRoomName,textAddress,textPeople,textMoney,textTime;

    private ChaosWithBluetooth chaosWithBluetooth;

    String roomName,roomAddress,roomPeople,roomMoney,roomCheckIn,roomCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
         roomName = getIntent().getStringExtra("ROOM_NAME");
         roomAddress= getIntent().getStringExtra("ROOM_ADDRESS");
         roomPeople= getIntent().getStringExtra("ROOM_PEOPLE");
         roomMoney= getIntent().getStringExtra("ROOM_MONEY");
         roomCheckIn= getIntent().getStringExtra("ROOM_CHECK_IN");
         roomCheckOut= getIntent().getStringExtra("ROOM_CHECK_OUT");
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

    public void buttonOnClick() {
        Intent i = new Intent("la.droid.qr.scan");    //使用QRDroid的掃描功能
        i.putExtra("la.droid.qr.complete", true);   //完整回傳，不截掉scheme
        try {
            //開啟 QRDroid App 的掃描功能，等待 call back onActivityResult()
            startActivityForResult(i, 0);
        } catch (ActivityNotFoundException ex) {
            //若沒安裝 QRDroid，則開啟 Google Play商店，並顯示 QRDroid App
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=la.droid.qr"));
            startActivity(intent);
        }
    }
    ImageView mQRCodeImg;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( 0==requestCode && null!=data && data.getExtras()!=null ) {
            //掃描結果存放在 key 為 la.droid.qr.result 的值中
            String result = data.getExtras().getString("la.droid.qr.result");
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy年MM月dd日");
            Long stayDay = null;
            try {
                Long checkIn = timeFormat.parse(roomCheckIn).getTime();
                Long checkOut = timeFormat.parse(roomCheckOut).getTime();
                stayDay = (checkOut-checkIn)/(1000*60*60*24);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (stayDay==null) {
                new AlertDialog.Builder(MyRoom.this).setMessage("失敗").show();
                return;
            }
            JavaBeanMyRoom roomData = new JavaBeanMyRoom(roomCheckIn,String.valueOf(stayDay),roomAddress,roomName,roomMoney,roomPeople);
            new ShareRoomPermission(MyRoom.this,FirebaseAuth.getInstance().getUid(),result,roomData);
//                Log.d("TEST",result);
//                TextView a = findViewById(R.id.textView);
//                a.setText(result);  //將結果顯示在 TextVeiw 中
//                new ShareDevicePermission(firebaseUid,result);
//            Intent shareToPage = new Intent(MainActivity.this,ShareToActivity.class);
//            shareToPage.putExtra("firebaseUid",result);
//            shareToPage.putExtra("myFirebaseUid",firebaseUid);
//            startActivity(shareToPage);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        chaosWithBluetooth.setSocketNull();
    }
}
