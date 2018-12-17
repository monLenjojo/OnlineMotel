package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerFunctionMyRoomList {
    ArrayList<JavaBeanMyRoom> arrayList = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    DatabaseReference data;
    public RecyclerFunctionMyRoomList(Context context, RecyclerView recyclerView, String firebaseId) {
        this.context = context;
        this.recyclerView = recyclerView;
        data = FirebaseDatabase.getInstance().getReference("userList").child(firebaseId);
        data.child("myRoomList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanMyRoom data = dataSnapshot.getValue(JavaBeanMyRoom.class);
                arrayList.add(data);
                upData();
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
        });
    }
    public void upData(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        RecyclerAdapterMyRoomList adapter = new RecyclerAdapterMyRoomList(context,arrayList);
        recyclerView.setAdapter(adapter);
    }
}
