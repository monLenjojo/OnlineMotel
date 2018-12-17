package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1801.onlinemotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerAdapterHomePage extends RecyclerView.Adapter<RecyclerAdapterHomePage.RecyclerViewHolder>{
    Context context;
    ArrayList<JavaBeanAllRoomList> arrayList;
    String firebaseId;
    public RecyclerAdapterHomePage(Context context, ArrayList<JavaBeanAllRoomList> arrayList, String firebaseId){
        this.context = context;
        this.arrayList = arrayList;
        this.firebaseId =  firebaseId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view, viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.textRoomName.setText(arrayList.get(i).roomNmae);
        recyclerViewHolder.textWhere.setText(arrayList.get(i).where);
        recyclerViewHolder.textPeople.setText(arrayList.get(i).people);
        recyclerViewHolder.textMoney.setText(arrayList.get(i).money);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class  RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textRoomName, textWhere, textPeople, textMoney;
        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            textRoomName = itemView.findViewById(R.id.textView_RoomName);
            textWhere = itemView.findViewById(R.id.textView_Where);
            textPeople = itemView.findViewById(R.id.textView_People);
            textMoney = itemView.findViewById(R.id.textView_Money);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context).setMessage("訂房")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    JavaBeanAllRoomList data = new JavaBeanAllRoomList(textWhere.getText().toString(),textRoomName.getText().toString(),textMoney.getText().toString(),textPeople.getText().toString());
                                    FirebaseDatabase.getInstance().getReference("userList")
                                            .child(firebaseId).child("myRoomList")
                                            .child(textRoomName.getText().toString())
                                            .setValue(data)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isComplete()) {
                                                if (task.isSuccessful()) {
                                                    new AlertDialog.Builder(context).setMessage("成功").show();
                                                }else {
                                                    new AlertDialog.Builder(context).setMessage("失敗").show();
                                                }
                                            }
                                        }
                                    });
                                }
                            }).show();
                }
            });
        }
    }

}
