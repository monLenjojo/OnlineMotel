package com.example.user1801.onlinemotel.firebaseThing;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user1801.onlinemotel.chaosThing.JavaBeanRoomKey;
import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanAllRoomList;
import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanPassRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setTestData {
    public setTestData() {
        super();
    }

    DatabaseReference roomReference = FirebaseDatabase.getInstance().getReference("allRoomList");

    public void addTestDataRoom(int startNum, int endNum, String money, String people) {
//        for (int i = startNum; i <= endNum; i++) {
//            JavaBeanAllRoomList data = new JavaBeanAllRoomList(
//                    "高雄市愛情摩天輪路之" + Integer.toString(i),
//                    "摩天輪" + Integer.toString(i) + "號",
//                    money,
//                    people);
//            roomReference.push().setValue(data);
//        }"-12345/-543.21/21.354""98:D3:31:FB:19:CE"
        final DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference("allRoomKeyList");
        roomReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("onDataChange:", child.getKey());
                    JavaBeanRoomKey data = new JavaBeanRoomKey("-12345/-543.21/21.354", "null", child.getKey());
                    keyReference.push().setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        for (int i = startNum; i <= endNum; i++) {
//            JavaBeanRoomKey data = new JavaBeanRoomKey("-12345/-543.21/21.354", "null", );
//            keyReference.push().setValue(data);
//        }
    }

    public void addScadaSystem() {
        final DatabaseReference scadaSystem = FirebaseDatabase.getInstance().getReference("scadaSystem");
        roomReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("onDataChange:", child.getKey());
                    JavaBeanPower data = new JavaBeanPower("0.0", "0.0", "0.0", child.getKey());
                    scadaSystem.push().setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addControlDevice() {
        final DatabaseReference controlDevice = FirebaseDatabase.getInstance().getReference("allRoomControlDevice");
        roomReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("onDataChange:", child.getKey());
                    JavaBeanRoomControlDevice data = new JavaBeanRoomControlDevice(child.getKey(),
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false);
                    controlDevice.push().setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addPassRecord() {
        final DatabaseReference passRecord = FirebaseDatabase.getInstance().getReference("passRecordList");
        roomReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("onDataChange:", child.getKey());
                    JavaBeanPassRecord data = new JavaBeanPassRecord("", "", child.getKey());
                    passRecord.push().setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}


