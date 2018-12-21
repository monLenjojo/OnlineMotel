package com.example.user1801.onlinemotel.firebaseThing;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.user1801.onlinemotel.chaosThing.JavaBeanRoomKey;
import com.example.user1801.onlinemotel.recyclerDesign.JavaBeanMyRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShareRoomPermission {
    String owner;
    String shareTo;
    Context context;
    JavaBeanMyRoom data;

    public ShareRoomPermission(final Context context, String owner, String shareTo, JavaBeanMyRoom data) {
        this.owner = owner;
        this.shareTo = shareTo;
        this.context = context;
        this.data = data;
        FirebaseDatabase.getInstance().getReference("userList").child(shareTo).child("myRoomList").child(data.getRoomNmae()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    if (task.isSuccessful()) {
                        upDataKey();
                    }else {
                        new AlertDialog.Builder(context).setMessage("分享失敗").show();
                    }
                }
            }
        });
    }
    public void upDataKey(){
        FirebaseDatabase.getInstance().getReference("userList").child(owner).child("myRoomList").child(data.getRoomNmae()).child("myRoomKey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    JavaBeanRoomKey theKey = dataSnapshot.getValue(JavaBeanRoomKey.class);
                    FirebaseDatabase.getInstance().getReference("userList").child(shareTo).child("myRoomList").child(data.getRoomNmae()).child("myRoomKey").setValue(theKey).addOnCompleteListener(new OnCompleteListener<Void>() {
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
