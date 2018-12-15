package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.user1801.onlinemotel.MainActivity;
import com.example.user1801.onlinemotel.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerFunction {
    ArrayList<JavaBeanAllRoomList> arrayList = new ArrayList<JavaBeanAllRoomList>();
    RecyclerView recyclerHomeView;
    DatabaseReference data;
    Context context;
    public RecyclerFunction(Context context,RecyclerView recyclerHomeView) {
        this.context = context;
        this.recyclerHomeView = recyclerHomeView;
        data = FirebaseDatabase.getInstance().getReference("allRoomList");
        Log.d("TEST", "Start");
        data.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("TEST", dataSnapshot.getValue().toString());
                JavaBeanAllRoomList data = dataSnapshot.getValue(JavaBeanAllRoomList.class);
                arrayList.add(data);
                Log.d("TEST", data.roomNmae);
                updata();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("TEST",dataSnapshot.getValue().toString());
                JavaBeanAllRoomList data = dataSnapshot.getValue(JavaBeanAllRoomList.class);
                if (s==null) {
                    Log.d("TEST", arrayList.get(0).roomNmae+" ,"+null);
                    arrayList.set(0,data);
                }else {
                    Log.d("TEST", arrayList.get(1+Integer.parseInt(s)).roomNmae + " ," + (1+Integer.parseInt(s)));
                    arrayList.set((1+Integer.parseInt(s)),data);
                }

//                Log.d("TEST",data.roomNmae);
//                if (s!= null) {
//                    Log.d("TEST",arrayList.get(Integer.valueOf(s)).roomNmae);
//                }
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

    public void updata(){
        recyclerHomeView.setHasFixedSize(true);
        recyclerHomeView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerHomeView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        RecyclerAdapter adapter = new RecyclerAdapter(context,arrayList);
        recyclerHomeView.setAdapter(adapter);
    }
}
