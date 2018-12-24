package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerFunctionHomePage {
    ArrayList<JavaBeanRoomPath> arrayList = new ArrayList<JavaBeanRoomPath>();
    RecyclerView recyclerHomeView;
    DatabaseReference data;
    Context context;
    String firebaseId;
    public RecyclerFunctionHomePage(Context context, RecyclerView recyclerHomeView,String firebaseId) {
        this.context = context;
        this.recyclerHomeView = recyclerHomeView;
        this.firebaseId = firebaseId;
        data = FirebaseDatabase.getInstance().getReference("allRoomList");
        Log.d("TEST", "Start");
        data.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("RecyclerTEST", dataSnapshot.getKey().toString());
                JavaBeanAllRoomList val = dataSnapshot.getValue(JavaBeanAllRoomList.class);
                JavaBeanRoomPath data  = new JavaBeanRoomPath(val.address,val.name,val.money,val.people,dataSnapshot.getKey());
                arrayList.add(data);
//                Log.d("TEST", data.name);
                upData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("TEST",dataSnapshot.getValue().toString());
                JavaBeanAllRoomList val = dataSnapshot.getValue(JavaBeanAllRoomList.class);
                JavaBeanRoomPath data  = new JavaBeanRoomPath(val.address,val.name,val.money,val.people,dataSnapshot.getKey());
                if (s==null) {
                    Log.d("TEST", arrayList.get(0).name+" ,"+null);
                    arrayList.set(0,data);
                }else {
                    Log.d("TEST", arrayList.get(1+Integer.parseInt(s)).name + " ," + (1+Integer.parseInt(s)));
                    arrayList.set((1+Integer.parseInt(s)),data);
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
        });
    }

    public void upData(){
        recyclerHomeView.setHasFixedSize(true);
        recyclerHomeView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerHomeView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        RecyclerAdapterHomePage adapter = new RecyclerAdapterHomePage(context,arrayList,firebaseId);
        recyclerHomeView.setAdapter(adapter);
    }
}
