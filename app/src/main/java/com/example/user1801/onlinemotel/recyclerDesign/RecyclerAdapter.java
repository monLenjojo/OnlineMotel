package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1801.onlinemotel.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    Context context;
    ArrayList<JavaBeanAllRoomList> arrayList;
    public RecyclerAdapter(Context context,ArrayList<JavaBeanAllRoomList> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view, viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        Log.d("TEST", "recyclerViewHolder：" +String.valueOf(i));
        recyclerViewHolder.textRoomName.setText(arrayList.get(i).roomNmae);
        recyclerViewHolder.textWhere.setText(arrayList.get(i).where);
        recyclerViewHolder.textPeople.setText(Integer.toString(arrayList.get(i).people));
        recyclerViewHolder.textMoney.setText(Integer.toString(arrayList.get(i).money));
    }

    @Override
    public int getItemCount() {
        Log.d("TEST", "getItemCount：" +String.valueOf(arrayList.size()));
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
                    Toast.makeText(context, "click"+textRoomName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
