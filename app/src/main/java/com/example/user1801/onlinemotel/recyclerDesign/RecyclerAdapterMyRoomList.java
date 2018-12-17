package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user1801.onlinemotel.MyRoom;
import com.example.user1801.onlinemotel.R;

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
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textRoomName.setText(arrayList.get(i).roomNmae);
        viewHolder.textWhere.setText(arrayList.get(i).where);
        viewHolder.textPeople.setText(arrayList.get(i).people);
        Integer stayVal = Integer.valueOf(arrayList.get(i).getStay());
        viewHolder.textMoney.setText(String.valueOf(Integer.valueOf(arrayList.get(i).money)*stayVal));
        String checkInVal = arrayList.get(i).getCheckIn();
        viewHolder.checkIn.setText(checkInVal);
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar checkIn = Calendar.getInstance();
        try {
            checkIn.setTime(timeFormat.parse(checkInVal));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        checkIn.add(Calendar.DAY_OF_YEAR,+stayVal);
        Date checkOutVal = checkIn.getTime();
        viewHolder.checkOut.setText(timeFormat.format(checkOutVal));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
                    Intent page = new Intent(context,MyRoom.class);
                    page.putExtra("ROOM_NAME",textRoomName.getText().toString());
                    page.putExtra("ROOM_ADDRESS",textWhere.getText().toString());
                    page.putExtra("ROOM_PEOPLE",textPeople.getText().toString());
                    page.putExtra("ROOM_MONEY",textMoney.getText().toString());
                    page.putExtra("ROOM_CHECK_IN",checkIn.getText().toString());
                    page.putExtra("ROOM_CHECK_OUT",checkOut.getText().toString());
                    context.startActivity(page);
                }
            });
        }
    }
}
