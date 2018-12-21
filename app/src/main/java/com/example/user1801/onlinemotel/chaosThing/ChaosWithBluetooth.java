package com.example.user1801.onlinemotel.chaosThing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.user1801.onlinemotel.bluetoothChaos.BluetoothTest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChaosWithBluetooth {
    BluetoothTest bluetoothTest;
    ChaosMath chaosMath;
    Context context;

    public ChaosWithBluetooth(Context context) {
        this.context = context;
    }
    public void getInstance(){
        bluetoothTest = new BluetoothTest();
        chaosMath = new ChaosMath();
        chaosMath.inti();
    }
    public boolean connect(String macAddress){
        return bluetoothTest.connect(context,macAddress);
    }
    public boolean isConnect(){
        return bluetoothTest.isConnected();
    }
    public void chaosAndSend(Float doorKey){
        chaosMath.chaosMath();
        bluetoothTest.send(chaosMath.getU1());
        bluetoothTest.send((1 + (chaosMath.getX1() * chaosMath.getX1())) *doorKey);
//        Log.d("ChoasWithBluetooth",bluetoothTest.getCheckMCUState());
    }
    public void runRnlockLoop(String doorKey,String state){
        if (!TextUtils.isEmpty(doorKey)) {//-12345, USBKey2 = -543.21, USBKey3 = 21.354
            Log.d("ChoasWithBluetooth","Start Run");
            String[] keyArray = doorKey.split("/");
            switch (state){
                case "A":
                    chaosAndSend(Float.valueOf(keyArray[0]));
                    Log.d("ChoasWithBluetooth",String.valueOf(keyArray[0]));
                    runRnlockLoop(doorKey,bluetoothTest.getCheckMCUState());
                    break;
                case "B":
                    chaosAndSend(Float.valueOf(keyArray[1]));
                    Log.d("ChoasWithBluetooth",String.valueOf(keyArray[1]));
                    runRnlockLoop(doorKey,bluetoothTest.getCheckMCUState());
                    break;
                case "C":
                    chaosAndSend(Float.valueOf(keyArray[2]));
                    Log.d("ChoasWithBluetooth",String.valueOf(keyArray[2]));
                    runRnlockLoop(doorKey,bluetoothTest.getCheckMCUState());
                    break;
                case "E":
                    chaosMath = new ChaosMath();
                    chaosMath.inti();
                    chaosAndSend(Float.valueOf(keyArray[0]));
                    Log.d("ChoasWithBluetooth",String.valueOf(keyArray[0]));
                    runRnlockLoop(doorKey,bluetoothTest.getCheckMCUState());
                    break;
                default:
                    Log.d("ChoasWithBluetooth",state);
                    break;
            }
        }
    }
    public void unlock(final String roomName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getOnFirebaseKey(roomName);
            }
        }).start();
    }
    public void getOnFirebaseKey(String roomName){
        DatabaseReference keyQuery = FirebaseDatabase.getInstance().getReference("userList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("myRoomList").child(roomName).child("myRoomKey");
        final JavaBeanRoomKey[] key = new JavaBeanRoomKey[1];
        final boolean[] findState = {false};
        keyQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Log.d("ChoasWithBluetooth", dataSnapshot.getValue().toString());
                    key[0] = dataSnapshot.getValue(JavaBeanRoomKey.class);
                    Log.d("ChoasWithBluetooth", key[0].key);
                    findState[0] = true;
                    if (isConnect()) {
//                    runRnlockLoop("-12345/-543.21/21.354","A");
                        runRnlockLoop(key[0].key, "A");
                    } else {
                        if (connect(key[0].macAddress)) {
//                        runRnlockLoop("-12345/-543.21/21.354","A");
                            runRnlockLoop(key[0].key, "A");
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(findState[0]){
            Log.d("ChoasWithBluetooth","findState[0] = " + findState[0]);
        }else{
            Log.d("ChoasWithBluetooth","findState[0] = " + findState[0]);
        }
    }

    public void reSetBluetooth(){
        bluetoothTest.reSetBluetooth();
    }
    public void setSocketNull(){
        bluetoothTest.setSocketNull();
    }
}
