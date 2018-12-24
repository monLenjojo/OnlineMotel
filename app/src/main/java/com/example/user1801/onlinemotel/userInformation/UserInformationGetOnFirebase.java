package com.example.user1801.onlinemotel.userInformation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class UserInformationGetOnFirebase {
    TextView txName,txEmail,txPhone,txAddress;
    DatabaseReference databaseReference;
    UserInformationSharedPreferences userInformationSharedPreferences;
    public UserInformationGetOnFirebase(String firebaseUid, TextView txName, TextView txEmail, TextView txPhone, TextView txAddress) {
        this.txName = txName;
        this.txEmail = txEmail;
        this.txPhone = txPhone;
        this.txAddress = txAddress;
        databaseReference = FirebaseDatabase.getInstance().getReference("userList").child(firebaseUid);
//        databaseReference.addChildEventListener();
    }

    public void updataNewInformation(String item, String value){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(item,value);
        databaseReference.updateChildren(hashMap);
    }

    public void addListenter() {
        databaseReference.addChildEventListener(listener);
    }

    public void removeListenter(){
        databaseReference.removeEventListener(listener);
    }

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//            JavaBeanSetPerson data = dataSnapshot.getValue(JavaBeanSetPerson.class);
            getData(dataSnapshot,s);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            getData(dataSnapshot,s);
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

    private void getData(DataSnapshot dataSnapshot, String s) {
        Log.d("TEST123",dataSnapshot.getValue().toString() + s);
//        JavaBeanSetPerson data = dataSnapshot.getValue(JavaBeanSetPerson.class);
//        if (!TextUtils.isEmpty(data.name)) {
        if(TextUtils.isEmpty(s)){
            txAddress.setText(dataSnapshot.getValue().toString());
        }else {
            switch (s) {
                case "myRoomList":
                    txName.setText(dataSnapshot.getValue().toString());
                    break;
                case "address":
                    txEmail.setText(dataSnapshot.getValue().toString());
                    break;
                case "name":
                    txPhone.setText(dataSnapshot.getValue().toString());
                    break;
            }
        }
//        }
    }
}




