package com.example.user1801.onlinemotel.firebaseThing;

import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanAllRoomList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setTestData {
    public setTestData() {
        super();
    }

    public void addNewTestData(int startNum, int endNum, String money, String people) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("allRoomList");
        for (int i = startNum; i <= endNum; i++) {
            JavaBeanAllRoomList data = new JavaBeanAllRoomList("高雄市愛情摩天輪路之" + Integer.toString(i),
                    "摩天輪" + Integer.toString(i) + "號", money, people);
            databaseReference.child(Integer.toString(i)).setValue(data);
        }
    }
}


