package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user1801.onlinemotel.MyRoom;
import com.example.user1801.onlinemotel.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerAdapterMyRoomList extends RecyclerView.Adapter<RecyclerAdapterMyRoomList.ViewHolder> {
    Context context;
    ArrayList<JavaBeanMyRoom> arrayList;

    public RecyclerAdapterMyRoomList(Context context, ArrayList<JavaBeanMyRoom> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Long unixIn = Long.valueOf(arrayList.get(i).getCheckIn());
        Long unixOut = Long.valueOf(arrayList.get(i).getCheckOut());
        final Long stay = (unixOut-unixIn)/(1000*60*60*24);
        FirebaseDatabase.getInstance().getReference("allRoomList").orderByChild("name").equalTo(arrayList.get(i).name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JavaBeanAllRoomList data = dataSnapshot.getValue(JavaBeanAllRoomList.class);
                viewHolder.textRoomName.setText(data.name);
                viewHolder.textWhere.setText(data.address);
                viewHolder.textPeople.setText(data.people);
                viewHolder.textMoney.setText(String.valueOf(Integer.valueOf(data.money)*Integer.valueOf(String.valueOf(stay))));
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
        Date in = new Date(unixIn);
        Date out = new Date(unixOut);
        viewHolder.checkIn.setText(format.format(in));
        viewHolder.checkOut.setText(format.format(out));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRoomName, textWhere, textPeople, textMoney, checkIn, checkOut;
        LinearLayout layoutRecyclerList_Time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutRecyclerList_Time = itemView.findViewById(R.id.layoutRecyclerList_Time);
            layoutRecyclerList_Time.setVisibility(View.VISIBLE);
            textRoomName = itemView.findViewById(R.id.textView_RoomName);
            textWhere = itemView.findViewById(R.id.textView_Where);
            textPeople = itemView.findViewById(R.id.textView_People);
            textMoney = itemView.findViewById(R.id.textView_Money);
            checkIn = itemView.findViewById(R.id.textView_CheckIn);
            checkOut = itemView.findViewById(R.id.textView_CheckOut);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent page = new Intent(context, MyRoom.class);
                    page.putExtra("ROOM_NAME", textRoomName.getText().toString());
                    page.putExtra("ROOM_ADDRESS", textWhere.getText().toString());
                    page.putExtra("ROOM_PEOPLE", textPeople.getText().toString());
                    page.putExtra("ROOM_MONEY", textMoney.getText().toString());
                    page.putExtra("ROOM_CHECK_IN", checkIn.getText().toString());
                    page.putExtra("ROOM_CHECK_OUT", checkOut.getText().toString());
                    page.putExtra("ROOM_PATH",String.valueOf(getAdapterPosition()));
                    context.startActivity(page);
                }
            });
        }
    }
}
