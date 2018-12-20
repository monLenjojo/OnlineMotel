package com.example.user1801.onlinemotel.firebaseThing;

import com.example.user1801.onlinemotel.chaosThing.JavaBeanRoomKey;
import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanAllRoomList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setTestData {
    public setTestData() {
        super();
    }

    public void addNewTestDataRoom(int startNum, int endNum, String money, String people) {
        DatabaseReference roomReference = FirebaseDatabase.getInstance().getReference("allRoomList");
        for (int i = startNum; i <= endNum; i++) {
            JavaBeanAllRoomList data = new JavaBeanAllRoomList(
                    "高雄市愛情摩天輪路之" + Integer.toString(i),
                    "摩天輪" + Integer.toString(i) + "號",
                    money,
                    people);
            roomReference.child(Integer.toString(i)).setValue(data);
        }
        DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference("allRoomKeyList");
        for (int i = startNum; i <= endNum; i++) {
            JavaBeanRoomKey data = new JavaBeanRoomKey("-12345/-543.21/21.354","null");
            keyReference.child("摩天輪" + Integer.toString(i) + "號").setValue(data);
        }
    }
}


