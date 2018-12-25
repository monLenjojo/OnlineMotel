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
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user1801.onlinemotel.bluetoothChaos.BluetoothTest;
import com.example.user1801.onlinemotel.chaosThing.ChaosMath;
import com.example.user1801.onlinemotel.chaosThing.ChaosWithBluetooth;
import com.example.user1801.onlinemotel.firebaseThing.JavaBeanPower;
import com.example.user1801.onlinemotel.firebaseThing.JavaBeanRoomControlDevice;
import com.example.user1801.onlinemotel.firebaseThing.ShareRoomPermission;
import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanMyRoom;
import com.example.user1801.onlinemotel.recyclerDesign.RecyclerAdapterPassRecord;
import com.example.user1801.onlinemotel.recyclerDesign.RecyclerFunctionPassRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
                    chaosWithBluetooth.unlock(roomItem);
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

    TextView textRoomName, textAddress, textPeople, textMoney, textTime, textVoltage, textCurrent, textTotalPower;
    ImageButton imgButtonFan,imgButtonLamp;
    boolean fanState , lampState;
    private ChaosWithBluetooth chaosWithBluetooth;

    String name, roomItem, address, people, money, checkIn, checkOut;
    RecyclerView recyclerView;
    private String roomId,roomControlId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        name = getIntent().getStringExtra("ROOM_NAME");
        address = getIntent().getStringExtra("ROOM_ADDRESS");
        people = getIntent().getStringExtra("ROOM_PEOPLE");
        money = getIntent().getStringExtra("ROOM_MONEY");
        checkIn = getIntent().getStringExtra("ROOM_CHECK_IN");
        checkOut = getIntent().getStringExtra("ROOM_CHECK_OUT");
        roomItem = getIntent().getStringExtra("ROOM_PATH");
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(people) || TextUtils.isEmpty(money) || TextUtils.isEmpty(checkIn) || TextUtils.isEmpty(checkOut) || TextUtils.isEmpty(roomItem)) {
            this.finish();
            return;
        }
        recyclerView = findViewById(R.id.recyclerView_passRecord);
        textRoomName = findViewById(R.id.textMyRoomName);
        textAddress = findViewById(R.id.textMyRoomAddress);
        textPeople = findViewById(R.id.textMyRoomPeople);
        textMoney = findViewById(R.id.textMyRoomMoney);
        textTime = findViewById(R.id.textMyRoomTime);
        textVoltage = findViewById(R.id.textMyRoomV);
        textCurrent = findViewById(R.id.textMyRoomI);
        textTotalPower = findViewById(R.id.textMyRoomkWh);
        imgButtonFan = findViewById(R.id.imageButtonFan);
        imgButtonLamp = findViewById(R.id.imageButtonLamp);

        textRoomName.setText(name);
        textAddress.setText(address);
        textPeople.setText(people);
        textMoney.setText(money);
        textTime.setText(checkIn + " ~ " + checkOut);
        chaosWithBluetooth = new ChaosWithBluetooth(this);
        chaosWithBluetooth.getInstance();
    }

    DatabaseReference powerListener;
    ChildEventListener powerValListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (dataSnapshot.getValue() != null) {
                JavaBeanPower data = dataSnapshot.getValue(JavaBeanPower.class);
                textCurrent.setText(data.getCurrent());
                textVoltage.setText(data.getVoltage());
                textTotalPower.setText(data.getTotalPower());
            }
        }
        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (dataSnapshot.getValue() != null) {
                JavaBeanPower data = dataSnapshot.getValue(JavaBeanPower.class);
                textCurrent.setText(data.getCurrent());
                textVoltage.setText(data.getVoltage());
                textTotalPower.setText(data.getTotalPower());
            }
        }
        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    ChildEventListener controlValListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.d("onChildAdded: ",dataSnapshot.getKey());
            roomControlId = dataSnapshot.getKey();
            if (dataSnapshot.getValue() != null) {
                JavaBeanRoomControlDevice data = dataSnapshot.getValue(JavaBeanRoomControlDevice.class);
                fanState = data.isFanState();
                lampState = data.isLampState();
                if (fanState) {

                    imgButtonFan.setImageDrawable(getResources().getDrawable(R.drawable.fan_on));
                }else {
                    imgButtonFan.setImageDrawable(getResources().getDrawable(R.drawable.fan_off));
                }
                if (lampState) {
                    imgButtonLamp.setImageDrawable(getResources().getDrawable(R.drawable.lamp_on));
                }else {
                    imgButtonLamp.setImageDrawable(getResources().getDrawable(R.drawable.lamp_off));
                }
            }
        }
        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (dataSnapshot.getValue() != null) {
                JavaBeanRoomControlDevice data = dataSnapshot.getValue(JavaBeanRoomControlDevice.class);
                fanState = data.isFanState();
                lampState = data.isLampState();
                if (fanState) {

                    imgButtonFan.setImageDrawable(getResources().getDrawable(R.drawable.fan_on));
                }else {
                    imgButtonFan.setImageDrawable(getResources().getDrawable(R.drawable.fan_off));
                }
                if (lampState) {
                    imgButtonLamp.setImageDrawable(getResources().getDrawable(R.drawable.lamp_on));
                }else {
                    imgButtonLamp.setImageDrawable(getResources().getDrawable(R.drawable.lamp_off));
                }
            }
        }
        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
    Query query ,controlListener;
    ChildEventListener queryListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.d("onChildAdded: ",dataSnapshot.getValue().toString());
            JavaBeanMyRoom data = dataSnapshot.getValue(JavaBeanMyRoom.class);
            powerListener = FirebaseDatabase.getInstance().getReference("scadaSystem");
            powerListener.orderByChild("room").equalTo(data.getRoom()).addChildEventListener(powerValListener);
            controlListener = FirebaseDatabase.getInstance().getReference("allRoomControlDevice");
            controlListener.orderByChild("room").equalTo(data.getRoom()).addChildEventListener(controlValListener);
            new RecyclerFunctionPassRecord(MyRoom.this,recyclerView,FirebaseAuth.getInstance().getUid(), data.getRoom());
        }
        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }
        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        query = FirebaseDatabase.getInstance().getReference("userList").child(FirebaseAuth.getInstance().getUid()).child("myRoomList");
        query.orderByChild("name").equalTo(name).addChildEventListener(queryListener);
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
        if (0 == requestCode && null != data && data.getExtras() != null) {
            //掃描結果存放在 key 為 la.droid.qr.result 的值中
            final String result = data.getExtras().getString("la.droid.qr.result");

            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd");
            Long stayDay = null;
            Long unixIn = null, unixOut = null;
            try {
                unixIn = timeFormat.parse(checkIn).getTime();
                unixOut = timeFormat.parse(checkOut).getTime();
                stayDay = (unixOut - unixIn) / (1000 * 60 * 60 * 24);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (stayDay == null) {
                new AlertDialog.Builder(MyRoom.this).setMessage("失敗").show();
                return;
            }
            FirebaseDatabase.getInstance().getReference("userList").child(FirebaseAuth.getInstance().getUid()).child("myRoomList").child(roomItem)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("share", dataSnapshot.getValue().toString());
                            JavaBeanMyRoom data = dataSnapshot.getValue(JavaBeanMyRoom.class);
                            new ShareRoomPermission(MyRoom.this, FirebaseAuth.getInstance().getUid(), result, data);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
//            JavaBeanMyRoom roomData = new JavaBeanMyRoom(unixIn,unixOut,name,room,money,people);
//            new ShareRoomPermission(MyRoom.this,FirebaseAuth.getInstance().getUid(),result,roomData);
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
        powerListener.removeEventListener(powerValListener);
        query.removeEventListener(queryListener);
        controlListener.removeEventListener(controlValListener);
    }


    public void clickFan(View view) {
        if (fanState) {
            FirebaseDatabase.getInstance().getReference("allRoomControlDevice").child(roomControlId).child("fanState").setValue(false);
        }else {
            FirebaseDatabase.getInstance().getReference("allRoomControlDevice").child(roomControlId).child("fanState").setValue(true);
        }
    }
    public void clickLamp(View view){
        if (lampState) {
            FirebaseDatabase.getInstance().getReference("allRoomControlDevice").child(roomControlId).child("lampState").setValue(false);
        }else{
            FirebaseDatabase.getInstance().getReference("allRoomControlDevice").child(roomControlId).child("lampState").setValue(true);
        }
    }
}
