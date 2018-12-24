package com.example.user1801.onlinemotel.firebaseThing;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user1801.onlinemotel.chaosThing.JavaBeanRoomKey;
import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanMyRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShareRoomPermission {
    String owner;
    String shareTo;
    Context context;
    JavaBeanMyRoom data;
    private int[] itemNum = {0};

    public ShareRoomPermission(final Context context, String owner, String shareTo, final JavaBeanMyRoom data) {
        this.owner = owner;
        this.shareTo = shareTo;
        this.context = context;
        this.data = data;
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userList").child(shareTo).child("myRoomList");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("onDataChange: ", child.getKey());
                    itemNum[0] += 1;
                    Log.d("onDataChange: ", String.valueOf(itemNum[0]));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reference.child(String.valueOf(itemNum[0])).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            if (task.isSuccessful()) {
                                new AlertDialog.Builder(context).setMessage("分享成功").show();
                            }else {
                                new AlertDialog.Builder(context).setMessage("分享失敗").show();
                            }
                        }
                    }
                });
            }
        },2000);
    }
}
