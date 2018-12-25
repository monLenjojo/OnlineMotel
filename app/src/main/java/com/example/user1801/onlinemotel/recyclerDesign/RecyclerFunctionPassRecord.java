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
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class RecyclerFunctionPassRecord {
    ArrayList<JavaBeanPassRecord> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;
    DatabaseReference reference;
    String firebaseId;

    public RecyclerFunctionPassRecord(Context context, RecyclerView recyclerView, String firebaseId,String room) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.firebaseId = firebaseId;
        reference = FirebaseDatabase.getInstance().getReference("passRecordList").child(room);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanPassRecord data = dataSnapshot.getValue(JavaBeanPassRecord.class);
                Log.d("onChildAdded: ",dataSnapshot.getValue().toString());
                arrayList.add(data);
                upData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanPassRecord data = dataSnapshot.getValue(JavaBeanPassRecord.class);
                if(s==null){
                    arrayList.set(0,data);
                }else {
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

    private void upData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerAdapterPassRecord adapter = new RecyclerAdapterPassRecord(context,arrayList,firebaseId);
        recyclerView.setAdapter(adapter);
    }

    private void clearArrayList(){
        arrayList.clear();
    }
}
